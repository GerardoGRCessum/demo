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
    //TODO: añadir relacion para conectar maestro a materias y grupos
    private String clave_materia;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Maestro maestro = (Maestro) o;

        if (id != maestro.id) return false;
        if (!username.equals(maestro.username)) return false;
        if (!email.equals(maestro.email)) return false;
        return clave_materia.equals(maestro.clave_materia);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + clave_materia.hashCode();
        return result;
    }
}
