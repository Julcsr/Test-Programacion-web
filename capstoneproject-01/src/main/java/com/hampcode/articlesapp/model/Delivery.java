package com.hampcode.articlesapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "deliveries")
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_customer")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "id_motorized")
	private Employee motorized;

	@ManyToOne
	@JoinColumn(name = "id_recepcionist")
	private Employee recepcionist;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "delivery_order", joinColumns = { @JoinColumn(name = "delivery_id") }, inverseJoinColumns = {
			@JoinColumn(name = "order_id") })
	private List<Order> orders = new ArrayList<>();

	private double monto;

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getMotorized() {
		return motorized;
	}

	public void setMotorized(Employee motorized) {
		this.motorized = motorized;
	}

	public Employee getRecepcionist() {
		return recepcionist;
	}

	public void setRecepcionist(Employee recepcionist) {
		this.recepcionist = recepcionist;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
