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

@Entity
@EnableAutoConfiguration
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "invoices")
public class Maestro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonIgnoreProperties({"students","maestros","personas","teacher","grupos", "roles","handler", "hibernateLazyInitializer"})
    //TODO: probar tabla conjunta
   @OneToMany(mappedBy = "teacher")
    private List<Grupo> grupos;


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
    @Column(name = "enable")
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
        return enable == maestro.enable && admin == maestro.admin && Objects.equals(id, maestro.id) && Objects.equals(username, maestro.username) && Objects.equals(email, maestro.email) && Objects.equals(password, maestro.password) && Objects.equals(grupos, maestro.grupos) && Objects.equals(roles, maestro.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, grupos, roles, enable, admin);
    }
}
