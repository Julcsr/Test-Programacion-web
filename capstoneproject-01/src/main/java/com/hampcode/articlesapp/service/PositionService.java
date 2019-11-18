package com.hampcode.articlesapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hampcode.articlesapp.model.Position;

public interface PositionService extends CrudService<Position, Long> {
	Page<Position> findAll(Pageable pageable);
}
