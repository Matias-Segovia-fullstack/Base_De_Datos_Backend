package Backend.Base_Datos.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ItemCarritoDTO {

    private Long idItemCarrito;
    private Integer cantidad;
    private Long idUsuario;

    private Long idProducto;
    private String name;
    private Integer price;
    private String imageUrl;
    private Integer stock;

    private Long subtotal;
}