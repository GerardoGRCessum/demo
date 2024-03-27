package com.example.demo.student.Service;

import com.example.demo.student.Entity.Role;
import com.example.demo.student.Entity.Student;
import com.example.demo.student.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.student.Repository.studentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private studentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

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
        //List<Role> roles = new ArrayList<>();
        //optionalRoleStudent.ifPresent(roles::add);
        Role rol = new Role("ROLE_USER");

        //student.setRoles(roles);
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
        if (studentOptional.isPresent()){
            Student studentDb = studentOptional.orElseThrow();

            studentDb.setUsername(student.getUsername());
            studentDb.setEmail(student.getEmail());
            studentDb.setPassword(student.getPassword());
            studentDb.setAdmin(student.isAdmin());
            studentDb.setEnable(student.isEnable());
            return Optional.of(studentRepository.save(studentDb));
        }
        return studentOptional;
    }


    /*public Optional<Student> getStudentById(String studentName){
        long id = studentRepository.findBy()

        return studentRepository.findById(studentName);
    }*/




}
