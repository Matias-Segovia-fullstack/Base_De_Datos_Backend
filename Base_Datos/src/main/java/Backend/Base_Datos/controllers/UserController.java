package Backend.Base_Datos.controllers;

import Backend.Base_Datos.models.User;
import Backend.Base_Datos.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Usuarios", description = "Endpoints para la gestión de usuarios y autenticación")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registra un nuevo usuario en el sistema")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. RUT/Correo duplicado)")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.saveOrUpdateUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Recoge todos los usuarios del sistema")
    @ApiResponse(responseCode = "200", description = "Usuarios encontrados exitosamente")
    @ApiResponse(responseCode = "404", description = "No se encontraron usuarios")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Recoge un usuario del sistema por su id")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no existe")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Operation(summary = "Cuenta todos los usuarios del sistema")
    @ApiResponse(responseCode = "200", description = "Cantidad de usuarios contados exitosamente")
    @ApiResponse(responseCode = "404", description = "No fue posible contar los usarios")
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        long count = userService.countUsers();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Operation(summary = "Borra un usuario por id")
    @ApiResponse(responseCode = "204", description = "Usuario borrado exitosamente")
    @ApiResponse(responseCode = "404", description = "no se encontro el usuario para borrar")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}