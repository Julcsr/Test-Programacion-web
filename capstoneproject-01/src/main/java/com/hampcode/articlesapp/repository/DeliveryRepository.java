package com.hampcode.articlesapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.hampcode.articlesapp.model.Delivery;


@Repository
public interface DeliveryRepository extends PagingAndSortingRepository<Delivery, Long>{

	Page<Delivery> findAll(Pageable pageable);
	
}
