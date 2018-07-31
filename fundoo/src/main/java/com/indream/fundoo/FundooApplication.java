package com.indream.fundoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @purpose START POINT FOR THE FUNDOO APPLICATION
 * @author Akshay
 */
@SpringBootApplication
@EnableSwagger2
public class FundooApplication {
	public static void main(String[] args) {
		SpringApplication.run(FundooApplication.class, args);
	}// main ends
}// FundooApplication class ends
