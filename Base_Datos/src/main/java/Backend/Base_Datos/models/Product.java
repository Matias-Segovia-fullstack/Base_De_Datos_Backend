package Backend.Base_Datos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name="producto")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreProducto;

    @Column(nullable = false)
    @NotBlank(message = "Debe ingresar Categoria")
    private String categoria;

    @Column(nullable = false)
    @NotNull(message = "El precio es obligatoria")
    @Min(value = 1, message = "El precio debe ser al menos 1")
    private Integer price;

    @Column(nullable = false)
    @NotNull(message = "El stock es obligatoria")
    @Min(value = 0, message = "El stock debe ser al menos 0")
    private Integer stock;

    @Column
    private String imageUrl;

}
