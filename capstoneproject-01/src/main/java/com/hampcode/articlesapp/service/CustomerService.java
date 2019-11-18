package com.hampcode.articlesapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hampcode.articlesapp.model.Customer;

public interface CustomerService  extends CrudService<Customer, Long> {
	Page<Customer> findAll(Pageable pageable);
}
