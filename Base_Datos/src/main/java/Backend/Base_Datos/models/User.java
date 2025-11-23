package Backend.Base_Datos.models;

import com.fasterxml.jackson.databind.DatabindException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.format.DecimalStyle;

@Schema(description = "Modelo de datos que representa a un usuario en el sistema. Se utiliza como contrato de la API.")
@Entity
@Table(name="usuario")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
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
    private String contrasena;

    @Column(nullable = false)
    @NotBlank(message = "El rol no puede ser vacio")
    @Schema(description = "Rol del usuario", example = "Cliente")
    private String rolUsuario;

    @Column
    @Schema( description = "Url que contiene avatar del usuario", example = "httsp://avatar.com")
    private String avatarUrl;

}
