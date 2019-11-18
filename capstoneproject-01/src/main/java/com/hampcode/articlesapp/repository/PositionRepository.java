package com.hampcode.articlesapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.hampcode.articlesapp.model.Position;


@Repository
public interface PositionRepository extends PagingAndSortingRepository<Position, Long>{
	Page<Position> findAll(Pageable pageable);
}
