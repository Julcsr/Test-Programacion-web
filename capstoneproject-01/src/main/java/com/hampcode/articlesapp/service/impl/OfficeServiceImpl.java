package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Office;
import com.hampcode.articlesapp.repository.OfficeRepository;
import com.hampcode.articlesapp.service.OfficeService;

@Service
public class OfficeServiceImpl implements OfficeService{
	@Autowired
	private OfficeRepository officeRepository;

	@Override
	public List<Office> getAll() {
		List<Office> offices = new ArrayList<>();
		officeRepository.findAll().iterator().forEachRemaining(offices::add);
		return offices;
	}

	@Override
	public Office create(Office entity) {
		Office newOffice;
		newOffice = officeRepository.save(entity);
		return newOffice;
	}

	@Override
	public Office update(Long id, Office entity) {
		Office office = findById(id);

		office.setAdress(entity.getAdress());
		office.setBoss(entity.getBoss());
		office.setEmail(entity.getEmail());
		office.setPhoneNumber(entity.getPhoneNumber());

		officeRepository.save(office);
		return office;
	}

	@Override
	public void delete(Long id) {
		officeRepository.delete(findById(id));
	}

	@Override
	public Office findById(Long id) {
		Optional<Office> office = officeRepository.findById(id);

		if (!office.isPresent()) {
			throw new ResourceNotFoundException("There is no Office with ID = " + id);
		}

		return office.get();

	}

	@Override
	public Page<Office> findAll(Pageable pageable) {
		return officeRepository.findAll(pageable);
	}
}
