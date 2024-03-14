package com.example.demo.student.Service;

import com.example.demo.student.Entity.Maestro;
import com.example.demo.student.Entity.Role;
import com.example.demo.student.Repository.MaestroRepository;
import com.example.demo.student.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaestroServiceImpl implements MaestroService{

    @Autowired
    private MaestroRepository maestroRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Maestro> findAll() {
        return (List<Maestro>) maestroRepository.findAll();
    }

    @Override
    @Transactional
    public Maestro save(Maestro maestro){
        Optional<Role> optionalRoleMaestro = roleRepository.findByName("ROLE_TEACHER");
        List<Role> roles = new ArrayList<>();

        optionalRoleMaestro.ifPresent(roles::add);

        if(maestro.isAdmin()){
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        maestro.setRoles(roles);
        maestro.setPassword(passwordEncoder.encode(maestro.getPassword()));
        return maestroRepository.save(maestro);
    }

    @Override
    @Transactional
    public Optional<Maestro> delete(Long id){
        Optional<Maestro> maestroOptional = maestroRepository.findById(id);
        maestroOptional.ifPresent(maestroDb -> {
            maestroRepository.delete(maestroDb);
        });
        return maestroOptional;
    }

    @Override
    @Transactional
    public Optional<Maestro> update(Long id, Maestro maestro){
        Optional<Maestro> maestroOptional = maestroRepository.findById(id);
        if(maestroOptional.isPresent()){
            Maestro maestroDb = maestroOptional.orElseThrow();
            maestroDb.setUsername(maestro.getUsername());
            maestroDb.setEmail(maestro.getEmail());
            maestroDb.setPassword(maestro.getPassword());
            maestroDb.setRoles(maestro.getRoles());
            maestroDb.setAdmin(maestro.isAdmin());
            maestroDb.setEnable(maestro.isEnable());
            maestroDb.setClave_materia(maestro.getClave_materia());
            return Optional.of(maestroRepository.save(maestroDb));
        }
        return maestroOptional;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }
}
