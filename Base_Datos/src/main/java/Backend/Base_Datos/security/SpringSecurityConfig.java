package Backend.Base_Datos.security;

import Backend.Base_Datos.security.filter.JwtAuthenticationFilter;
import Backend.Base_Datos.security.filter.JwtValidationFilter;
import org.apache.catalina.filters.CorsFilter; // Importar el filtro de Tomcat (como en el ejemplo)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean; // Importar para registrar el filtro
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered; // Importar para dar prioridad al filtro
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
// Si necesitas usar @PreAuthorize en los métodos del controlador, descomentar la siguiente línea:
// @EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    // Esto nos permite devolver un script de codificador de contraseñas
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    // Esta componente es el que nos permitirá gestionar los accesos de cada uno de los enpoint que poseemos
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authz) -> {
                    // De esta forma dejamos publica la ruta de usuario pero todo lo demás necesita autentificación
                    authz
                            // RUTAS PÚBLICAS: Login, Registro y Swagger
                            .requestMatchers(HttpMethod.POST, "/api/users", "/api/users/login").permitAll()
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                            // REGLAS DE AUTORIZACIÓN (Utilizando los roles 'ADMINISTRADOR' y 'CLIENTE')
                            .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/{id}", "/api/products/count").hasAnyRole("ADMINISTRADOR", "CLIENTE")
                            .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMINISTRADOR")
                            .requestMatchers(HttpMethod.DELETE, "/api/products/{id}").hasRole("ADMINISTRADOR")
                            .requestMatchers(HttpMethod.PUT, "/api/products/decrease-stock/{id}").hasAnyRole("ADMINISTRADOR", "CLIENTE")

                            .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/count", "/api/users/{id}").hasRole("ADMINISTRADOR")
                            .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMINISTRADOR")

                            .requestMatchers("/api/carrito/**").hasAnyRole("ADMINISTRADOR", "CLIENTE")

                            // Cualquier otra petición debe estar autenticada
                            .anyRequest().authenticated();
                })
                // Agregamos los filtros JWT
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config ->
                        config.disable() // Desactivar CSRF para API REST
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Y la sesion de generación la dejamos en STATELESS dado que esa autentificación se realizara mediante
                // un token JWT, no queda autentificado en una session HTTP si no mediante un token
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

    // Configuración de CORS (Usado por Spring Security internamente)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite cualquier origen (debe ser delimitado en producción)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Bean adicional para CORS (COPIADO DEL PROFESOR para dar el mismo estilo)
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter()
        );
        // Le da la máxima prioridad al filtro de CORS
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}