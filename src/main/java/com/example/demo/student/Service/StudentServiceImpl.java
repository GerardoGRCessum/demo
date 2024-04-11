package com.example.demo.student.Service;

import com.example.demo.student.Entity.Grupo;
import com.example.demo.student.Entity.Role;
import com.example.demo.student.Entity.Student;
import com.example.demo.student.Repository.GrupoRepository;
import com.example.demo.student.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.student.Repository.studentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private studentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    @Transactional
    public Student save(Student student) {
        Optional<Role> optionalRoleStudent = roleRepository.findByName("ROLE_USER");
        Role rol = optionalRoleStudent.orElseThrow();
       // List<Role> roles = new ArrayList<>();
       // optionalRoleStudent.ifPresent(roles::add);
       // student.setRoles(roles);
        student.setRol(rol);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Transactional
    @Override
    public Optional<Student> delete(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(studentDb -> {
            studentRepository.delete(studentDb);
        });
        return studentOptional;
    }

    @Override
    @Transactional
    public Optional<Student> update(Long id, Student student){
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isPresent()){
            Student studentDb = studentOptional.orElseThrow();
            studentDb.setUsername(student.getUsername());
            studentDb.setEmail(student.getEmail());
            studentDb.setGrupos(student.getGrupos());
            return Optional.of(studentRepository.save(studentDb));
        }
        return studentOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    /*public Optional<Student> getStudentById(String studentName){
        long id = studentRepository.findBy()

        return studentRepository.findById(studentName);
    }*/

    @Override
    public Optional<Student> asignarClase(Long idStudent, Long idClase) {
        Optional<Student> studentOptional = studentRepository.findById(idStudent);
        Optional<Grupo> grupoOptional = grupoRepository.findById(idClase);
        Set<Grupo> grupos;
        if (studentOptional.isPresent()) {
            Student stuDb = studentOptional.orElseThrow();
            grupos = stuDb.getGrupos();
            Grupo gruDb = grupoOptional.orElseThrow();
            grupos.add(gruDb);
            stuDb.setGrupos(grupos);
            return Optional.of(studentRepository.save(stuDb));
        }
        return studentOptional;
    }

    @Override
    public Optional<Student> desactivarStudent(Long idStudent){
        Optional<Student> studentOptional = studentRepository.findById(idStudent);
        if (studentOptional.isPresent()){
            Student studb = studentOptional.orElseThrow();
            studb.setEnable(false);
            return Optional.of(studentRepository.save(studb));
        }
        return studentOptional;
    }
}
