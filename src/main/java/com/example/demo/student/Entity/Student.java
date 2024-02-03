package com.example.demo.student.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
@Table
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate dot;
    private Set rol;
    @Transient
    private Integer age;

    public Student() {
    }

    public Student(Long id, String name, String email, String pwd,LocalDate dot, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = pwd;
        this.dot = dot;
        this.rol = roles;
    }

    public Student(String name, String email, String pwd,LocalDate dot, Set<String> roles) {
        this.name = name;
        this.email = email;
        this.password = pwd;
        this.dot = dot;
        this.rol = roles;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDot() {
        return dot;
    }

    public void setDot(LocalDate dot) {
        this.dot = dot;
    }

    public Set getRol() {
        return rol;
    }

    public void setRol(Set rol) {
        this.rol = rol;
    }

    public Integer getAge() {
        return Period.between(this.dot, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dot=" + dot +
                ", rol=" + rol +
                ", age=" + age +
                '}';
    }
}
