package com.hampcode.articlesapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hampcode.articlesapp.model.Articulo;

public interface ArticuloService extends CrudService<Articulo, Long>{
	Page<Articulo> findAll(Pageable pageable);
}
