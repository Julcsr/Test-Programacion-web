package com.hampcode.articlesapp.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.hampcode.articlesapp.common.PageInitPaginationArticulo;
import com.hampcode.articlesapp.model.Articulo;
import com.hampcode.articlesapp.service.ArticuloService;

@Controller
@RequestMapping("/articulos")
public class ArticuloController {

	protected static final String ARTICULO_VIEW = "articulos/showArticulo"; // view template for single articulo
	protected static final String ARTICULO_ADD_FORM_VIEW = "articulos/newArticulo"; // form for new articulo
	protected static final String ARTICULO_EDIT_FORM_VIEW = "articulos/editArticulo"; // form for editing an articulo
	protected static final String ARTICULO_PAGE_VIEW = "articulos/allArticulos"; // list with pagination
	protected static final String INDEX_VIEW = "index"; // articulos with pagination

	@Autowired
	private ArticuloService articuloService;
	
	@Autowired
	private PageInitPaginationArticulo pageInitiPagination;

	@GetMapping("/{id}")
	public String getArticuloById(@PathVariable(value = "id") Long articuloId, Model model) {
		model.addAttribute("articulo", articuloService.findById(articuloId));
		return ARTICULO_VIEW;
	}

	@GetMapping
	public ModelAndView getAllArticulos(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page
			) {
		ModelAndView modelAndView = 
				pageInitiPagination.initPagination(pageSize
						,page
						, ARTICULO_PAGE_VIEW);
		return modelAndView;
	}

	@GetMapping("/new")
	public String newArticulo(Model model) {

		// in case of redirection model will contain articulo
		if (!model.containsAttribute("articulo")) {
			
			model.addAttribute("articulo", new Articulo());
			
		}
		return ARTICULO_ADD_FORM_VIEW;
	}

	@PostMapping("/create")
	public String createArticulo(@Valid Articulo articulo, 
			BindingResult result,
			RedirectAttributes attr,
			Model model) {

        if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.articulo",result);
        	attr.addFlashAttribute("articulo",articulo);
        	
        	return "redirect:/articulos/new";
        }
        articulo.setPrecio(articulo.getCostoMateriaPrima()*2);
		Articulo newArticulo = articuloService.create(articulo);
		model.addAttribute("articulo", newArticulo);

		return "redirect:/articulos/" + newArticulo.getId();
	}

	@GetMapping("{id}/edit")
	public String editArticulo(@PathVariable(value = "id") Long articuloId, Model model) {
		/*
		 * in case of redirection from '/articulo/{id}/update' model will contain articulo
		 * with field values
		 */
		if (!model.containsAttribute("articulo")) {
			model.addAttribute("articulo", articuloService.findById(articuloId));
		}
		return ARTICULO_EDIT_FORM_VIEW;
	}

	@PostMapping(path = "/{id}/update")
	public String updateArticulo(@PathVariable(value = "id") Long articuloId, Articulo articuloDetails, Model model) {

		articuloService.update(articuloId, articuloDetails);
		model.addAttribute("articulo", articuloService.findById(articuloId));
		return "redirect:/articulos/" + articuloId;
	}

	@GetMapping(value = "/{id}/delete")
	public String deleteArticulo(@PathVariable("id") Long articuloId) {
		articuloService.delete(articuloId);
		return "redirect:/articulos";
	}

}
