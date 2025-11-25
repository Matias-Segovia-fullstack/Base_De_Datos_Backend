package Backend.Base_Datos.security;

import Backend.Base_Datos.security.filter.JwtAuthenticationFilter;
import Backend.Base_Datos.security.filter.JwtValidationFilter;
// CORRECCIÓN 1: Importar el filtro de Spring, NO el de Tomcat/Catalina
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
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
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authz) -> {
                    authz
                            // RUTAS PÚBLICAS
                            .requestMatchers(HttpMethod.POST, "/api/users", "/api/users/login").permitAll()
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                            // CORRECCIÓN 2: Permitir ver productos a TODOS (Público)
                            .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/{id}", "/api/products/count").permitAll()

                            // RUTAS PROTEGIDAS (Requieren Login/Rol)
                            .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMINISTRADOR")
                            .requestMatchers(HttpMethod.DELETE, "/api/products/{id}").hasRole("ADMINISTRADOR")
                            .requestMatchers(HttpMethod.PUT, "/api/products/decrease-stock/{id}").hasAnyRole("ADMINISTRADOR", "CLIENTE")

                            .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/count", "/api/users/{id}").hasRole("ADMINISTRADOR")
                            .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMINISTRADOR")

                            .requestMatchers("/api/carrito/**").hasAnyRole("ADMINISTRADOR", "CLIENTE")

                            .anyRequest().authenticated();
                })
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Permite React en puerto 3000
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        // CORRECCIÓN 3: Pasar la configuración al constructor
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource())
        );
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}