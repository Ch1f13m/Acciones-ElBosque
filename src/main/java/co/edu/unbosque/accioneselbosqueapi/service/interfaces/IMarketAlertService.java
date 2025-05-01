package co.edu.unbosque.accioneselbosqueapi.service.interfaces;

import co.edu.unbosque.accioneselbosqueapi.model.DTO.MarketAlertDTO;

import java.util.List;

/**
 * Interfaz que define las operaciones disponibles para el servicio de alertas de mercado.
 */
public interface IMarketAlertService {

    /**
     * Procesa una alerta de mercado y la envía a los usuarios interesados.
     *
     * @param mercado     Mercado afectado (ej: NYSE)
     * @param tipo        Tipo de alerta (ej: HORARIO, FERIADO, MANTENIMIENTO)
     * @param descripcion Detalle de la alerta
     */
    void procesarAlerta(String mercado, String tipo, String descripcion);

    /**
     * Devuelve todas las alertas registradas para un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Lista de alertas en formato DTO
     */
    List<MarketAlertDTO> obtenerAlertasPorUsuario(Long usuarioId);
}
