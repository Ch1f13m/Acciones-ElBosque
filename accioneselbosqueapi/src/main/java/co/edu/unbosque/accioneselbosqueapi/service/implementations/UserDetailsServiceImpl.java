/**
 * Paquete que contiene implementaciones concretas de servicios del proyecto
 * Acciones El Bosque, incluyendo lógica relacionada con seguridad, autenticación
 * y lógica de negocio.
 */
package co.edu.unbosque.accioneselbosqueapi.service.implementations;

import co.edu.unbosque.accioneselbosqueapi.model.entity.User;
import co.edu.unbosque.accioneselbosqueapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementación personalizada de {@link UserDetailsService} para integrar
 * el sistema de autenticación de Spring Security con los datos de usuarios
 * almacenados en la base de datos.
 * 
 * Esta clase permite cargar los detalles del usuario (credenciales y roles)
 * durante el proceso de autenticación.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Carga los detalles de un usuario dado su nombre de usuario.
     * Este método es utilizado automáticamente por Spring Security durante la autenticación.
     *
     * @param username el nombre de usuario ingresado por el usuario.
     * @return un objeto {@link UserDetails} que contiene la información del usuario.
     * @throws UsernameNotFoundException si no se encuentra un usuario con ese nombre.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Retorna el usuario con su username, password y rol como autoridad
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}

