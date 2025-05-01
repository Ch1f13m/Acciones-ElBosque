package co.edu.unbosque.accioneselbosqueapi.controller.implementations;

import co.edu.unbosque.accioneselbosqueapi.model.DTO.MarketAlertDTO;
import co.edu.unbosque.accioneselbosqueapi.service.interfaces.IMarketAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que expone endpoints relacionados con las alertas de mercado para los usuarios.
 */
@RestController
@RequestMapping("/api/alertas/v1")
public class MarketAlertController {

    @Autowired
    private IMarketAlertService marketAlertService;

    /**
     * Endpoint para consultar el historial de alertas de un usuario específico.
     *
     * @param usuarioId ID del usuario
     * @return Lista de alertas relacionadas con los mercados de interés
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MarketAlertDTO>> obtenerAlertasPorUsuario(@PathVariable Long usuarioId) {
        List<MarketAlertDTO> alertas = marketAlertService.obtenerAlertasPorUsuario(usuarioId);
        return ResponseEntity.ok(alertas);
    }
}
