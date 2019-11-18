package com.hampcode.articlesapp.controller;

import java.util.ArrayList;
import java.util.List;
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
import com.hampcode.articlesapp.common.PageInitPaginationDelivery;
import com.hampcode.articlesapp.model.Delivery;
import com.hampcode.articlesapp.model.Employee;
import com.hampcode.articlesapp.model.Order;
import com.hampcode.articlesapp.service.CustomerService;
import com.hampcode.articlesapp.service.DeliveryService;
import com.hampcode.articlesapp.service.EmployeeService;

import com.hampcode.articlesapp.service.OrderService;


@Controller
@RequestMapping("/deliverys")
public class DeliveryController {
	protected static final String DELIVERY_VIEW = "deliverys/showDelivery"; // view template for single delivery
	protected static final String DELIVERY_ADD_FORM_VIEW = "deliverys/newDelivery"; // form for new delivery
	protected static final String DELIVERY_EDIT_FORM_VIEW = "deliverys/editDelivery"; // form for editing an delivery
	protected static final String DELIVERY_PAGE_VIEW = "deliverys/allDeliverys"; // list with pagination
	protected static final String INDEX_VIEW = "index"; // deliverys with pagination

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private PageInitPaginationDelivery pageInitiPagination;

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public String getDeliveryById(@PathVariable(value = "id") Long deliveryId, Model model) {
		model.addAttribute("delivery", deliveryService.findById(deliveryId));
		return DELIVERY_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public ModelAndView getAllDeliverys(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		ModelAndView modelAndView = pageInitiPagination.initPagination(pageSize, page, DELIVERY_PAGE_VIEW);
		return modelAndView;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/new")
	public String newDelivery(Model model) {

		// in case of redirection model will contain delivery
		if (!model.containsAttribute("delivery")) {
			model.addAttribute("delivery", new Delivery());

			List<Employee> motorizeds = new ArrayList<>();
			List<Employee> recepcionists = new ArrayList<>();
			for (Employee e : employeeService.getAll()) {
				if("Motorizado".equals(e.getPosition().getName())) {
					motorizeds.add(e);
				}else {
					recepcionists.add(e);
				}
			}
			model.addAttribute("motorizeds", motorizeds);
			model.addAttribute("recepcionists", recepcionists);
			List<Order> orders = orderService.getAll();
			model.addAttribute("orders", orders);
			model.addAttribute("customers", customerService.getAll());
		}
		return DELIVERY_ADD_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/create")
	public String createDelivery(@Valid Delivery delivery, BindingResult result, RedirectAttributes attr, Model model) {

		if (result.hasErrors()) {

			attr.addFlashAttribute("org.springframework.validation.BindingResult.delivery", result);
			attr.addFlashAttribute("delivery", delivery);

			return "redirect:/deliverys/new";
		}

		Delivery newDelivery = deliveryService.create(delivery);
		model.addAttribute("delivery", newDelivery);

		return "redirect:/deliverys/" + newDelivery.getId();
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("{id}/edit")
	public String editDelivery(@PathVariable(value = "id") Long deliveryId, Model model) {
		/*
		 * in case of redirection from '/delivery/{id}/update' model will contain
		 * delivery with field values
		 */
		if (!model.containsAttribute("delivery")) {
			model.addAttribute("delivery", deliveryService.findById(deliveryId));
			List<Employee> motorizeds = new ArrayList<>();
			List<Employee> recepcionists = new ArrayList<>();
			for (Employee e : employeeService.getAll()) {
				if("Motorizado".equals(e.getPosition().getName())) {
					motorizeds.add(e);
				}else {
					recepcionists.add(e);
				}
			}
			model.addAttribute("motorizeds", motorizeds);
			model.addAttribute("recepcionists", recepcionists);
			List<Order> orders = orderService.getAll();
			model.addAttribute("orders", orders);
			model.addAttribute("customers", customerService.getAll());
		}
		return DELIVERY_EDIT_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping(path = "/{id}/update")
	public String updateDelivery(@PathVariable(value = "id") Long deliveryId,@Valid Delivery deliveryDetails, BindingResult result, RedirectAttributes attr, Model model) {

		if (result.hasErrors()) {

			attr.addFlashAttribute("org.springframework.validation.BindingResult.delivery", result);
			attr.addFlashAttribute("delivery", deliveryDetails);
			deliveryService.update(deliveryId, deliveryDetails);
			model.addAttribute("delivery", deliveryService.findById(deliveryId));
			return "redirect:/deliverys/" + deliveryId;
		}

		
		deliveryService.update(deliveryId, deliveryDetails);
		model.addAttribute("delivery", deliveryService.findById(deliveryId));
		return "redirect:/deliverys/" + deliveryId;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/{id}/delete")
	public String deleteDelivery(@PathVariable("id") Long deliveryId) {
		deliveryService.delete(deliveryId);
		return "redirect:/deliverys";
	}
}
