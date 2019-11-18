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

import com.hampcode.articlesapp.common.PageInitPaginationCustomer;
import com.hampcode.articlesapp.model.Customer;
import com.hampcode.articlesapp.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	protected static final String CUSTOMER_VIEW = "customers/showCustomer"; // view template for single customer
	protected static final String CUSTOMER_ADD_FORM_VIEW = "customers/newCustomer"; // form for new customer
	protected static final String CUSTOMER_EDIT_FORM_VIEW = "customers/editCustomer"; // form for editing an customer
	protected static final String CUSTOMER_PAGE_VIEW = "customers/allCustomers"; // list with pagination
	protected static final String INDEX_VIEW = "index"; // customers with pagination

	
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PageInitPaginationCustomer pageInitiPagination;

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public String getCustomerById(@PathVariable(value = "id") Long customerId, Model model) {
		model.addAttribute("customer", customerService.findById(customerId));
		return CUSTOMER_VIEW;
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public ModelAndView getAllCustomers(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page
			) {
		ModelAndView modelAndView = 
				pageInitiPagination.initPagination(pageSize
						,page
						, CUSTOMER_PAGE_VIEW);
		return modelAndView;
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/new")
	public String newCustomer(Model model) {

		// in case of redirection model will contain customer
		if (!model.containsAttribute("customer")) {
			model.addAttribute("customer", new Customer());
		}
		return CUSTOMER_ADD_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/create")
	public String createCustomer(@Valid Customer customer, 
			BindingResult result,
			RedirectAttributes attr,
			Model model) {

		
        if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.customer",result);
        	attr.addFlashAttribute("customer",customer);
        	
        	return "redirect:/customers/new";
        }

		
		Customer newCustomer = customerService.create(customer);
		model.addAttribute("customer", newCustomer);
		


		return "redirect:/customers/" + newCustomer.getId();
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("{id}/edit")
	public String editCustomer(@PathVariable(value = "id") Long customerId, Model model) {
		/*
		 * in case of redirection from '/customer/{id}/update' model will contain customer
		 * with field values
		 */
		if (!model.containsAttribute("customer")) {
			model.addAttribute("customer", customerService.findById(customerId));
		}
		return CUSTOMER_EDIT_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping(path = "/{id}/update")
	public String updateCustomer(@PathVariable(value = "id") Long customerId,@Valid Customer customerDetails,	BindingResult result,
			RedirectAttributes attr, Model model) {

		
		
        if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.customer",result);
        	attr.addFlashAttribute("customer",customerDetails);
    		customerService.update(customerId, customerDetails);
    		model.addAttribute("customer", customerService.findById(customerId));
        	return "redirect:/customers/" + customerId;
        }
		
		customerService.update(customerId, customerDetails);
		model.addAttribute("customer", customerService.findById(customerId));
		return "redirect:/customers/" + customerId;
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/{id}/delete")
	public String deleteCustomer(@PathVariable("id") Long customerId) {
		customerService.delete(customerId);
		return "redirect:/customers";
	}
	
	
}
