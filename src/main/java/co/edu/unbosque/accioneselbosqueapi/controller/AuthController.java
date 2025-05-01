package co.edu.unbosque.accioneselbosqueapi.controller;

import co.edu.unbosque.accioneselbosqueapi.model.entity.User;
import co.edu.unbosque.accioneselbosqueapi.repository.UserRepository;
import co.edu.unbosque.accioneselbosqueapi.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // Log para consola/debug
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);
    // Log para Splunk
    private static final org.slf4j.Logger splunkLogger = org.slf4j.LoggerFactory.getLogger("splunk");

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/registro")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "registro";
    }

    @PostMapping("/registro")
    public String processRegister(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        splunkLogger.info("Nuevo usuario registrado: {}", user.getUsername());
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        logger.info("Acceso al formulario de login - Acciones El Bosque");
        return "login";
    }

    @GetMapping("/panel")
    public String showUserPanel() {
        logger.info("Acceso al panel de usuario - Acciones El Bosque");
        return "panel";
    }

    @GetMapping("/recuperar")
    public String mostrarFormularioRecuperar() {
        logger.info("Acceso al formulario de recuperacion - Acciones El Bosque");
        return "recuperar";
    }

    @PostMapping("/recuperar")
    public String procesarRecuperar(@RequestParam String email, Model model) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user);

            String link = "http://localhost:8080/nueva-password?token=" + token;
            String asunto = "Recuperación de contraseña - Acciones El Bosque";
            String cuerpo = "Hola " + user.getNombre() + ",\n\n"
                    + "Haz clic en el siguiente enlace para restablecer tu contraseña:\n"
                    + link;

            try {
                emailService.enviarCorreo(email, asunto, cuerpo);
                model.addAttribute("mensaje", "Se ha enviado un enlace a tu correo.");
                splunkLogger.info("Correo de recuperación enviado a: {}", email);
            } catch (Exception e) {
                model.addAttribute("error", "Error al enviar el correo.");
                logger.error("Fallo envío de correo: {}", e.getMessage());
                splunkLogger.error("Error al enviar correo de recuperación a {}: {}", email, e.getMessage());
            }
        } else {
            model.addAttribute("error", "No existe una cuenta con ese correo.");
            splunkLogger.warn("Intento de recuperación para correo inexistente: {}", email);
        }
        logger.info("Solicito envio a correo - Acciones El Bosque", email);
        return "recuperar";
    }

    @GetMapping("/nueva-password")
    public String mostrarFormularioCambio(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        logger.info("genero token de correo- Acciones El Bosque");
        return "nueva-password";
    }

    @PostMapping("/nueva-password")
    public String guardarNuevaPassword(@RequestParam String token, @RequestParam String password, Model model) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(password));
            user.setResetToken(null);
            userRepository.save(user);
            logger.info("Usuario {} ha restablecido su contraseña", user.getUsername());
            return "redirect:/login?recuperado=true";
        } else {
            model.addAttribute("error", "Token inválido.");
            logger.info("Intento de restablecimiento con token inválido: {}", token);
            return "nueva-password";

        }
        
    }

    @GetMapping("/test-email")
    public ResponseEntity<String> testCorreo() {
        emailService.enviarCorreo("test@example.com", "Correo de prueba", "Este es un correo de prueba desde Spring Boot.");
        logger.info("Se envió un correo de prueba desde el endpoint /test-email");
        return ResponseEntity.ok("Correo enviado");
    }
}
