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
import com.hampcode.articlesapp.common.PageInitPaginationOffice;
import com.hampcode.articlesapp.model.Office;
import com.hampcode.articlesapp.service.OfficeService;

@Controller
@RequestMapping("/offices")
public class OfficeController {
	protected static final String OFFICE_VIEW = "offices/showOffice"; // view template for single office
	protected static final String OFFICE_ADD_FORM_VIEW = "offices/newOffice"; // form for new office
	protected static final String OFFICE_EDIT_FORM_VIEW = "offices/editOffice"; // form for editing an office
	protected static final String OFFICE_PAGE_VIEW = "offices/allOffices"; // list with pagination
	protected static final String INDEX_VIEW = "index"; // offices with pagination

	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private PageInitPaginationOffice pageInitiPagination;
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public String getOfficeById(@PathVariable(value = "id") Long officeId, Model model) {
		model.addAttribute("office", officeService.findById(officeId));
		return OFFICE_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public ModelAndView getAllOffices(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page
			) {
		ModelAndView modelAndView = 
				pageInitiPagination.initPagination(pageSize
						,page
						, OFFICE_PAGE_VIEW);
		return modelAndView;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/new")
	public String newOffice(Model model) {

		// in case of redirection model will contain office
		if (!model.containsAttribute("office")) {
			model.addAttribute("office", new Office());
		}
		return OFFICE_ADD_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/create")
	public String createOffice(@Valid Office office, 
			BindingResult result,
			RedirectAttributes attr,
			Model model) {

        if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.office",result);
        	attr.addFlashAttribute("office",office);
        	
        	return "redirect:/offices/new";
        }
		
		Office newOffice = officeService.create(office);
		model.addAttribute("office", newOffice);

		return "redirect:/offices/" + newOffice.getId();
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("{id}/edit")
	public String editOffice(@PathVariable(value = "id") Long officeId,Model model) {
		
		if (!model.containsAttribute("office")) {
			model.addAttribute("office", officeService.findById(officeId));
		}
		return OFFICE_EDIT_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping(path = "/{id}/update")
	public String updateOffice(@PathVariable(value = "id") Long officeId, @Valid Office officeDetails,BindingResult result,
			RedirectAttributes attr,  Model model) {

		  if(result.hasErrors()) {
	        	
	        	attr.addFlashAttribute("org.springframework.validation.BindingResult.office",result);
	        	attr.addFlashAttribute("office",officeDetails);
	        	officeService.update(officeId, officeDetails);
	    		model.addAttribute("office", officeService.findById(officeId));
	        	return "redirect:/offices/" + officeId;
	        }
		
		officeService.update(officeId, officeDetails);
		model.addAttribute("office", officeService.findById(officeId));
		return "redirect:/offices/" + officeId;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/{id}/delete")
	public String deleteOffice(@PathVariable("id") Long officeId) {
		officeService.delete(officeId);
		return "redirect:/offices";
	}
}
