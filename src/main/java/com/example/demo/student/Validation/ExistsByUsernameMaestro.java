package com.example.demo.student.Validation;

import jakarta.validation.Constraint;

@Constraint(validatedBy = ExistsByUsernameValidationMaestro.class)
public @interface ExistsByUsernameMaestro {
}
