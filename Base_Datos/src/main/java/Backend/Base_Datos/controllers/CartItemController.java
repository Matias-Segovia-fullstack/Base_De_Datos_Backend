package Backend.Base_Datos.controllers;

import Backend.Base_Datos.dtos.ItemCarritoDTO;
import Backend.Base_Datos.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/add")
    public ResponseEntity<ItemCarritoDTO> addItem(
            @RequestBody Map<String, Object> payload) {

        Long userId = Long.valueOf(payload.get("userId").toString());
        Long productId = Long.valueOf(payload.get("productId").toString());
        int cantidad = (int) payload.getOrDefault("cantidad", 1);

        ItemCarritoDTO item = cartItemService.addItemToCart(userId, productId, cantidad);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ItemCarritoDTO>> getCart(@PathVariable Long userId) {
        List<ItemCarritoDTO> cartDetails = cartItemService.getCartDetailsByUserId(userId);
        return new ResponseEntity<>(cartDetails, HttpStatus.OK);
    }

    @DeleteMapping("/item/{itemCarritoId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemCarritoId) {
        cartItemService.removeItemFromCart(itemCarritoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartItemService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}