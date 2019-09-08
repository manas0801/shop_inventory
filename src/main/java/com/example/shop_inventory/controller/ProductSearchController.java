package com.example.shop_inventory.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.shop_inventory.aop.ReadOnlyTx;
import com.example.shop_inventory.aop.UpdateTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.shop_inventory.bin.Product;
import com.example.shop_inventory.service.*;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.example.shop_inventory.bin.priceInfo;

@RestController
@RequestMapping("/product")
public class ProductSearchController {
	
	@Autowired
	private productInfoService pis;
	
	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private RestTemplate Rest;

	@Autowired
	private KafkaTemplate kafkaTemplate;

	public static final  String TOPIC ="new_product";

	@GetMapping("/")
    @ReadOnlyTx
	public List<Product> getProduct(Model model) throws Exception {
		//model.addAttribute("products" ,pis.getProductInfo());
		 //model.addAttribute("products",jdbc.query("select id,name,price from Product", this::MapToProduct));
		 //return "product";
        throw new Exception();
		//return pis.getProductInfo();
	}
	
	@GetMapping("/search")
    @ReadOnlyTx
	public Product getProductSearch(@RequestParam(name= "name",required=false,defaultValue="baseProduct") String name ) {
		return pis.getProductInfo(name);
		//return jdbc.queryForObject("select id,name,desc from Product where name = ?", this::MapToProduct,name);
		
	}

	/*private Product MapToProduct(ResultSet rs, int rowNum)
    	    throws SQLException {

		priceInfo p =Rest.getForObject("http://localhost:8080/price/{product}",priceInfo.class,rs.getString("name"));

    	  return new Product(
    	      rs.getInt("id"),
    	      rs.getString("name"),
			  p.getPrice(),
    	      rs.getString("desc")
    	      );
    	}
	*/

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@UpdateTx
	public Product InsertProduct(@RequestParam(name="name") String name,
			                    @RequestParam(name="price")     String price,
								@RequestParam(name="desc") String desc, Model model)
	{
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(c->{
			PreparedStatement ps = c.prepareStatement("Insert into Product (name,desc) values (?,?)",
					new String[] {"id"});
        ps.setString(1, name);
        ps.setString(2, desc);
        return ps;
		},kh);
        priceInfo pi = new priceInfo();
        pi.setName(name);
        pi.setPrice(Float.parseFloat(price));

		//priceInfo p1 = Rest.postForObject("http://localhost:8090/price",pi,priceInfo.class);

        kafkaTemplate.send(TOPIC,pi);
		//return kh.getKey()+ " Successfully Inserted";

		return new Product(kh.getKey().intValue(),name,pi.getPrice(),desc);

	}
}
