package com.Jabai.WebShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "https://comix-frontend-jabaika-731663d931ec.herokuapp.com")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class WebShopApplication {

	public static void main(String[] args) {
		System.out.println("\n\n\n\nHello\n\n\n\n");
		SpringApplication.run(WebShopApplication.class, args);
	}

}
