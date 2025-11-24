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

        List<Role> roles = new ArrayList<>();

        Role defaultRole = roleRepository.findByName("ROLE_CLIENTE").orElseThrow(
                () -> new RuntimeException("Error: El rol ROLE_CLIENTE no existe. Verifica LoadDatabase.")
        );
        roles.add(defaultRole);

        if (user.isAdmin()) {
            Role adminRole = roleRepository.findByName("ROLE_ADMINISTRADOR").orElseThrow(
                    () -> new RuntimeException("Error: El rol ROLE_ADMINISTRADOR no existe. Verifica LoadDatabase.")
            );
            roles.add(adminRole);
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