package de.ij3rry.chatApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ChatAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatAppApplication.class, args);
	}
}
