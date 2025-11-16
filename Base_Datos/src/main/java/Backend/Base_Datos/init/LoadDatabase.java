package Backend.Base_Datos.init;

import Backend.Base_Datos.models.Product;
import Backend.Base_Datos.models.User;
import Backend.Base_Datos.repositories.ProductRepository;
import Backend.Base_Datos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {

            loadUsers();

        } else {
            System.out.println(">>> Datos de usuario ya existentes. Saltando inicialización (SEED).");
        }

        if (productRepository.count() == 0) {

            loadProducts();

        } else {
            System.out.println(">>> Datos de productos ya existentes. Saltando inicialización (SEED).");
        }
    }

    private void loadUsers() {
        User adminUser = new User();
        adminUser.setNombreUsuario("Admin Duoc");
        adminUser.setRut("12.345.678-9");
        adminUser.setCorreo("admin@duocuc.cl");
        adminUser.setContrasena("admin123");
        adminUser.setRolUsuario("Administrador");
        adminUser.setAvatarUrl("urlAdmin");
        userRepository.save(adminUser);

        User clientUser = new User();
        clientUser.setNombreUsuario("Cliente Gmail");
        clientUser.setRut("98.765.432-1");
        clientUser.setCorreo("cliente@gmail.com");
        clientUser.setContrasena("cliente123");
        clientUser.setRolUsuario("Cliente");
        clientUser.setAvatarUrl("urlCliente");
        userRepository.save(clientUser);
    }

    private void loadProducts() {

        List<Product> products = List.of(
                new Product(null, "Catan", "Juegos de mesa", "29.990", "10", "catan_url"),
                new Product(null, "Carcassonne", "Juegos de mesa", "24.990", "5", "carcassonne_url"),
                new Product(null, "Controlador inalambrico xbox series X", "Accesorios", "59.990", "20", "cont_xbox_url"),
                new Product(null, "Auriculares Gamer hyperX Cloud II ", "Accesorios", "79.990", "20", "auri_gam_url"),
                new Product(null, "Playstation 5", "Consolas", "549.990", "8", "play5_url"),
                new Product(null, "PC gamer ASUS ROG STRIX", "Computadoras gamers", "1.299.990", "5", "pc_gamer_url"),
                new Product(null, "Silla gamer Secretlab titan", "Sillas gamer", "349.990", "18", "silla_gamer_st_url"),
                new Product(null, "Mousepad Razer goliathus extended chroma", "Mousepads", "29.990", "30", "mousepad_razer_url"),
                new Product(null, "Polera gamer personalizada 'level-up'croma", "Poleras y polerones personalizados", "14.990", "18", "polera_per_url")
        );
        productRepository.saveAll(products);
    }
}