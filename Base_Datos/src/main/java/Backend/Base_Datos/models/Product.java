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
    @NotBlank(message = "Debe ingresar Precio")
    private String price;

    @Column(nullable = false)
    @NotNull(message = "El stock no puede ser vacio")
    private String stock;

    @Column
    private String imageUrl;

}
