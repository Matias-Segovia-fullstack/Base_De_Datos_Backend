package Backend.Base_Datos.services;

import Backend.Base_Datos.dtos.ItemCarritoDTO;
import Backend.Base_Datos.models.ItemCarrito;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    ItemCarritoDTO addItemToCart(Long userId, Long productId, int cantidad);
    List<ItemCarritoDTO> getCartDetailsByUserId(Long userId);
    void removeItemFromCart(Long itemCarritoId);
    void clearCart(Long userId);
}