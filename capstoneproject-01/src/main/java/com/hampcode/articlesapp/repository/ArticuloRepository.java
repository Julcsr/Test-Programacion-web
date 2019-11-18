package com.hampcode.articlesapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hampcode.articlesapp.model.Articulo;

@Repository
public interface ArticuloRepository extends PagingAndSortingRepository<Articulo, Long> {

		Page<Articulo> findAll(Pageable pageable);
}
