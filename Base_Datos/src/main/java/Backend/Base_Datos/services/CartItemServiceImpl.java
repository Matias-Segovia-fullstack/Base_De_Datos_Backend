package Backend.Base_Datos.services;

import Backend.Base_Datos.dtos.ItemCarritoDTO;
import Backend.Base_Datos.models.ItemCarrito;
import Backend.Base_Datos.models.Product;
import Backend.Base_Datos.models.User;
import Backend.Base_Datos.repositories.CartItemRepository;
import Backend.Base_Datos.repositories.ProductRepository;
import Backend.Base_Datos.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class    CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository,
                                  ProductRepository productRepository,
                                  UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ItemCarritoDTO addItemToCart(Long userId, Long productId, int cantidad) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

        int currentStock = product.getStock();
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a agregar debe ser positiva.");
        }

        Optional<ItemCarrito> existingItem = cartItemRepository
                .findByUser_IdUsuarioAndProduct_IdProducto(userId, productId);

        ItemCarrito item;
        int newTotalQuantity;

        if (existingItem.isPresent()) {
            item = existingItem.get();
            newTotalQuantity = item.getCantidad() + cantidad;
        } else {
            item = new ItemCarrito();
            item.setUser(user);
            item.setProduct(product);
            item.setCantidad(0);
            newTotalQuantity = cantidad;
        }

        if (newTotalQuantity > currentStock) {
            throw new RuntimeException("Stock insuficiente. Solo quedan " + currentStock + " unidades.");
        }

        item.setCantidad(newTotalQuantity);
        ItemCarrito savedItem = cartItemRepository.save(item);

        return mapToDTO(savedItem);
    }

    @Override
    public List<ItemCarritoDTO> getCartDetailsByUserId(Long userId) {
        List<ItemCarrito> items = cartItemRepository.findByUser_IdUsuario(userId);

        return items.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeItemFromCart(Long itemCarritoId) {
        cartItemRepository.deleteById(itemCarritoId);
    }

    @Override
    public void clearCart(Long userId) {
        List<ItemCarrito> itemsToDelete = cartItemRepository.findByUser_IdUsuario(userId);
        cartItemRepository.deleteAll(itemsToDelete);
    }

    private ItemCarritoDTO mapToDTO(ItemCarrito item) {
        ItemCarritoDTO dto = new ItemCarritoDTO();
        Product product = item.getProduct();

        dto.setIdItemCarrito(item.getIdItemCarrito());
        dto.setCantidad(item.getCantidad());
        dto.setIdUsuario(item.getUser().getIdUsuario());

        dto.setIdProducto(product.getIdProducto());
        dto.setName(product.getNombreProducto());
        dto.setImageUrl(product.getImageUrl());


        dto.setStock(product.getStock());

        Integer precioUnitario = product.getPrice();

        Long subtotal = (long) precioUnitario * item.getCantidad();

        dto.setPrice(precioUnitario);

        dto.setSubtotal(subtotal);

        return dto;
    }
}