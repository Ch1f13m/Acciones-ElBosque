/**
 * Paquete de configuración en el cual esta todo lo que tenga que configurarse con parametros como seguridad y demas
 */
package co.edu.unbosque.accioneselbosqueapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración de seguridad para Spring Security.
 * Define reglas de acceso, login, logout y codificación de contraseñas.
 */
@Configuration
public class SecurityConfig {

    /**
     * Define la cadena de filtros de seguridad que manejará las reglas de acceso y autenticación.
     *
     * @param http el objeto HttpSecurity que permite personalizar el comportamiento de seguridad web
     * @return una instancia de SecurityFilterChain con las configuraciones establecidas
     * @throws Exception si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
           
            .authorizeHttpRequests(auth -> auth
               
                .requestMatchers("/login", "/registro", "/recuperar", "/nueva-password", "/css/**").permitAll()

                .anyRequest().authenticated()
            )

            .formLogin(form -> form

                .loginPage("/login")

                .defaultSuccessUrl("/panel", true)

                .failureUrl("/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout

                .logoutSuccessUrl("/login?logout=true")
                .permitAll() 
            );


        return http.build();
    }

    /**
     * Bean que define el codificador de contraseñas utilizando el algoritmo BCrypt.
     *
     * @return una instancia de PasswordEncoder usando BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
