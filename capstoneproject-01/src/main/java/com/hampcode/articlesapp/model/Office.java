package com.hampcode.articlesapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "offices")
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @NotEmpty(message="Please enter a name")
	@Column(name = "phone_number", nullable = false, length = 9)
	private int phoneNumber;
    @NotEmpty(message="Please enter a name")
	@Column(name = "adress", nullable = false, length = 50)
	private String adress;
    @NotEmpty(message="Please enter a name")
	@Column(name = "email", nullable =true, length = 50)
	private String email;
    @NotEmpty(message="Please enter a name")
	@Column(name = "boss", nullable = true, length = 50)
	private String boss;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBoss() {
		return boss;
	}
	public void setBoss(String boss) {
		this.boss = boss;
	}
	
	
}
