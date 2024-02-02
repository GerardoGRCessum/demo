package com.example.demo.student.Config;

import com.example.demo.student.Entity.Student;
import com.example.demo.student.Repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository) {
        return arg -> {
            Student gar = new Student(1L,
                    "gar",
                    "garmail","0705" ,LocalDate.of(
                    1998, Month.APRIL, 22));

            repository.saveAll(
                    List.of(gar)
            );
        };

    }
}
