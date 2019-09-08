package com.example.shop_inventory.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.shop_inventory.aop.LogExecutionTime;
import com.example.shop_inventory.aop.ReadOnlyTx;
import com.example.shop_inventory.bin.priceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.shop_inventory.bin.Product;
import com.example.shop_inventory.service.productInfoService;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Service
public class productInfoServiceImpl implements  productInfoService {

	private  JdbcTemplate jdbc;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private RestTemplate Rest;

	@LogExecutionTime
	public Product getProductInfo(String name){
		return jdbc.queryForObject("select id,name,desc from Product where name = ?", this::MapToProduct,name);
    	

    	
    }


	@LogExecutionTime
	private Product MapToProduct(ResultSet rs, int rowNum)
			throws SQLException {

		priceInfo p =Rest.getForObject("http://localhost:8090/price/{product}",priceInfo.class,rs.getString("name"));

		return new Product(
				rs.getInt("id"),
				rs.getString("name"),
				p.getPrice(),
				rs.getString("desc")
		);
	}
    @LogExecutionTime
    public List<Product> getProductInfo(){

    	jdbc = new JdbcTemplate(dataSource);
    	return jdbc.query("select id,name,desc from product", this::MapToProduct);
    	
    }

}
