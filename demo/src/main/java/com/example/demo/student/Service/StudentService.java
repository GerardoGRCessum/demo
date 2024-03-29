package com.example.demo.student.Service;

import com.example.demo.student.Entity.Student;

import java.util.List;
import java.util.Optional;


public interface StudentService {
    List<Student> findAll();

    Student save(Student student);

    boolean existsByUsername(String username);

    Optional<Student> delete(Long id);

}
