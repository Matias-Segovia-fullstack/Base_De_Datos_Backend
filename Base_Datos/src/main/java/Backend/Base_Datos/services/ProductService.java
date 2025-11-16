package Backend.Base_Datos.services;

import Backend.Base_Datos.models.Product;
import Backend.Base_Datos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {this.productRepository = productRepository; }

    public Product saveOrUpdateProduct (Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public long countProducts() {
        return productRepository.count();
    }
}