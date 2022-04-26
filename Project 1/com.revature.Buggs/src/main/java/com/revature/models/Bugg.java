package com.revature.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "buggs")
public class Bugg {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String kind;
	
	@Column
	private String fam;
	
	@Column
	private String hab;
	
	@Column
	private int price;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "seller_id")
	private User seller;

	public Bugg() {
		super();

	}


	public Bugg(int id, String kind, String fam, String hab, int price) {
	
	}


	public int getid() {
		return id;
	}


	public void setid(int id) {
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


	public User getSeller() {
		return seller;
	}


	public void setSeller(User seller) {
		this.seller = seller;
	}


	@Override
	public int hashCode() {
		return Objects.hash(fam, hab, id, kind, price, seller);
	}


	@Override
	public String toString() {
		return "Bugg [id=" + id + ", kind=" + kind + ", fam=" + fam + ", hab=" + hab + ", price=" + price + ", seller="
				+ seller + "]";
	}


	
	}


	


	