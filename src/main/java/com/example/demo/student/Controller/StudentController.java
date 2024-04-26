package com.example.demo.student.Controller;

import com.example.demo.student.Entity.Grupo;
import com.example.demo.student.Entity.Student;
import com.example.demo.student.Service.GrupoService;
import com.example.demo.student.Service.StudentService;
import com.example.demo.student.Repository.studentRepository;
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
@RequestMapping(path = "api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private studentRepository studentRepository;

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

    @GetMapping(path = "/activos")
    public List<Student> getActivosStudent(){
        return studentRepository.findAllByEnableTrue();
    }

    @GetMapping(path = "/listgrupos")
    public List<Grupo> getGrupos() {
        return grupoService.findAll();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Student student, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        if (validarEmail(student.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(studentService.save(student));
        }
        return ResponseEntity.badRequest().build();
    }

    //{"application/xml", "application/json", "application/x-www-form-urlencoded;charset=UTF-8"}
    @PostMapping(path = "/register", consumes = {"application/xml", "application/json;charset=UTF-8", "application/x-www-form-urlencoded;charset=UTF-8"})
    public ResponseEntity<?> register(@Valid @RequestBody Student student, BindingResult result) {
        return create(student, result);
    }


    // @PreAuthorize("hasRoles('ADMIN')")
    @PutMapping(path = "/{studentId}")
    public ResponseEntity<?> desactivarStudent(@PathVariable("studentId") Long studentId) {
        Optional<Student> studentOptional = studentService.desactivarStudent(studentId);
        if (studentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(studentOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


    //@PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{Id}/grupo/{grupoId}")//METODO PARA ASIGNAR ESTUDIANTE A GRUPO
    public ResponseEntity<Student> EstudianteAGrupo(@PathVariable("Id") Long Id, @PathVariable("grupoId") Long grupoId) {
        Optional<Student> studentOptional = studentService.asignarClase(Id, grupoId);
        if (studentOptional.isPresent() && studentOptional.get().isEnable()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(studentOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo" + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    private boolean validarEmail(String email) {
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regexPattern);
    }
}

