package com.example.demo.student.Validation;

import com.example.demo.student.Service.MaestroService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistsByUsernameValidationMaestro implements ConstraintValidator<ExistsByUsernameMaestro, String>{

    @Autowired
    private MaestroService maestroService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext){
<<<<<<< HEAD
        if(maestroService== null){
            return true;
        }
        return !maestroService.existsByUsername(username);
    }

=======
        if(maestroService == null){
            return true;
        }
        return  !maestroService.existsByUsername(username);
    }
>>>>>>> 0299fdc7685649360533a763ea752989fc202d2e
}