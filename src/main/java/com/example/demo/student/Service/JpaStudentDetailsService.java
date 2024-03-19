package com.example.demo.student.Service;

import com.example.demo.student.Entity.Student;
import com.example.demo.student.Repository.studentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaStudentDetailsService implements UserDetailsService {

    @Autowired
    private studentRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> studentOptional = repository.findByUsername(username);

        if(studentOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("username %s no existe en el sistema",
                    username));
        }

        Student student = studentOptional.orElseThrow();

        List<GrantedAuthority> authorities = student.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(
                student.getUsername(),
                student.getPassword(),
                student.isEnable(),
                true,
                true,
                true,
                authorities);
    }
}
