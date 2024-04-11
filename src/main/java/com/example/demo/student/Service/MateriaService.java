package com.example.demo.student.Service;

import com.example.demo.student.Entity.Materia;

import java.util.List;
import java.util.Optional;

public interface MateriaService {
    Optional<Materia> findById(Long idMateria);

    List<Materia> findAll();

    Materia save(Materia materia);
}
