package Backend.Base_Datos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="itemcarrito")
@Getter @Setter @ToString(exclude = {"user", "product"})
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemCarrito;

    @Column(nullable = false)
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_usuario", nullable = false)
    @NotNull(message = "El item debe estar asociado a un usuario")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_producto", nullable = false)
    @NotNull(message = "El item debe estar asociado a un producto")
    private Product product;

}
