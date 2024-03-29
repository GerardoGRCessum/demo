package com.example.demo.student.Controller;

import com.example.demo.student.Entity.Grupo;
import com.example.demo.student.Entity.Maestro;
import com.example.demo.student.Entity.Student;
import com.example.demo.student.Service.MaestroService;
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

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping(path = "api/v1/teacher")
public class MaestroController {

    @Autowired
    private MaestroService maestroService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Maestro> getTeachers() {
        return maestroService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Maestro maestro,
                                    BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(maestroService.save(maestro));
    }

    @PostMapping(path = "/register", consumes = {"application/xml", "application/json",
            "application/x-www-form-url-urlencoded"})
    public ResponseEntity<?> register(@Valid @RequestBody Maestro maestro,
                                      BindingResult result) {
        maestro.setAdmin(false);

        return create(maestro, result);
    }

    @PreAuthorize("hasRoles('ADMIN')")
    @DeleteMapping(path = "/{teacherId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("teacherId") Long teacherId) {
        Optional<Maestro> maestroOptional = maestroService.delete(teacherId);
        if (maestroOptional.isPresent()) {
            return ResponseEntity.ok(maestroOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{teacherId}")
    public ResponseEntity<?> update(@Valid @RequestBody Maestro maestro, BindingResult  result,
                                    @PathVariable Long teacherId) {
        Optional<Maestro> maestroOptional = maestroService.update(teacherId, maestro);
        if (result.hasFieldErrors()){
            return ResponseEntity.status(HttpStatus.CREATED).body(maestroOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('TEACHER)")
    @PutMapping(path = "/studentgroup/{studentId}")
    public ResponseEntity<?> EstudianteAGrupo(@Valid @RequestBody Student student, BindingResult result,
                                                  @PathVariable Long studentId){
        Optional<Student> studentOptional = studentService.update(studentId, student);

        if (result.hasFieldErrors()){
            return ResponseEntity.status(HttpStatus.CREATED).body(studentOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo" + err.getField() + " "
            + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    //------------------------------------------------------------
}


