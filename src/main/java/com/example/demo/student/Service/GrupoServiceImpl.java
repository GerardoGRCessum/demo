package com.example.demo.student.Service;

import com.example.demo.student.Entity.Grupo;
import com.example.demo.student.Repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GrupoServiceImpl implements GrupoService {
    @Autowired
    GrupoRepository grupoRepository;
    @Override
    public Optional<Grupo> findById(Long id) {
        return grupoRepository.findById(id);
    }
}
