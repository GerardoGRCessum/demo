package com.example.demo.student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;
import java.util.Objects;

@Entity
@EnableAutoConfiguration
@Table(name = "materias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"grupos"})
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    @NotBlank
    private String name;

    @Column(name = "clave_materia")
    private String clave_materia;


    @JsonIgnoreProperties(value = {"students", "maestros", "personas", "teacher", "grupos", "roles", "handler", "hibernateLazyInitializer"})
    @OneToMany(mappedBy = "materia")
    @ToString.Exclude
    private List<Grupo> grupos;
}
