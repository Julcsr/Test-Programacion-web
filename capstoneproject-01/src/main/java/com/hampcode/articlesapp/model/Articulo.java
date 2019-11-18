package com.hampcode.articlesapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articulos")
public class Articulo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int clave;
	private double costoMateriaPrima;
	private double precio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	public double getCostoMateriaPrima() {
		return costoMateriaPrima;
	}

	public void setCostoMateriaPrima(double costoMateriaPrima) {
		this.costoMateriaPrima = costoMateriaPrima;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
