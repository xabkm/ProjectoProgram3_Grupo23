package es.deusto.ingenieria.g23.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class SolicitudAdopcion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Animal animal;
    private Persona solicitante;
    private LocalDateTime fechaSolicitud;
    private EstadoSolicitud estado;

    public SolicitudAdopcion() {
    }

    public SolicitudAdopcion(String id, Animal animal, Persona solicitante, LocalDateTime fechaSolicitud, EstadoSolicitud estado) {
        this.id = id;
        this.animal = animal;
        this.solicitante = solicitante;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }

    public Persona getSolicitante() { return solicitante; }
    public void setSolicitante(Persona solicitante) { this.solicitante = solicitante; }

    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolicitudAdopcion que = (SolicitudAdopcion) o;
        return Objects.equals(id, que.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String nomAnimal = (animal != null) ? animal.getNombre() : "N/A";
        String nomSolicitante = (solicitante != null) ? solicitante.getNombre() : "N/A";
        return "Solicitud #" + id + " [" + estado + "] - Animal: " + nomAnimal + " | Solicitante: " + nomSolicitante;
    }
}