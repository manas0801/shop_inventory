package com.example.shop_inventory.bin;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Product {
	
	private int id;
	@NotNull
	private String name;
	@NotBlank
	private double price;
	@NotNull
	@Length(min = 0,max=10)
	private String desc;

	public Product(int id, String name, double price,String desc) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.desc  = desc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", Name=" + name + ", price=" + price + "]";
	}
	
	

}
