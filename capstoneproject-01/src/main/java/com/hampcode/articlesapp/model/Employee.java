package com.hampcode.articlesapp.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @NotEmpty(message="Please enter a name")
	@Column(name = "name_employee", nullable = false, length = 50)
	private String name;
    @NotEmpty(message="Please enter a name")
	@Column(name = "apellido_paterno", nullable = false, length = 50)
    @NotEmpty(message="Please enter a name")
	private String apellidoPaterno;
    @NotEmpty(message="Please enter a name")
	@Column(name = "apellido_materno", nullable = false, length = 50)
	private String apellidoMaterno;
    @NotEmpty(message="Please enter a name")
	@Column(name = "employee_dni", nullable = false, length = 8, unique=true)
	private String dni;
	@ManyToOne
	@JoinColumn(name = "id_position")
	private Position position;
	@ManyToOne
	@JoinColumn(name = "id_office")
	private Office office;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

}
