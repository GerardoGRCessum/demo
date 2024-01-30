package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository){
        return arg -> {
            new Student(1L,
                    "mar",
                    "email", LocalDate.of(
                    2000, Month.JANUARY, 21));
        };
    }
}
