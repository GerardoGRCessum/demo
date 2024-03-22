package com.example.demo.student.Service;

import com.example.demo.student.Entity.Student;

import java.util.List;
import java.util.Optional;


public interface StudentService {
    List<Student> findAll();

    Student save(Student student);

    Optional<Student> update(Long id, Student student);

    boolean existsByUsername(String username);

    //TODO: ELIMINAR EN FUTURAS UPDATES
    Optional<Student> delete(Long id);

}
