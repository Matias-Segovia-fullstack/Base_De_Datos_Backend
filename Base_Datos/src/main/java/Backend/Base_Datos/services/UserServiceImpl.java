package Backend.Base_Datos.services;

import Backend.Base_Datos.models.User;
import Backend.Base_Datos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveOrUpdateUser(User user) {
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
    public Optional<User> authenticateUser(String correo, String contrasena) {
        return userRepository.findByCorreoAndContrasena(correo, contrasena);
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