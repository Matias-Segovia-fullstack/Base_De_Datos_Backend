package Backend.Base_Datos.services;

import Backend.Base_Datos.models.Product;
import Backend.Base_Datos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    // URL BASE: Definida con tu Cloud Name y carpeta (lvl_up/)
    private static final String CLOUDINARY_BASE_URL = "https://res.cloudinary.com/dch7p88hj/image/upload/lvl_up/";

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        String incomingUrl = product.getImageUrl();

        if (incomingUrl != null && !incomingUrl.isEmpty() && !incomingUrl.startsWith("http")) {

            String cleanedKey = incomingUrl
                    .replaceAll("\\.(jpg|jpeg|png|webp)$", "")
                    .trim();

            String fullUrl = CLOUDINARY_BASE_URL + cleanedKey + ".jpg";
            product.setImageUrl(fullUrl);
        }

        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional
    public void decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

        int currentStock = product.getStock();

        if (currentStock < quantity) {

            throw new RuntimeException("Stock insuficiente. Solo quedan " + currentStock + " unidades.");
        }


    }

}