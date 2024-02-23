package com.example.demo.student.Validation;

import com.example.demo.student.Service.StudentService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    @Autowired
    private StudentService studentService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext){
        if (studentService == null){
            return true;
        }
        return !studentService.existsByUsername(username);
    }

}
