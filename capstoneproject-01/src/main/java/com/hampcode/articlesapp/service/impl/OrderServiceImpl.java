package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Order;
import com.hampcode.articlesapp.repository.OrderRepository;
import com.hampcode.articlesapp.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> getAll() {
		List<Order> orders = new ArrayList<>();
		orderRepository.findAll().iterator().forEachRemaining(orders::add);
		return orders;
	}

	@Override
	public Order create(Order entity) {
		Order newOrder;
		newOrder = orderRepository.save(entity);
		return newOrder;
	}

	@Override
	public Order update(Long id, Order entity) {
		Order order = findById(id);
		order.setName(entity.getName());
		order.setPrice(entity.getPrice());
		order.setIngredients(entity.getIngredients());
		orderRepository.save(order);
		return order;
	}

	@Override
	public void delete(Long id) {
		orderRepository.delete(findById(id));
	}

	@Override
	public Order findById(Long id) {
		Optional<Order> order = orderRepository.findById(id);

		if (!order.isPresent()) {
			throw new ResourceNotFoundException("There is no Order with ID = " + id);
		}

		return order.get();

	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}
	
}
