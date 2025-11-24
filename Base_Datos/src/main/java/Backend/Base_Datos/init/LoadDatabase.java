package Backend.Base_Datos.init;

import Backend.Base_Datos.models.Product;
import Backend.Base_Datos.models.User;
import Backend.Base_Datos.models.Role; // Importar el nuevo modelo Role
import Backend.Base_Datos.repositories.ProductRepository;
import Backend.Base_Datos.repositories.UserRepository;
import Backend.Base_Datos.repositories.RoleRepository; // Importar el nuevo RoleRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar PasswordEncoder

import java.util.List;

@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // NUEVO: Inyectar PasswordEncoder


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // 1. CARGAR ROLES PRIMERO
        if (roleRepository.count() == 0) {
            loadRoles();
        } else {
            System.out.println(">>> Datos de roles ya existentes. Saltando inicialización (SEED).");
        }

        // 2. CARGAR USUARIOS
        if (userRepository.count() == 0) {
            loadUsers();
        } else {
            System.out.println(">>> Datos de usuario ya existentes. Saltando inicialización (SEED).");
        }

        // 3. CARGAR PRODUCTOS
        if (productRepository.count() == 0) {
            loadProducts();
        } else {
            System.out.println(">>> Datos de productos ya existentes. Saltando inicialización (SEED).");
        }
    }

    private void loadRoles() {
        System.out.println(">>> Inicializando roles: Administrador y Cliente.");
        // Los nombres de rol DEBEN tener el prefijo ROLE_
        roleRepository.save(new Role("ROLE_ADMINISTRADOR"));
        roleRepository.save(new Role("ROLE_CLIENTE"));
    }

    private void loadUsers() {
        System.out.println(">>> Inicializando usuarios: Admin y Cliente.");

        // Obtenemos los roles para asignarlos
        Role adminRole = roleRepository.findByName("ROLE_ADMINISTRADOR").get();
        Role clientRole = roleRepository.findByName("ROLE_CLIENTE").get();

        User adminUser = new User();
        adminUser.setNombreUsuario("Admin Duoc");
        adminUser.setRut("12.345.678-9");
        adminUser.setCorreo("admin@duocuc.cl");
        // CRÍTICO: Cifrar la contraseña
        adminUser.setContrasena(passwordEncoder.encode("admin123"));
        adminUser.setRoles(List.of(adminRole));
        adminUser.setAvatarUrl("urlAdmin");
        userRepository.save(adminUser);

        User clientUser = new User();
        clientUser.setNombreUsuario("Cliente Gmail");
        clientUser.setRut("98.765.432-1");
        clientUser.setCorreo("cliente@gmail.com");
        // CRÍTICO: Cifrar la contraseña
        clientUser.setContrasena(passwordEncoder.encode("cliente123"));
        clientUser.setRoles(List.of(clientRole)); // Asignar rol de cliente
        clientUser.setAvatarUrl("urlCliente");
        userRepository.save(clientUser);
    }

    private void loadProducts() {
        // Tu lógica original de carga de productos (sin cambios)
        List<Product> products = List.of(
                new Product(null, "Catan", "Juegos de mesa", 29990, 10, "catan_url"),
                new Product(null, "Carcassonne", "Juegos de mesa", 24990, 5, "carcassonne_url"),
                new Product(null, "Controlador inalambrico xbox series X", "Accesorios", 59990, 20, "cont_xbox_url"),
                new Product(null, "Auriculares Gamer hyperX Cloud II ", "Accesorios", 79990, 20, "auri_gam_url"),
                new Product(null, "Playstation 5", "Consolas", 549990, 20, "play5_url"),
                new Product(null, "PC gamer ASUS ROG STRIX", "Computadoras gamers", 1299990, 5, "pc_gamer_url"),
                new Product(null, "Silla gamer Secretlab titan", "Sillas gamer", 349990, 18, "silla_gamer_st_url"),
                new Product(null, "Mousepad Razer goliathus extended chroma", "Mousepads", 29990, 30, "mousepad_razer_url"),
                new Product(null, "Polera gamer personalizada 'level-up'croma", "Poleras y polerones personalizados", 14990, 18, "polera_per_url")
        );
        productRepository.saveAll(products);
    }
}