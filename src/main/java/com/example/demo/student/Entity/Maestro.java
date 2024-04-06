package com.example.demo.student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@EnableAutoConfiguration
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "roles")
public class Maestro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @Column(name = "username")
    @NotBlank
    @Size(min = 4, max = 60)
    private String username;

    @Column(name = "email")
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //no mostrar dato en json get
    @Column(name = "password")
    private String password;

    @JsonIgnoreProperties(value = {"students", "maestros", "personas", "teacher", "grupos", "roles", "handler", "hibernateLazyInitializer"})
    //TODO: probar tabla conjunta
   @OneToMany(mappedBy = "teacher")
    private Set<Grupo> grupos;


    @JsonIgnoreProperties(value = {"personas", "students", "handler", "hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
            name = "teachers_roles",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"teacher_id", "role_id"})}
    )
    @ToString.Exclude
    private List<Role> roles;



    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "enable")
    private boolean enable;

    @PrePersist
    public void prePersist(){
        enable = true;
    }

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

}
