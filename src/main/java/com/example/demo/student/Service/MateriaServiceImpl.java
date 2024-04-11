package com.example.demo.student.Service;

import com.example.demo.student.Entity.Materia;
import com.example.demo.student.Repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaServiceImpl implements MateriaService{

    @Autowired
    MateriaRepository materiaRepository;

    @Override
    public Optional<Materia> findById(Long idMateria) {
        return materiaRepository.findById(idMateria);
    }

    @Override
    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    @Override
    public Materia save(Materia materia) {
        return materiaRepository.save(materia);
    }
}
