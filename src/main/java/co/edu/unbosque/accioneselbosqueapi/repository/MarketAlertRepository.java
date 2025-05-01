package co.edu.unbosque.accioneselbosqueapi.repository;

import co.edu.unbosque.accioneselbosqueapi.model.entity.MarketAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceder a las alertas de mercado registradas.
 */
@Repository
public interface MarketAlertRepository extends JpaRepository<MarketAlert, Long> {

    // Buscar todas las alertas asociadas a un usuario
    List<MarketAlert> findByUsuarioId(Long usuarioId);

    // Buscar todas las alertas de un mercado específico (opcional)
    List<MarketAlert> findByMercado(String mercado);
}
