package com.hampcode.articlesapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.hampcode.articlesapp.model.Office;


@Repository
public interface OfficeRepository extends PagingAndSortingRepository<Office, Long>{
	Page<Office> findAll(Pageable pageable);
}
