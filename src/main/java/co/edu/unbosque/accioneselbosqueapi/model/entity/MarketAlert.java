package co.edu.unbosque.accioneselbosqueapi.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa una alerta relevante del mercado enviada a los usuarios.
 */
@Entity
@Table(name = "market_alerts")
public class MarketAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mercado;         // Ej: NYSE, NASDAQ, LONDRES
    private String tipo;            // Ej: HORARIO, FERIADO, MANTENIMIENTO
    private String descripcion;     // Detalle de la alerta
    private LocalDateTime fechaAlerta;

    private Long usuarioId;         // Para registrar a qué usuario se le envió (opcional si es general)

    public MarketAlert() {}

    public MarketAlert(String mercado, String tipo, String descripcion, LocalDateTime fechaAlerta, Long usuarioId) {
        this.mercado = mercado;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaAlerta = fechaAlerta;
        this.usuarioId = usuarioId;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getMercado() {
        return mercado;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(LocalDateTime fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
