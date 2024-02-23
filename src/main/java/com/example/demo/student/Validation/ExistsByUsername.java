package com.example.demo.student.Validation;

import jakarta.validation.Constraint;

@Constraint(validatedBy = ExistsByUsernameValidation.class)
public @interface ExistsByUsername {



}
