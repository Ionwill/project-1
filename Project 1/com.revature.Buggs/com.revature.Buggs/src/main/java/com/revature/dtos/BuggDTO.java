package com.revature.dtos;

import java.util.List;

import com.revature.models.Bugg;
import com.revature.models.User;
import com.revature.models.UserRole;

public class BuggDTO {
	
	
	private int id;
	private String kind;
	private String fam;
	private String hab;
	private int price;
	private UserDTO user;

	
	public BuggDTO() {
		super();
	}
	
	public BuggDTO(Bugg bugg) {
		super();
		id = bugg.getid();
		kind = bugg.getKind();
		fam = bugg.getFam();
		hab = bugg.getHab();
		price = bugg.getPrice();
		user = new UserDTO(bugg.getSeller());
	}
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getFam() {
		return fam;
	}
	public void setFam(String fam) {
		this.fam = fam;
	}
	public String getHab() {
		return hab;
	}
	public void setHab(String hab) {
		this.hab = hab;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}


	
	
	
}
