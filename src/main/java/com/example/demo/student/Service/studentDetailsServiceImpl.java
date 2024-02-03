package com.example.demo.student.Service;

import com.example.demo.student.Entity.Student;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Set;

public class studentDetailsServiceImpl implements UserDetailsService {
    StudentService studentService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        var student = getStudentById(username);

        if (student == null){
            throw new UsernameNotFoundException(username);
        }
        return User
                .withUsername(username)
                .password(student.getPassword())
                .roles(Arrays.toString(student.getRol().toArray(new String[0])))
                .build();
    }

    private static Student getStudentById(String studentName) {
        var password = "0705";
        Student per = new Student(1L,
                "per",
                "permail","0705" , LocalDate.of(
                1998, Month.APRIL, 22), Set.of("USER"));
        return per;
    }
}
