package com.example.demo.student.Controller;

import com.example.demo.student.Entity.Student;
import com.example.demo.student.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(originPatterns= "*")
@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getStudents() {
        return studentService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Student student, BindingResult result) {
        if (result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.save(student));
    }
//{"application/xml", "application/json", "application/x-www-form-urlencoded;charset=UTF-8"}
    @PostMapping(path = "/register", consumes = {"application/xml", "application/json", "application/x-www-form-urlencoded;charset=UTF-8"})
    public ResponseEntity<?> register(@Valid @RequestBody Student student, BindingResult result){
        student.setAdmin(false);
        return create(student, result);
    }



    @PreAuthorize("hasRoles('ADMIN')")
    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") Long studentId) {
        Optional<Student> studentOptional = studentService.delete(studentId);
        if (studentOptional.isPresent()) {
            return ResponseEntity.ok(studentOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{studentId}")
    public ResponseEntity<?> update(@Valid @RequestBody Student student, BindingResult result,
                                    @PathVariable Long studentId){

        Optional<Student> studentOptional = studentService.update(studentId, student);
        if(result.hasFieldErrors()){
            return ResponseEntity.status(HttpStatus.CREATED).body(studentOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    /*public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        studentService.update(studentId, )
    } */

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo" + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}

