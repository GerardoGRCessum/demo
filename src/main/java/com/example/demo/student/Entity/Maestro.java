package com.example.demo.student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;

@Entity
@EnableAutoConfiguration
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Maestro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    @Size(min = 4, max = 60)
    private String username;

    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //no mostrar dato en json get
    private String password;

    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    //TODO: probar tabla conjunta
    @ManyToMany
    @JoinTable(
            name = "clases",
            joinColumns = @JoinColumn(name = "fk_maestro_id"),
            inverseJoinColumns = @JoinColumn(name = "clase_id")
    )
    private List<Materia> materias;


    @JsonIgnoreProperties({"personas", "students", "handler", "hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
            name = "teachers_roles",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"teacher_id", "role_id"})}
    )
    private List<Role> roles;



    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean enable;

    @PrePersist
    public void prePersist(){
        enable = true;
    }

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;


}
