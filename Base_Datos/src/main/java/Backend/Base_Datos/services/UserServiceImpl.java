package Backend.Base_Datos.services;

import Backend.Base_Datos.models.Role;
import Backend.Base_Datos.models.User;
import Backend.Base_Datos.repositories.RoleRepository;
import Backend.Base_Datos.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    // URL BASE: Definida con tu Cloud Name y carpeta (lvl_up/)
    private static final String CLOUDINARY_BASE_URL = "https://res.cloudinary.com/dch7p88hj/image/upload/lvl_up/";
    private static final String DEFAULT_EXTENSION = ".jpg";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User saveOrUpdateUser(User user) {

        String incomingUrl = user.getAvatarUrl();

        if (incomingUrl != null && !incomingUrl.isEmpty() && !incomingUrl.startsWith("http")) {

            String cleanedKey = incomingUrl
                    .replaceAll("\\.(jpg|jpeg|png|webp|gif)$", "")
                    .trim();

            // Construye la URL completa forzando la extensión .jpg
            String fullUrl = CLOUDINARY_BASE_URL + cleanedKey + DEFAULT_EXTENSION;
            user.setAvatarUrl(fullUrl);
        }
        List<Role> roles = new ArrayList<>();

        if (user.isAdmin()) {
            Role adminRole = roleRepository.findByName("ROLE_ADMINISTRADOR").orElseThrow(
                    () -> new RuntimeException("Error: El rol ROLE_ADMINISTRADOR no existe. Verifica LoadDatabase.")
            );
            roles.add(adminRole);
        } else {
            Role defaultRole = roleRepository.findByName("ROLE_CLIENTE").orElseThrow(
                    () -> new RuntimeException("Error: El rol ROLE_CLIENTE no existe. Verifica LoadDatabase.")
            );
            roles.add(defaultRole);
        }

        user.setRoles(roles);
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByRut(String rut) {
        return userRepository.findByRut(rut);
    }

    @Override
    public Optional<User> getUserByCorreo(String correo) {
        return userRepository.findByCorreo(correo);
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}