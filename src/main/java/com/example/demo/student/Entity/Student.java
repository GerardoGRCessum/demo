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
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    @Size(min = 4, max = 45)
    private String username;
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // no mostrar dato en json GET
    private String password;


    @JsonIgnoreProperties({"personas", "students", "handler", "hibernateLazyInitializer"})
    /*@ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames =
                    {"user_id", "role_id"})}
    )*/
    //private List<Role> roles;
    @ManyToOne(

            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_roles")
    private Role rol;




    @JsonIgnoreProperties({"maestros", "personas", "students", "handler","roles", "hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
            name = "user_clase",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "clase_id")
    )
    private List<Grupo> grupos;

    /*public Student() {
        roles = new ArrayList<>();
    }*/


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean enable;

    @PrePersist
    public void prePersist() {
        enable = true;
    }

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {return enable;}

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return enable == student.enable && admin == student.admin && Objects.equals(id, student.id) && Objects.equals(username, student.username) && Objects.equals(email, student.email) && Objects.equals(password, student.password) && Objects.equals(rol, student.rol) && Objects.equals(grupos, student.grupos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, rol, grupos, enable, admin);
    }
}


