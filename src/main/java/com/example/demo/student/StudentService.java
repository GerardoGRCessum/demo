package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository sr) {
        this.studentRepository = sr;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }
}
