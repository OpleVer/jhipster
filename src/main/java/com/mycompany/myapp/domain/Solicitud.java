package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Origenes;

import com.mycompany.myapp.domain.enumeration.Estatus;

import com.mycompany.myapp.domain.enumeration.Interno,externo;

/**
 * A Solicitud.
 */

@Document(collection = "solicitud")
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("numero_solicitud")
    private Integer numero_solicitud;

    @NotNull
    @Field("fecha")
    private LocalDate fecha;

    @Field("hora")
    private ZonedDateTime hora;

    @NotNull
    @Field("acto_certificar")
    private String acto_certificar;

    @NotNull
    @Field("origen")
    private Origenes origen;

    @NotNull
    @Field("estatus")
    private Estatus estatus;

    @Field("prevencion")
    private Boolean prevencion;

    @NotNull
    @Field("tiporesponsable")
    private Interno,externo tiporesponsable;

    @NotNull
    @Field("nombre_responsable")
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

    public Solicitud numero_solicitud(Integer numero_solicitud) {
        this.numero_solicitud = numero_solicitud;
        return this;
    }

    public void setNumero_solicitud(Integer numero_solicitud) {
        this.numero_solicitud = numero_solicitud;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Solicitud fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getHora() {
        return hora;
    }

    public Solicitud hora(ZonedDateTime hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }

    public String getActo_certificar() {
        return acto_certificar;
    }

    public Solicitud acto_certificar(String acto_certificar) {
        this.acto_certificar = acto_certificar;
        return this;
    }

    public void setActo_certificar(String acto_certificar) {
        this.acto_certificar = acto_certificar;
    }

    public Origenes getOrigen() {
        return origen;
    }

    public Solicitud origen(Origenes origen) {
        this.origen = origen;
        return this;
    }

    public void setOrigen(Origenes origen) {
        this.origen = origen;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public Solicitud estatus(Estatus estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Boolean isPrevencion() {
        return prevencion;
    }

    public Solicitud prevencion(Boolean prevencion) {
        this.prevencion = prevencion;
        return this;
    }

    public void setPrevencion(Boolean prevencion) {
        this.prevencion = prevencion;
    }

    public Interno,externo getTiporesponsable() {
        return tiporesponsable;
    }

    public Solicitud tiporesponsable(Interno,externo tiporesponsable) {
        this.tiporesponsable = tiporesponsable;
        return this;
    }

    public void setTiporesponsable(Interno,externo tiporesponsable) {
        this.tiporesponsable = tiporesponsable;
    }

    public String getNombre_responsable() {
        return nombre_responsable;
    }

    public Solicitud nombre_responsable(String nombre_responsable) {
        this.nombre_responsable = nombre_responsable;
        return this;
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
        Solicitud solicitud = (Solicitud) o;
        if (solicitud.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, solicitud.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Solicitud{" +
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
