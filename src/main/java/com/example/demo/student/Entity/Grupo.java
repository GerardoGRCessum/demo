package com.example.demo.student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;
import java.util.Objects;

@Entity
@EnableAutoConfiguration
@Table(name = "clases")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clase")
    private Long id;

    @ManyToMany(mappedBy = "grupos",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JsonIgnoreProperties({"roles", "id", "students","maestros","student","teacher","grupos","handler", "hibernateLazyInitializer"})
    @ToString.Exclude
    private List<Student> students;

    @ManyToOne
    @JsonIgnoreProperties({"students","maestros","student","teacher","grupos","handler", "hibernateLazyInitializer"})
    @JoinColumn(name = "id_materia")
    @ToString.Exclude
    private Materia materia;

    @ManyToOne
    @JsonIgnoreProperties({"students","maestros","personas","teacher","grupos","handler", "hibernateLazyInitializer"})
    @JoinColumn(name = "id_teacher")
    private Maestro teacher;

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grupo grupo = (Grupo) o;
        return Objects.equals(id, grupo.id) && Objects.equals(students, grupo.students) && Objects.equals(materia, grupo.materia) && Objects.equals(teacher, grupo.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, students, materia, teacher);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grupo grupo = (Grupo) o;
        return Objects.equals(id, grupo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
