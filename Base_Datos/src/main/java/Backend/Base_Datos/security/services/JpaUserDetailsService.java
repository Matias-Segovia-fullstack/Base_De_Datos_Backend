package Backend.Base_Datos.security.services;

import Backend.Base_Datos.models.User;
import Backend.Base_Datos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // 1. Buscamos al usuario por correo (tu campo de login)
        User user = userRepository.findByCorreo(correo).orElseThrow(
                () -> new UsernameNotFoundException(String.format("No se encontró usuario con correo '%s'", correo))
        );

        // 2. Cargamos los roles/autoridades
        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        // 3. Retornamos un objeto de Spring Security UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getCorreo(),
                user.getContrasena(),
                user.getEnabled(),
                true,
                true,
                true,
                grantedAuthorities
        );
    }
}