package Backend.Base_Datos.services;

import Backend.Base_Datos.models.Product;
import Backend.Base_Datos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveOrUpdateProduct (Product product){
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public long countProducts() {
        return productRepository.count();
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}