package com.example.demo.student.Service;

import com.example.demo.student.Entity.Grupo;

import java.util.Optional;

public interface GrupoService {

    Optional<Grupo> findById(Long id);
}
