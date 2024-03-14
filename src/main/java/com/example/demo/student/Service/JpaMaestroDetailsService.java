package com.example.demo.student.Service;

import com.example.demo.student.Entity.Maestro;
import com.example.demo.student.Repository.MaestroRepository;
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
public class JpaMaestroDetailsService implements UserDetailsService {

    @Autowired
    private MaestroRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<Maestro> maestroOptional = repository.findByUsername(username);

        if(maestroOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("username %s no existe en el sistema",
                    username));
        }

        Maestro maestro = maestroOptional.orElseThrow();

        List<GrantedAuthority> authorities = maestro.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(
                maestro.getUsername(),
                maestro.getPassword(),
                maestro.isEnable(),
                true,
                true,
                true,
                authorities);
    }
}
