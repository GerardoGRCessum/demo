package com.example.demo.student.Controller;

import com.example.demo.student.Entity.Materia;
import com.example.demo.student.Service.MateriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping(path = "api/v1/materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping
    public List<Materia> getMaterias(){
        return materiaService.findAll();
    }

    @PostMapping(path = "/crearmateria")
    public ResponseEntity<?> create(@Valid @RequestBody Materia materia, BindingResult result){
        if (result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(materiaService.save(materia));
    }

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " "
            + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
