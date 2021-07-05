package br.com.nex2you.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CrudServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudServiceApplication.class, args);
	}

}
