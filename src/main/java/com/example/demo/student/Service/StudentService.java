package com.example.demo.student.Service;

import com.example.demo.student.Entity.Student;
import com.example.demo.student.Repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.StringTemplate.STR;

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

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exist = studentRepository.existsById(studentId);
        if (!exist) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public Student updateStudent(Long studentId, String studentName, String studentEmail){

        boolean exist = studentRepository.existsById(studentId);
        if (!exist) {
           //String msg = STR."student with \{ studentId } not found.";
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        Optional<Student> dummy = studentRepository.findById(studentId);




        return null;
    }
}
