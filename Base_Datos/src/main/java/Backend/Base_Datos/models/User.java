package Backend.Base_Datos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name="usuario")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreUsuario;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Debe ingresar Rut")
    @Pattern(regexp = "^\\d{1,2}\\.?\\d{3}\\.?\\d{3}-[\\dkK]$", message = "El formato del RUT debe ser xx.xxx.xxx-x")
    private String rut;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Debe ingresar Correo")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|duocuc\\.cl)$", message = "El formato de correo no es válido o el dominio no está permitido (solo @gmail.com o @duocuc.cl)")
    private String correo;

    @Column(nullable = false)
    @NotBlank(message = "Debe ingresar una contraseña")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    @Column(nullable = false)
    @NotBlank(message = "El rol no puede ser vacio")
    private String rolUsuario;

    @Column
    private String avatarUrl;

}
