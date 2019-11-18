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
import com.hampcode.articlesapp.common.PageInitPaginationEmployee;
import com.hampcode.articlesapp.model.Employee;
import com.hampcode.articlesapp.service.EmployeeService;
import com.hampcode.articlesapp.service.OfficeService;
import com.hampcode.articlesapp.service.PositionService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	protected static final String EMPLOYEE_VIEW = "employees/showEmployee"; // view template for single employee
	protected static final String EMPLOYEE_ADD_FORM_VIEW = "employees/newEmployee"; // form for new employee
	protected static final String EMPLOYEE_EDIT_FORM_VIEW = "employees/editEmployee"; // form for editing an employee
	protected static final String EMPLOYEE_PAGE_VIEW = "employees/allEmployees"; // list with pagination
	protected static final String INDEX_VIEW = "index"; // employees with pagination

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private OfficeService officeService;

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private PageInitPaginationEmployee pageInitiPagination;
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public String getEmployeeById(@PathVariable(value = "id") Long employeeId, Model model) {
		model.addAttribute("employee", employeeService.findById(employeeId));
		return EMPLOYEE_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public ModelAndView getAllEmployees(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page
			) {
		ModelAndView modelAndView = 
				pageInitiPagination.initPagination(pageSize
						,page
						, EMPLOYEE_PAGE_VIEW);
		return modelAndView;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/new")
	public String newEmployee(Model model) {

		// in case of redirection model will contain employee
		if (!model.containsAttribute("employee")) {
			model.addAttribute("employee", new Employee());
			model.addAttribute("positions", this.positionService.getAll());
	        model.addAttribute("offices", this.officeService.getAll());
		}
		return EMPLOYEE_ADD_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/create")
	public String createEmployee(@Valid Employee employee, 
			BindingResult result,
			RedirectAttributes attr,
			Model model) {

        if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.employee",result);
        	attr.addFlashAttribute("employee",employee);
        	
        	return "redirect:/employees/new";
        }
		
		Employee newEmployee = employeeService.create(employee);
		model.addAttribute("employee", newEmployee);

		return "redirect:/employees/" + newEmployee.getId();
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("{id}/edit")
	public String editEmployee(@PathVariable(value = "id") Long employeeId, Model model) {
		/*
		 * in case of redirection from '/employee/{id}/update' model will contain employee
		 * with field values
		 */
		if (!model.containsAttribute("employee")) {
			model.addAttribute("employee", employeeService.findById(employeeId));
			model.addAttribute("positions", this.positionService.getAll());
	        model.addAttribute("offices", this.officeService.getAll());
		}
		return EMPLOYEE_EDIT_FORM_VIEW;
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping(path = "/{id}/update")
	public String updateEmployee(@PathVariable(value = "id") Long employeeId,@Valid Employee employeeDetails, 
			BindingResult result,
			RedirectAttributes attr, Model model) {

    if(result.hasErrors()) {
        	
        	attr.addFlashAttribute("org.springframework.validation.BindingResult.employee",result);
        	attr.addFlashAttribute("employee",employeeDetails);
        	employeeService.update(employeeId, employeeDetails);
    		model.addAttribute("employee", employeeService.findById(employeeId));
    		return "redirect:/employees/" + employeeId;
        }
		
		employeeService.update(employeeId, employeeDetails);
		model.addAttribute("employee", employeeService.findById(employeeId));
		return "redirect:/employees/" + employeeId;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/{id}/delete")
	public String deleteEmployee(@PathVariable("id") Long employeeId) {
		employeeService.delete(employeeId);
		return "redirect:/employees";
	}
}
