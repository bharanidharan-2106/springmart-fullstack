package com.springmart.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringmartApiGatewayApplication {
 public static void main(String[] args) {
	 SpringApplication.run(SpringmartApiGatewayApplication.class,args);
	 }
}
