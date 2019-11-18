package com.hampcode.articlesapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hampcode.articlesapp.model.Order;


@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{
	Page<Order> findAll(Pageable pageable);
}
