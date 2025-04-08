/**
 * Paquete en el cual esta el controlador y permite la comicacion con el modelo, la vista, sus configuraciones y servicios.
 */
package co.edu.unbosque.accioneselbosqueapi.controller;

import org.springframework.http.ResponseEntity;
import co.edu.unbosque.accioneselbosqueapi.model.entity.User;
import co.edu.unbosque.accioneselbosqueapi.repository.UserRepository;
import co.edu.unbosque.accioneselbosqueapi.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Controlador encargado de manejar la autenticación de usuarios:
 * registro, login, recuperación y cambio de contraseña.
 */
@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * Constructor con inyección de dependencias.
     */
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /**
     * Muestra el formulario de registro.
     */
    @GetMapping("/registro")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "registro";
    }

    /**
     * Procesa el formulario de registro de un nuevo usuario.
     */
    @PostMapping("/registro")
    public String processRegister(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

    /**
     * Muestra el formulario de inicio de sesión.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Muestra el panel principal del usuario autenticado.
     */
    @GetMapping("/panel")
    public String showUserPanel() {
        return "panel";
    }

    /**
     * Muestra el formulario para solicitar la recuperación de contraseña.
     */
    @GetMapping("/recuperar")
    public String mostrarFormularioRecuperar() {
        return "recuperar";
    }

    /**
     * Procesa la solicitud de recuperación de contraseña.
     * Busca el correo en la base de datos, genera un token de recuperación y envía un enlace por email.
     */
    @PostMapping("/recuperar")
    public String procesarRecuperar(@RequestParam String email, Model model) {
        System.out.println(">> Recuperando contraseña para: " + email);

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user); // Guarda el token en la base de datos

            String link = "http://localhost:8080/nueva-password?token=" + token;
            String asunto = "Recuperación de contraseña - Acciones El Bosque";
            String cuerpo = "Hola " + user.getNombre() + ",\n\n"
                    + "Recibimos una solicitud para restablecer tu contraseña.\n"
                    + "Haz clic en el siguiente enlace para continuar:\n"
                    + link + "\n\n"
                    + "Si no solicitaste esto, puedes ignorar este mensaje.";

            try {
                emailService.enviarCorreo(email, asunto, cuerpo);
                System.out.println(">> Correo enviado a: " + email);
                model.addAttribute("mensaje", "Se ha enviado un enlace a tu correo.");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", "Error al enviar el correo.");
            }
        } else {
            model.addAttribute("error", "No existe una cuenta con ese correo.");
        }
        return "recuperar";
    }

    /**
     * Muestra el formulario para ingresar una nueva contraseña, accedido con un token válido.
     */
    @GetMapping("/nueva-password")
    public String mostrarFormularioCambio(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "nueva-password";
    }

    /**
     * Procesa y guarda la nueva contraseña introducida por el usuario.
     */
    @PostMapping("/nueva-password")
    public String guardarNuevaPassword(@RequestParam String token, @RequestParam String password, Model model) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(password)); // Encripta la nueva contraseña
            user.setResetToken(null); // Elimina el token para evitar reutilización
            userRepository.save(user);
            return "redirect:/login?recuperado=true";
        } else {
            model.addAttribute("error", "Token inválido.");
            return "nueva-password";
        }
    }

    /**
     * Endpoint de prueba para enviar un correo genérico.
     */
    @GetMapping("/test-email")
    public ResponseEntity<String> testCorreo() {
        emailService.enviarCorreo("test@example.com", "Correo de prueba", "Este es un correo de prueba desde Spring Boot.");
        return ResponseEntity.ok("Correo enviado");
    }
}
