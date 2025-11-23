package Backend.Base_Datos.controllers;

import Backend.Base_Datos.models.Product;
import Backend.Base_Datos.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;
import java.util.Optional;

@Tag(name = "Productos", description = "Endpoints para productos")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {this.productService = productService;}

    @Operation(summary = "Registra un nuevo producto en el sistema")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. Nombre duplicado/stock<0)")
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.saveOrUpdateProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "Recoge todos los productos del sistema")
    @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente")
    @ApiResponse(responseCode = "404", description = "No se encontraron productos")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Recoge un producto del sistema por su id")
    @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no existe")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);

        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Cuenta todos los productos del sistema")
    @ApiResponse(responseCode = "200", description = "Cantidad de productos contados exitosamente")
    @ApiResponse(responseCode = "404", description = "No fue posible contar los productos")
    @GetMapping("/count")
    public ResponseEntity<Long> countProducts() {
        long count = productService.countProducts();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Operation(summary = "Borra un producto por id")
    @ApiResponse(responseCode = "204", description = "Producto borrado exitosamente")
    @ApiResponse(responseCode = "404", description = "no se encontro el producto para borrar")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar (@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Disminuye el stock del producto")
    @ApiResponse(responseCode = "200", description = "El stock se modifico exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/decrease-stock/{id}")
    public ResponseEntity<Void> decreaseStock(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> payload) {
        Integer quantity = payload.get("quantity");
        if (quantity == null || quantity <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        productService.decreaseStock(id, quantity);
        return ResponseEntity.ok().build();
    }

}