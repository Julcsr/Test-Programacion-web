package com.hampcode.articlesapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hampcode.articlesapp.model.Office;

public interface OfficeService extends CrudService<Office, Long>{
	Page<Office> findAll(Pageable pageable);
}
