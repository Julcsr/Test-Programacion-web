package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Delivery;
import com.hampcode.articlesapp.model.Order;
import com.hampcode.articlesapp.repository.DeliveryRepository;
import com.hampcode.articlesapp.service.DeliveryService;


@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Override
	public List<Delivery> getAll() {
		List<Delivery> deliverys = new ArrayList<>();
		deliveryRepository.findAll().iterator().forEachRemaining(deliverys::add);
		return deliverys;
	}

	@Override
	public Delivery create(Delivery entity) {
		Delivery newDelivery;
		double price=0;
		for(Order o:entity.getOrders()){
			price+=o.getPrice();
		}
		entity.setMonto(price);
		newDelivery = deliveryRepository.save(entity);
		return newDelivery;
	}

	@Override
	public Delivery update(Long id, Delivery entity) {
		Delivery delivery = findById(id);
		delivery.setCustomer(entity.getCustomer());
		delivery.setMotorized(entity.getMotorized());
		delivery.setRecepcionist(entity.getRecepcionist());
		delivery.setOrders(entity.getOrders());
		double price=0;
		for(Order o:entity.getOrders()){
			price+=o.getPrice();
		}
		delivery.setMonto(price);
		deliveryRepository.save(delivery);
		return delivery;
	}

	@Override
	public void delete(Long id) {
		deliveryRepository.delete(findById(id));
	}

	@Override
	public Delivery findById(Long id) {
		Optional<Delivery> delivery = deliveryRepository.findById(id);

		if (!delivery.isPresent()) {
			throw new ResourceNotFoundException("There is no Delivery with ID = " + id);
		}

		return delivery.get();

	}

	@Override
	public Page<Delivery> findAll(Pageable pageable) {
		return deliveryRepository.findAll(pageable);
	}

}
