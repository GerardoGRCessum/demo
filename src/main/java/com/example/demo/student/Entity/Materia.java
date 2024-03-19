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

public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @Column(name = "clave_materia")
    private String clave_materia;


    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
            name = "teachers_materias",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "clave_materia"),
            uniqueConstraints = {@UniqueConstraint(columnNames =
                    {"teacher_id", "clave_materia"})}
    )
    private List<Maestro> maestros;


}
