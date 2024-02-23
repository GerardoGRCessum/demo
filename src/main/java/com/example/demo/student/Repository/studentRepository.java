package com.example.demo.student.Repository;

import com.example.demo.student.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface studentRepository extends JpaRepository<Student, Long> {

    boolean existsByusername(String username);

    Optional<Student> findByEmail(String email);

    Optional<Student> findByUsername(String username);
}
