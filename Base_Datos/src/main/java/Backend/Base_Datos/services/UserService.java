package Backend.Base_Datos.services;

import Backend.Base_Datos.models.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveOrUpdateUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void deleteUserById(Long id);
    Optional<User> getUserByRut(String rut);
    Optional<User> getUserByCorreo(String correo);
    Optional<User> authenticateUser(String correo, String contrasena);
    long countUsers();
}