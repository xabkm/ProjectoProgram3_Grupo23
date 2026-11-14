package es.deusto.ingenieria.g23.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class CitaAgenda implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private LocalDateTime fechaHora;
    private TipoCita tipoCita;
    private Animal animal;
    private Persona personaAsignada;

    public CitaAgenda() {
    }

    public CitaAgenda(String id, LocalDateTime fechaHora, TipoCita tipoCita, Animal animal, Persona personaAsignada) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.animal = animal;
        this.personaAsignada = personaAsignada;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public TipoCita getTipoCita() { return tipoCita; }
    public void setTipoCita(TipoCita tipoCita) { this.tipoCita = tipoCita; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }

    public Persona getPersonaAsignada() { return personaAsignada; }
    public void setPersonaAsignada(Persona personaAsignada) { this.personaAsignada = personaAsignada; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitaAgenda cita = (CitaAgenda) o;
        return Objects.equals(id, cita.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String nombreAnimal = (animal != null) ? animal.getNombre() : "Sin animal";
        return "Cita [" + tipoCita + "] - " + fechaHora + " | Animal: " + nombreAnimal;
    }
}