package com.example.demo.student.Repository;

import com.example.demo.student.Entity.Maestro;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaestroRepository extends JpaRepository<Maestro, Long> {
    boolean existsByusername(String username);

    Optional<Maestro> findByEmail(String email);

    Optional<Maestro> findByUsername(String username);

    List<Maestro> findAllByEnableTrue();
}
