package com.example.shop_inventory.service;

import java.util.List;

import com.example.shop_inventory.bin.Product;

public interface productInfoService {
	
	public Product getProductInfo(String name);
	
	public List<Product> getProductInfo();

}
