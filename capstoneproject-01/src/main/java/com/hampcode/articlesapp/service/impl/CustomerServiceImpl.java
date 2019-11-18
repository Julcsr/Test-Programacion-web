package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;

import com.hampcode.articlesapp.model.Customer;
import com.hampcode.articlesapp.repository.CustomerRepository;
import com.hampcode.articlesapp.service.CustomerService;


@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> getAll() {
		List<Customer> customers = new ArrayList<>();
		customerRepository.findAll().iterator().forEachRemaining(customers::add);
		return customers;
	}



	@Override
	public Customer create(Customer entity) {

		Customer newCustomer;
		newCustomer = customerRepository.save(entity);
		return newCustomer;
	}

	@Override
	public Customer update(Long id, Customer entity) {
		Customer customer = findById(id);
		customer.setName(entity.getName());
		customer.setApellidoPaterno(entity.getApellidoPaterno());
		customer.setApellidoMaterno(entity.getApellidoMaterno());
		customer.setPhone(entity.getPhone());
		customer.setAddress(entity.getAddress());
		//customer.setEmail(entity.getEmail());
		customerRepository.save(customer);
		return customer;
	}

	@Override
	public void delete(Long id) {
		customerRepository.delete(findById(id));
	}

	@Override
	public Customer findById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (!customer.isPresent()) {
			throw new ResourceNotFoundException("There is no Customer with ID = " + id);
		}

		return customer.get();

	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}
	
}
