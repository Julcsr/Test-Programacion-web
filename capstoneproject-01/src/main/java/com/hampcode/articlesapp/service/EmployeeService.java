package com.hampcode.articlesapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hampcode.articlesapp.model.Employee;



public interface EmployeeService extends CrudService<Employee, Long> {
	public List<Employee> fechfindByDni(String dni);
	Page<Employee> findAll(Pageable pageable);
}
