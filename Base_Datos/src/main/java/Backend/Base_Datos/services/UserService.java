package Backend.Base_Datos.services;

import Backend.Base_Datos.models.User;
import Backend.Base_Datos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByRut(String rut) {
        return userRepository.findByRut(rut);
    }

    public Optional<User> getUserByCorreo(String correo) {
        return userRepository.findByCorreo(correo);
    }

    public Optional<User> authenticateUser(String correo, String contrasena) {
        return userRepository.findByCorreoAndContrasena(correo, contrasena);
    }

    public long countUsers() {
        return userRepository.count();
    }
}