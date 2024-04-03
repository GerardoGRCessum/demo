package com.example.demo.student.Service;

import com.example.demo.student.Entity.Maestro;

import java.util.List;
import java.util.Optional;

public interface MaestroService {
    List<Maestro> findAll();

    Maestro save(Maestro meastro);

    Optional<Maestro> update(Long id, Maestro maestro);

    boolean existsByUsername(String username);

    Optional<Maestro> delete(Long id);

    Optional<Maestro> asignarGrupo(Long idMaestro, Long idClase);
}
