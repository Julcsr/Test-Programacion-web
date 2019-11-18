package com.hampcode.articlesapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hampcode.articlesapp.model.Order;

public interface OrderService  extends CrudService<Order, Long> {
	Page<Order> findAll(Pageable pageable);
}
