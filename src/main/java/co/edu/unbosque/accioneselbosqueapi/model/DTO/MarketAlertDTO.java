package co.edu.unbosque.accioneselbosqueapi.model.DTO;

import java.time.LocalDateTime;

/**
 * DTO para representar alertas relevantes del mercado enviadas al usuario.
 */
public class MarketAlertDTO {

    private Long id;
    private String mercado;
    private String tipo;
    private String descripcion;
    private LocalDateTime fechaAlerta;
    private Long usuarioId;

    public MarketAlertDTO() {
    }

    public MarketAlertDTO(Long id, String mercado, String tipo, String descripcion, LocalDateTime fechaAlerta, Long usuarioId) {
        this.id = id;
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

    public void setId(Long id) {
        this.id = id;
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
