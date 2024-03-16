package com.example.demo.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.example")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    /*TODO:
     * CAMBIAR API A AuthentificationController.
     * COMENTAR CLASES.
     * INVESTIGAR CORS.
     * AGREGAR ENTIDADES DE MATERIAS, PROFESORES
     * RELACIONAR LAS TABLAS.
     */

}
