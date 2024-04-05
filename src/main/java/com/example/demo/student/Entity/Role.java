package com.example.demo.student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EnableAutoConfiguration
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;


    /*@JsonIgnoreProperties({"rol", "students","handler", "hibernateLazyInitializer"})
    @JsonIgnore

    @OneToMany(mappedBy = "rol",
               cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Student> students;*/

   /* public Role(){
        this.students = new ArrayList<>();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Role(String name){
        this.name = name;
    }

    /*public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Student> getPersonas() {
        return students;
    }

    public void setPersonas(List<Student> students) {
        this.students = students;
    }

     */

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!id.equals(role.id)) return false;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }*/
}
