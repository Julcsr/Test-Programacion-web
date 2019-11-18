package com.hampcode.articlesapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hampcode.articlesapp.model.Customer;


@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>{
	Page<Customer> findAll(Pageable pageable);
}
