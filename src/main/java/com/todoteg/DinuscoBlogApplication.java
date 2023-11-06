package com.todoteg;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.todoteg.models.Article;
import com.todoteg.models.Role;
import com.todoteg.models.User;
import com.todoteg.repositories.IArticleRepo;
import com.todoteg.repositories.IUserRepo;

@SpringBootApplication
public class DinuscoBlogApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DinuscoBlogApplication.class, args);
	}
	
	@Autowired
	private IArticleRepo articleRepo;
	
	@Autowired
	private IUserRepo userRepo;

	@Override
	public void run(String... args) throws Exception {
		Long author = (long) 1;
		
        User newUser = User.builder()
        		.username("Todoteg")
        		.email("email")
        		.password("password")
        		.role(Role.ADMIN)
        		.enabled(true)
        		.build();
		
		Article newArt = Article.builder()
        		.author(author)
        		.slug("/slug")
        		.title("title")
        		.contentPreview("contentPreview")
        		.contentFull("contentFull")
        		.summary("contentFull")
        		.createDate(LocalDateTime.now())
        		.updateDate(LocalDateTime.now())
        		.enabled(true)
        		.build();
		
		//userRepo.save(newUser);
		//articleRepo.save(newArt);
		
	}

}
