package Backend.Base_Datos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "DTO que representa un ítem dentro del carrito de compras, incluyendo detalles del producto (Contrato de la API).")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarritoDTO {

    @Schema(description = "Identificador único del ítem en el carrito.", example = "105")
    private Long idItemCarrito;

    @Schema(description = "Cantidad de unidades del producto solicitadas por el usuario.", example = "2")
    private Integer cantidad;

    @Schema(description = "Identificador del usuario propietario del carrito.", example = "2")
    private Long idUsuario;

    @Schema(description = "Identificador del producto.", example = "3")
    private Long idProducto;

    @Schema(description = "Nombre del producto.", example = "Controlador inalambrico xbox series X")
    private String name;

    @Schema(description = "Precio unitario del producto.", example = "59990")
    private Integer price;

    @Schema(description = "URL de la imagen del producto.", example = "cont_xbox_url")
    private String imageUrl;

    @Schema(description = "Stock actual del producto disponible en el inventario.", example = "18")
    private Integer stock;

    @Schema(description = "Subtotal del ítem (Cantidad * Precio Unitario).", example = "119980")
    private Long subtotal;
}