package com.springmart.authuser;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication 
@EnableDiscoveryClient 
@EnableFeignClients
public class SpringmartAuthuserServiceApplication {
	public static void main(String[] a) {
		SpringApplication.run(SpringmartAuthuserServiceApplication.class,a);
	}
}