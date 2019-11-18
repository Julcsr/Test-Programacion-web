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
import com.hampcode.articlesapp.common.PageInitPaginationOrder;
import com.hampcode.articlesapp.model.Order;
import com.hampcode.articlesapp.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
	protected static final String ORDER_VIEW = "orders/showOrder"; // view template for single order
	protected static final String ORDER_ADD_FORM_VIEW = "orders/newOrder"; // form for new order
	protected static final String ORDER_EDIT_FORM_VIEW = "orders/editOrder"; // form for editing an order
	protected static final String ORDER_PAGE_VIEW = "orders/allOrders"; // list with pagination
	protected static final String INDEX_VIEW = "index"; // orders with pagination

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PageInitPaginationOrder pageInitiPagination;
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public String getOrderById(@PathVariable(value = "id") Long orderId, Model model) {
		model.addAttribute("order", orderService.findById(orderId));
		return ORDER_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public ModelAndView getAllOrders(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page
			) {
		ModelAndView modelAndView = 
				pageInitiPagination.initPagination(pageSize
						,page
						, ORDER_PAGE_VIEW);
		return modelAndView;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/new")
	public String newOrder(Model model) {

		// in case of redirection model will contain order
		if (!model.containsAttribute("order")) {
			model.addAttribute("order", new Order());
		}
		return ORDER_ADD_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/create")
	public String createOrder(@Valid Order order, 
			BindingResult result,
			RedirectAttributes attr,
			Model model) {

        if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.order",result);
        	attr.addFlashAttribute("order",order);
        	
        	return "redirect:/orders/new";
        }
		
		Order newOrder = orderService.create(order);
		model.addAttribute("order", newOrder);

		return "redirect:/orders/" + newOrder.getId();
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("{id}/edit")
	public String editOrder(@PathVariable(value = "id") Long orderId, Model model) {
		/*
		 * in case of redirection from '/order/{id}/update' model will contain order
		 * with field values
		 */
		if (!model.containsAttribute("order")) {
			model.addAttribute("order", orderService.findById(orderId));
		}
		return ORDER_EDIT_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping(path = "/{id}/update")
	public String updateOrder(@PathVariable(value = "id") Long orderId, @Valid Order orderDetails, 
			BindingResult result,
			RedirectAttributes attr, Model model) {

        if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.order",result);
        	attr.addFlashAttribute("order",orderDetails);
        	orderService.update(orderId, orderDetails);
    		model.addAttribute("order", orderService.findById(orderId));
    		return "redirect:/orders/" + orderId;
        }
		
		orderService.update(orderId, orderDetails);
		model.addAttribute("order", orderService.findById(orderId));
		return "redirect:/orders/" + orderId;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/{id}/delete")
	public String deleteOrder(@PathVariable("id") Long orderId) {
		orderService.delete(orderId);
		return "redirect:/orders";
	}
}
