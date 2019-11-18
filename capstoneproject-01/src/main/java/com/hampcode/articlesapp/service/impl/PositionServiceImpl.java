package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Position;
import com.hampcode.articlesapp.repository.PositionRepository;
import com.hampcode.articlesapp.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionRepository positionRepository;

	@Override
	public List<Position> getAll() {
		List<Position> positions = new ArrayList<>();
		positionRepository.findAll().iterator().forEachRemaining(positions::add);
		return positions;
	}

	@Override
	public Position create(Position entity) {
		Position newPosition;
		newPosition = positionRepository.save(entity);
		return newPosition;
	}

	@Override
	public Position update(Long id, Position entity) {
		Position position = findById(id);

		position.setName(entity.getName());
		position.setDescription(entity.getDescription());

		positionRepository.save(position);
		return position;
	}

	@Override
	public void delete(Long id) {
		positionRepository.delete(findById(id));
	}

	@Override
	public Position findById(Long id) {
		Optional<Position> position = positionRepository.findById(id);

		if (!position.isPresent()) {
			throw new ResourceNotFoundException("There is no Position with ID = " + id);
		}

		return position.get();

	}

	@Override
	public Page<Position> findAll(Pageable pageable) {
		return positionRepository.findAll(pageable);
	}
}
