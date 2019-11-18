package com.hampcode.articlesapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hampcode.articlesapp.model.Delivery;



public interface DeliveryService extends CrudService<Delivery, Long> {
	Page<Delivery> findAll(Pageable pageable);
}
