package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Employee;
import com.hampcode.articlesapp.repository.EmployeeRepository;
import com.hampcode.articlesapp.service.EmployeeService;


@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<>();
		employeeRepository.findAll().iterator().forEachRemaining(employees::add);
		return employees;
	}

	@Override
	public Employee create(Employee entity) {
		Employee newEmployee;
		newEmployee = employeeRepository.save(entity);
		return newEmployee;
	}

	@Override
	public Employee update(Long id, Employee entity) {
		Employee employee = findById(id);
		employee.setName(entity.getName());
		employee.setApellidoPaterno(entity.getApellidoPaterno());
		employee.setApellidoMaterno(entity.getApellidoMaterno());
		employee.setDni(entity.getDni());
		employee.setPosition(entity.getPosition());
		employee.setOffice(entity.getOffice());
		employeeRepository.save(employee);
		return employee;
	}

	@Override
	public void delete(Long id) {
		employeeRepository.delete(findById(id));
	}

	@Override
	public Employee findById(Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);

		if (!employee.isPresent()) {
			throw new ResourceNotFoundException("There is no Employee with ID = " + id);
		}

		return employee.get();

	}

	@Override
	public Page<Employee> findAll(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	@Override
	public List<Employee> fechfindByDni(String dni) {
		// TODO Auto-generated method stub
		return null;
	}
}
