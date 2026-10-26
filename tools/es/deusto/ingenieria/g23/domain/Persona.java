package es.deusto.ingenieria.g23.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dni;
    private String nombre;
    private String email;
    private String telefono;
    private ArrayList animales_adoptados;	//Lista de los animales que adopta cada uno

    public Persona() {
    }

    public Persona(String dni, String nombre, String email, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(dni, persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return nombre + " (DNI: " + dni + ")";
    }
}