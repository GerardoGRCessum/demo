package com.example.demo.student.Repository;

import com.example.demo.student.Entity.Grupo;
import com.example.demo.student.Entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
}
