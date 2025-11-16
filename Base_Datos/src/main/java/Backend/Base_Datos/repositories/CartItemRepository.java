package Backend.Base_Datos.repositories;

import Backend.Base_Datos.models.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<ItemCarrito, Long> {

    List<ItemCarrito> findByUserId(Long idUsuario);

    Optional<ItemCarrito> findByUserIdAndProductId(Long idUsuario, Long idProducto);
}