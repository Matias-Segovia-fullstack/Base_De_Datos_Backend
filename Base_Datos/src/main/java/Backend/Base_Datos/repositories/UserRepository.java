package Backend.Base_Datos.repositories;

import Backend.Base_Datos.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByRut(String rut);

  Optional<User> findByCorreo(String correo);

  Optional<User> findByContrasena(String contrasena);

  Optional<User> findByCorreoAndContrasena(String correo, String contrasena);
}
