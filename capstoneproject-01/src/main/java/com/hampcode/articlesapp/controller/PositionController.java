package com.hampcode.articlesapp.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.hampcode.articlesapp.common.PageInitPaginationPosition;
import com.hampcode.articlesapp.model.Position;
import com.hampcode.articlesapp.service.PositionService;


@Controller
@RequestMapping("/positions")
public class PositionController {
	protected static final String POSITION_VIEW = "positions/showPosition"; // view template for single position
	protected static final String POSITION_ADD_FORM_VIEW = "positions/newPosition"; // form for new position
	protected static final String POSITION_EDIT_FORM_VIEW = "positions/editPosition"; // form for editing an position
	protected static final String POSITION_PAGE_VIEW = "positions/allPositions"; // list with pagination
	protected static final String INDEX_VIEW = "index"; // positions with pagination

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private PageInitPaginationPosition pageInitiPagination;
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public String getPositionById(@PathVariable(value = "id") Long positionId, Model model) {
		model.addAttribute("position", positionService.findById(positionId));
		return POSITION_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public ModelAndView getAllPositions(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page
			) {
		ModelAndView modelAndView = 
				pageInitiPagination.initPagination(pageSize
						,page
						, POSITION_PAGE_VIEW);
		return modelAndView;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/new")
	public String newPosition(Model model) {

		// in case of redirection model will contain position
		if (!model.containsAttribute("position")) {
			model.addAttribute("position", new Position());
		}
		return POSITION_ADD_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/create")
	public String createPosition(@Valid Position position, 
			BindingResult result,
			RedirectAttributes attr,
			Model model) {

        if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.position",result);
        	attr.addFlashAttribute("position",position);
        	
        	return "redirect:/positions/new";
        }
		
		Position newPosition = positionService.create(position);
		model.addAttribute("position", newPosition);

		return "redirect:/positions/" + newPosition.getId();
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("{id}/edit")
	public String editPosition(@PathVariable(value = "id") Long positionId, Model model) {
		/*
		 * in case of redirection from '/position/{id}/update' model will contain position
		 * with field values
		 */
		if (!model.containsAttribute("position")) {
			model.addAttribute("position", positionService.findById(positionId));
		}
		return POSITION_EDIT_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping(path = "/{id}/update")
	public String updatePosition(@PathVariable(value = "id") Long positionId,@Valid Position positionDetails,BindingResult result,
			RedirectAttributes attr, Model model) {
        if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.position",result);
        	attr.addFlashAttribute("position",positionDetails);
        	
        	return "redirect:/positions/new";
        }
		positionService.update(positionId, positionDetails);
		model.addAttribute("position", positionService.findById(positionId));
		return "redirect:/positions/" + positionId;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/{id}/delete")
	public String deletePosition(@PathVariable("id") Long positionId) {
		positionService.delete(positionId);
		return "redirect:/positions";
	}
}
