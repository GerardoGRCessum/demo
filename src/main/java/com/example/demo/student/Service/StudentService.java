package com.example.demo.student.Service;

import com.example.demo.student.Entity.Student;

import java.util.List;
import java.util.Optional;


public interface StudentService {
    List<Student> findAll();

    Student save(Student student);

    Optional<Student> update(Long id, Student student);

    Optional<Student> findById(Long id);

    boolean existsByUsername(String username);


    Optional<Student> delete(Long id);

    Optional<Student> asignarClase(Long idStudent, Long idClase);

    Optional<Student> desactivarStudent(Long idStudent);

}
