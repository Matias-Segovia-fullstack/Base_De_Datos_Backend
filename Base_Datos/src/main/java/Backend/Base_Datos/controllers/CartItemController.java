package Backend.Base_Datos.controllers;

import Backend.Base_Datos.dtos.ItemCarritoDTO;
import Backend.Base_Datos.services.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Carrito", description = "Endpoints para la gestión de items del carrito")
@RestController
@RequestMapping("/api/carrito")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @Operation(summary = "Agrega un nuevo producto al carrito")
    @ApiResponse(responseCode = "201", description = "Producto agregado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @PostMapping("/add")
    public ResponseEntity<ItemCarritoDTO> addItem(
            @RequestBody Map<String, Object> payload) {

        Long userId = Long.valueOf(payload.get("userId").toString());
        Long productId = Long.valueOf(payload.get("productId").toString());
        int cantidad = (int) payload.getOrDefault("cantidad", 1);

        ItemCarritoDTO item = cartItemService.addItemToCart(userId, productId, cantidad);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @Operation(summary = "Recoge el carrito de un usuario por su id")
    @ApiResponse(responseCode = "200", description = "Carrito de usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Carrito de usuario no encontrado")
    @GetMapping("/{userId}")
    public ResponseEntity<List<ItemCarritoDTO>> getCart(@PathVariable Long userId) {
        List<ItemCarritoDTO> cartDetails = cartItemService.getCartDetailsByUserId(userId);
        return new ResponseEntity<>(cartDetails, HttpStatus.OK);
    }

    @Operation(summary = "Borra un producto del carrito por id")
    @ApiResponse(responseCode = "204", description = "Producto borrado exitosamente")
    @ApiResponse(responseCode = "404", description = "no se encontro el producto para borrar")
    @DeleteMapping("/item/{itemCarritoId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemCarritoId) {
        cartItemService.removeItemFromCart(itemCarritoId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Borra un carrito de usuario")
    @ApiResponse(responseCode = "204", description = "Carrito borrado exitosamente")
    @ApiResponse(responseCode = "404", description = "No se pudo borrar carrito")
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartItemService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}