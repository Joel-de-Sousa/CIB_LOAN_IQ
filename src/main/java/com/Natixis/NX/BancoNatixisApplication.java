package com.Natixis.NX;

import com.Natixis.NX.entities.Transfer;
import com.Natixis.NX.service.TransferService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class BancoNatixisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancoNatixisApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(TransferService service){
		return args -> {
			service.save(new Transfer(LocalDate.parse("2024-02-27"), 200.0));
			service.save(new Transfer(LocalDate.parse("2024-03-27"), 1000.0));
			service.save(new Transfer(LocalDate.parse("2024-04-27"), 3000.0));
			service.save(new Transfer(LocalDate.parse("2024-02-29"), 1200.0));
		};
		}
	}


