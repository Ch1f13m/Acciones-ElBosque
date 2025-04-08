/**
 * En este paquete se lleva acabo todas las entidades que sean objetos para el funcionamiento del programa
 */
package co.edu.unbosque.accioneselbosqueapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa a un usuario dentro del sistema.
 * Mapeada a la tabla "users" en la base de datos.
 * Incluye campos personales, de autenticación y para recuperación de contraseña.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Token temporal utilizado para la recuperación de contraseña.
     */
    @Column(name = "reset_token")
    private String resetToken;

    /**
     * Identificador único del usuario (clave primaria, autoincremental).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único para autenticación.
     */
    @Column(unique = true)
    private String username;

    /**
     * Contraseña encriptada del usuario.
     */
    private String password;

    /**
     * Rol del usuario dentro del sistema (ej. INVESTOR, ADMIN, BROKER).
     */
    private String role;

    /**
     * Nombre real del usuario.
     */
    private String nombre;

    /**
     * Apellido del usuario.
     */
    private String apellido;

    /**
     * Número de documento de identificación.
     */
    private String dni;

    /**
     * Correo electrónico del usuario (también se usa para recuperación de contraseña).
     */
    private String email;

    /**
     * Número de celular del usuario.
     */
    private String celular;
}
