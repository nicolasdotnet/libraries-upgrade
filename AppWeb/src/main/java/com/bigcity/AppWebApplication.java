/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity;

import com.bigcity.beans.Book;
import com.bigcity.services.interfaces.IBookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nicolasdotnet
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class AppWebApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private IBookService iBookService;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(AppWebApplication.class, args);
    }

    public void run(String... args) throws Exception {

        String author = "Nicolas";
        String bookTitle = "";
        String isbn = "";
        String categoryName = "";

        int i = 0;

        Page<Book> r = iBookService.getAllBooksByCriteria(isbn, author, bookTitle, categoryName, 0, 5);

        if (r.isEmpty()) {

            System.out.println("com.bigcity.AppWebApplication.run()" + r.isEmpty());

        } else {

            System.out.println("com.bigcity.AppWebApplication.run()" + r.isEmpty());
            
            

            List<Book> l = r.toList();
                    
                    //r.getContent();

            for (Book book : l) {

                i = ++i;

                System.out.println("affichage : " + i + " " + book.toString());
            }
        }
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }
}
