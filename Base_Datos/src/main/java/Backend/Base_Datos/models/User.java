package Backend.Base_Datos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Modelo de datos que representa a un usuario en el sistema. Se utiliza como contrato de la API.")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "roles"})
@Entity
@Table(name="usuario")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificar unico del usuario", example = "15")
    private Long idUsuario;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del usuario", example = "Cliente Gmail")
    private String nombreUsuario;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Debe ingresar Rut")
    @Pattern(regexp = "^\\d{1,2}\\.?\\d{3}\\.?\\d{3}-[\\dkK]$", message = "El formato del RUT debe ser xx.xxx.xxx-x")
    @Schema(description = "Rut del usuario (Debe ser unico)", example = "12.345.678-9")
    private String rut;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Debe ingresar Correo")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|duocuc\\.cl)$", message = "El formato de correo no es válido o el dominio no está permitido (solo @gmail.com o @duocuc.cl)")
    @Schema(description = "Correo del usuaerio (Debe ser unico)", example = "Cliente@gmail.com")
    private String correo;

    @Column(nullable = false)
    @NotBlank(message = "Debe ingresar una contraseña")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Schema(description = "Contraseña del usuario", example = "cliente123")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasena;

    @Column
    @Schema( description = "Url que contiene avatar del usuario", example = "httsp://avatar.com")
    private String avatarUrl;



    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    private Boolean enabled = true;

    @JsonIgnoreProperties({"users", "hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})}
    )
    private List<Role> roles;

    public User() {
        this.roles = new ArrayList<>();
    }

}
