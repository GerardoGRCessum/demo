package com.example.demo.student.Service;

import com.example.demo.student.Entity.Grupo;
import com.example.demo.student.Entity.Maestro;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GrupoService {

    Optional<Grupo> findById(Long id);

    List<Grupo> findAll();

    Grupo save(Grupo grupo);
}
