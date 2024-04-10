package com.example.demo.student.Controller;

import com.example.demo.student.Entity.Grupo;
import com.example.demo.student.Entity.Student;
import com.example.demo.student.Service.GrupoService;
import com.example.demo.student.Service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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

    @Autowired
    private GrupoService grupoService;
    /*@Autowired
    AuthenticationManager authenticationManager;
    ------SOLO ACTIVAR PARA PRUEBAS DE AUTO LOGIN DESPUES DE REGISTER------
    /*public void authWithAuthManager(HttpServletRequest request, String username, String password){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }*/

    @GetMapping
    public List<Student> getStudents() {
        return studentService.findAll();
    }

    @GetMapping(path = "/listgrupos")
    public List<Grupo> getGrupos(){
        return grupoService.findAll();
    }

    @PreAuthorize("hasRole('TEACHER')")
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
    @PostMapping(path = "/register", consumes =  {"application/xml", "application/json;charset=UTF-8", "application/x-www-form-urlencoded;charset=UTF-8"} )
    public ResponseEntity<?> register(@Valid @RequestBody Student student, BindingResult result){
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


    //@PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{Id}/grupo/{grupoId}")
    public ResponseEntity<Student> EstudianteAGrupo(@PathVariable("Id") Long Id, @PathVariable("grupoId") Long grupoId){
        Optional<Student> studentOptional = studentService.asignarClase(Id, grupoId);
        if (studentOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(studentOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo" + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}

