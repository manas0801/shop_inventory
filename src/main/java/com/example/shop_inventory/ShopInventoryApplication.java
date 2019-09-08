package com.example.shop_inventory;

import javax.sql.DataSource;

import com.example.shop_inventory.bin.ExceptionBean;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.example.shop_inventory.bin.priceInfo;
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages= {"com.example.shop_inventory"})
		public class ShopInventoryApplication {


	public static void main(String[] args) {
		SpringApplication.run(ShopInventoryApplication.class, args);}

	
@Bean(name="dataSource")
public DataSource dataSource() {
	
	return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("schema.sql").build();

}



@Bean
public RestTemplate restTemplate() {

	return new RestTemplate();

}



@Bean
public ExceptionBean exceptionBean(){

    return new ExceptionBean ();
}

/*@ControllerAdvice
    public class globalExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value={Exception.class})
    public ResponseEntity<Object> handleExceptions(Exception e){
        String msg = "Exception happenned";
        exceptionBean().setHttp(HttpStatus.INTERNAL_SERVER_ERROR);
        exceptionBean().setErrMsg(msg);
        exceptionBean().setE(e);

        //String msg = "Exception happenned";
       return new ResponseEntity<>(exceptionBean(),HttpStatus.INTERNAL_SERVER_ERROR);
        //return new ResponseEntity<>(e,HttpStatus.EXPECTATION_FAILED);
    }
}*/

@Bean
public ProducerFactory<String,priceInfo>  producerFactory(){

        Map<String ,Object > config = new HashMap();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);

    }

    @Bean
public KafkaTemplate<String,priceInfo>     kafkaTemplate(){
	    return new KafkaTemplate<>(producerFactory());
}




}

