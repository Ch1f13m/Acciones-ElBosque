package co.edu.unbosque.accioneselbosqueapi.service;

import co.edu.unbosque.accioneselbosqueapi.model.DTO.MarketAlertDTO;
import co.edu.unbosque.accioneselbosqueapi.model.entity.MarketAlert;
import co.edu.unbosque.accioneselbosqueapi.model.entity.User;
import co.edu.unbosque.accioneselbosqueapi.repository.MarketAlertRepository;
import co.edu.unbosque.accioneselbosqueapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona el envío y registro de alertas del mercado a los usuarios.
 */
@Service
public class MarketAlertService {

    @Autowired
    private MarketAlertRepository marketAlertRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Envía y registra una alerta relevante para los usuarios interesados.
     *
     * @param mercado     Mercado afectado (ej. NYSE)
     * @param tipo        Tipo de alerta (HORARIO, FERIADO, MANTENIMIENTO)
     * @param descripcion Detalle de la alerta
     */
    public void procesarAlerta(String mercado, String tipo, String descripcion) {
        List<User> usuarios = userRepository.findAll();

        for (User user : usuarios) {
            if (user.getMercadosInteres() != null && user.getMercadosInteres().contains(mercado)) {

                String mensaje = "[ALERTA " + tipo + "] " + mercado + ": " + descripcion;

                // Enviar por correo
                if (user.isNotificacionCorreo()) {
                    emailService.enviarCorreo(user.getEmail(), "Alerta de mercado", mensaje);
                }

                // TODO: enviar por SMS o App push si implementas
                // if (user.isNotificacionSMS()) { ... }
                // if (user.isNotificacionApp()) { ... }

                // Registrar alerta
                MarketAlert alerta = new MarketAlert(
                        mercado,
                        tipo,
                        descripcion,
                        LocalDateTime.now(),
                        user.getId()
                );
                marketAlertRepository.save(alerta);

                // TODO: notificar si tiene órdenes programadas afectadas
            }
        }
    }

    /**
     * Obtiene todas las alertas registradas para un usuario.
     *
     * @param usuarioId ID del usuario
     * @return lista de alertas en formato DTO
     */
    public List<MarketAlertDTO> obtenerAlertasPorUsuario(Long usuarioId) {
        return marketAlertRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(alerta -> modelMapper.map(alerta, MarketAlertDTO.class))
                .collect(Collectors.toList());
    }
}
