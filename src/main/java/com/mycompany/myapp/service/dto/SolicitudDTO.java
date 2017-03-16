package com.mycompany.myapp.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.Origenes;
import com.mycompany.myapp.domain.enumeration.Estatus;
import com.mycompany.myapp.domain.enumeration.Internoexterno;

/**
 * A DTO for the Solicitud entity.
 */
public class SolicitudDTO implements Serializable {

    private String id;

    @NotNull
    private Integer numero_solicitud;

    @NotNull
    private LocalDate fecha;

    private ZonedDateTime hora;

    @NotNull
    private String acto_certificar;

    @NotNull
    private Origenes origen;

    @NotNull
    private Estatus estatus;

    private Boolean prevencion;

    @NotNull
    private Internoexterno tiporesponsable;

    @NotNull
    private String nombre_responsable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Integer getNumero_solicitud() {
        return numero_solicitud;
    }

    public void setNumero_solicitud(Integer numero_solicitud) {
        this.numero_solicitud = numero_solicitud;
    }
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public ZonedDateTime getHora() {
        return hora;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }
    public String getActo_certificar() {
        return acto_certificar;
    }

    public void setActo_certificar(String acto_certificar) {
        this.acto_certificar = acto_certificar;
    }
    public Origenes getOrigen() {
        return origen;
    }

    public void setOrigen(Origenes origen) {
        this.origen = origen;
    }
    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
    public Boolean getPrevencion() {
        return prevencion;
    }

    public void setPrevencion(Boolean prevencion) {
        this.prevencion = prevencion;
    }
    public Internoexterno getTiporesponsable() {
        return tiporesponsable;
    }

    public void setTiporesponsable(Internoexterno tiporesponsable) {
        this.tiporesponsable = tiporesponsable;
    }
    public String getNombre_responsable() {
        return nombre_responsable;
    }

    public void setNombre_responsable(String nombre_responsable) {
        this.nombre_responsable = nombre_responsable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SolicitudDTO solicitudDTO = (SolicitudDTO) o;

        if ( ! Objects.equals(id, solicitudDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SolicitudDTO{" +
            "id=" + id +
            ", numero_solicitud='" + numero_solicitud + "'" +
            ", fecha='" + fecha + "'" +
            ", hora='" + hora + "'" +
            ", acto_certificar='" + acto_certificar + "'" +
            ", origen='" + origen + "'" +
            ", estatus='" + estatus + "'" +
            ", prevencion='" + prevencion + "'" +
            ", tiporesponsable='" + tiporesponsable + "'" +
            ", nombre_responsable='" + nombre_responsable + "'" +
            '}';
    }
}
