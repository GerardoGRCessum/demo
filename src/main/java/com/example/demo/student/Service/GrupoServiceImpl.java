package com.example.demo.student.Service;

import com.example.demo.student.Entity.Grupo;
import com.example.demo.student.Entity.Maestro;
import com.example.demo.student.Repository.GrupoRepository;
import com.example.demo.student.Repository.MaestroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoServiceImpl implements GrupoService {
    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    MaestroRepository maestroRepository;

    @Override
    public Optional<Grupo> findById(Long id) {
        return grupoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grupo> findAll() {
        return grupoRepository.findAll();
    }

    @Override
    public Grupo save(Grupo grupo) {
        return grupoRepository.save(grupo);
    }
}
