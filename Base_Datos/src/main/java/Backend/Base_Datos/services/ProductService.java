package Backend.Base_Datos.services;

import Backend.Base_Datos.models.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product saveOrUpdateProduct (Product product);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    void deleteProductById(Long id);
    long countProducts();
}