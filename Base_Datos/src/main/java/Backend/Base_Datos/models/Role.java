package Backend.Base_Datos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Los roles deben tener el prefijo 'ROLE_' (ej. ROLE_ADMIN, ROLE_CLIENTE)
    @Column(name = "name", unique=true, nullable = false)
    private String name;

    @JsonIgnoreProperties({"roles", "hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    public Role() {
        this.users = new ArrayList<>();
    }

    public Role(String name) {
        this();
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}