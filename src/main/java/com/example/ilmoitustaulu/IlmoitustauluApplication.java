package com.example.ilmoitustaulu;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.ilmoitustaulu.domain.Category;
import com.example.ilmoitustaulu.domain.CategoryRepository;
import com.example.ilmoitustaulu.domain.Message;
import com.example.ilmoitustaulu.domain.MessageRepository;
import com.example.ilmoitustaulu.domain.User;
import com.example.ilmoitustaulu.domain.UserRepository;

@SpringBootApplication
public class IlmoitustauluApplication {

	public static void main(String[] args) {
		SpringApplication.run(IlmoitustauluApplication.class, args);
	}
	
	// Lisätään esimerkkidataa ja käyttäjiä
	@Bean
	public CommandLineRunner run(MessageRepository messagerepository, CategoryRepository categoryrepository, UserRepository userrepository) {
		return (args) -> {
		
			// Lisätään mahdolliset ilmoituskategoriat jotka voidaan valita ilmoitusta tehdessä
			categoryrepository.save(new Category("Ostetaan"));
			categoryrepository.save(new Category("Myydään"));
			categoryrepository.save(new Category("Vaihdetaan"));
		
			// Luodaan esimerkki date-olio 10.10.2020
			SimpleDateFormat dateformat = new java.text.SimpleDateFormat("yyyy-MM-dd");
			Date date = dateformat.parse("2020-10-10");
			
			// Lisätään esimerkki-ilmoitukset
			messagerepository.save(new Message(1, "Myydään tietokone", "Myynnissä tehokas vähän käytetty kannettava hintaan 500e, ota yhteyttä puh 123123123", "Käyttäjä1", date, categoryrepository.findByName("Myydään").get(0)));
			date = dateformat.parse("2020-10-15"); // Vaihdetaan päivämäärää
			messagerepository.save(new Message(2, "Ostetaan kaiuttimet", "Ostetaan kaiuttimet ota yhteyttä käyttäjä@sposti.fi", "Käyttäjä2", date, categoryrepository.findByName("Ostetaan").get(0)));
			date = dateformat.parse("2020-10-20");
			messagerepository.save(new Message(3, "Vaihdetaan näytönohjain", "Vaihdetaan vähemmän tehokkaaseen + väliraha", "Käyttäjä3", date, categoryrepository.findByName("Vaihdetaan").get(0)));

			User user1 = new User("user","$2a$10$Q0RjYHYEdKN5GtazW0oJIeuAa8em3P6tNrp/Mc5TMcyue18jSZEQm","USER"); // Luodaan käyttäjä salasanalla "user"
			User user2 = new User("admin","$2a$10$0sq64EkoJE49ek6uN7eR9OaaNZuy/MmgClEaCLAq4b.5MjY8kKe2O","ADMIN"); // Luodaan admin-käyttäjä salasanalla "admin"
			userrepository.save(user1);
			userrepository.save(user2);
		};
	}
}
