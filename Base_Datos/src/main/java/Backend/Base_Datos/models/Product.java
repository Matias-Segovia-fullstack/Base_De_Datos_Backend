package Backend.Base_Datos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Schema(description = "Modelo de datos que representa un producto del catálogo.")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="producto")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificar unico del producto", example = "56")
    private Long idProducto;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del producto (Debe ser unico)", example = "Controlador inalambrico xbox series X")
    private String nombreProducto;

    @Column(nullable = false)
    @NotBlank(message = "Debe ingresar Categoria")
    @Schema(description = "Categoria del producto", example = "Mousepad")
    private String categoria;

    @Column(nullable = false)
    @NotNull(message = "El precio es obligatoria")
    @Min(value = 1, message = "El precio debe ser al menos 1")
    @Schema(description = "Precio unitario del producto.", example = "59990")
    private Integer price;

    @Column(nullable = false)
    @NotNull(message = "El stock es obligatoria")
    @Min(value = 0, message = "El stock debe ser al menos 0")
    @Schema(description = "Stock actual del producto disponible en el inventario.", example = "18")
    private Integer stock;

    @Column
    @Schema( description = "Url que contiene la imagen del producto", example = "httsp://img_producto.com")
    private String imageUrl;

}
