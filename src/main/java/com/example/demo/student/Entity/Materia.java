package com.example.demo.student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(name = "nombre")
    @NotBlank
    private String name;

    @Column(name = "clave_materia")
    private String clave_materia;


    @JsonIgnoreProperties(value = {"students", "maestros", "personas", "teacher", "grupos", "roles", "handler", "hibernateLazyInitializer"})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "materia")
    @ToString.Exclude
    private List<Grupo> grupos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materia materia = (Materia) o;
        return Objects.equals(id, materia.id) && Objects.equals(name, materia.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
