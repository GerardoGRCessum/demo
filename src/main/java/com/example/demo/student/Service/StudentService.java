package com.example.demo.student.Service;

import com.example.demo.student.Entity.Student;
import com.example.demo.student.Repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository sr) {
        this.studentRepository = sr;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
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
    public Student updateStudent(Long studentId, String studentName, String studentEmail) {

        //Checar si el usuario existe
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new IllegalStateException("NO SE ENCONTRO"));

        if (studentName != null &&
                studentName.isEmpty() &&
                !studentName.equals(student.getName())) {
            student.setName(studentName);
        }
        if (studentEmail != null &&
                studentEmail.isEmpty()
                ) {
            Optional<Student> sOptional = studentRepository.findStudentByEmail(studentEmail);
            if (sOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            student.setEmail(studentEmail);
        }
        return studentRepository.save(student);

    }
}
