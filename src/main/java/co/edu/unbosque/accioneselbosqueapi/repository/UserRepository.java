/**
 * Paquete que contiene las interfaces de acceso a datos (repositorios) 
 * del proyecto Acciones El Bosque.
 * 
 * Este repositorio define métodos personalizados para consultar usuarios
 * en la base de datos utilizando Spring Data JPA.
 */
package co.edu.unbosque.accioneselbosqueapi.repository;

import co.edu.unbosque.accioneselbosqueapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interfaz de repositorio para la entidad {@link User}.
 * 
 * Esta interfaz proporciona métodos para acceder a los datos de usuarios 
 * almacenados en la base de datos, utilizando Spring Data JPA.
 * Hereda de {@link JpaRepository}, lo que le permite usar operaciones CRUD estándar.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario a buscar.
     * @return un {@link Optional} con el usuario si fue encontrado, o vacío si no.
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email el correo electrónico a buscar.
     * @return un {@link Optional} con el usuario si fue encontrado, o vacío si no.
     */
    Optional<User> findByEmail(String email);

    /**
     * Busca un usuario por su token de recuperación de contraseña.
     *
     * @param token el token asociado a la recuperación de contraseña.
     * @return un {@link Optional} con el usuario si fue encontrado, o vacío si no.
     */
    Optional<User> findByResetToken(String token);
}
