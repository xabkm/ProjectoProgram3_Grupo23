package es.deusto.ingenieria.g23.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Objects;

public class Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private  Especie especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private EstadoAnimal estado;
    private String descripcion;
    private boolean vacunado;
    private boolean esterilizado;
    private List<String> rutasFotos;

    public Animal() {
    	this.rutasFotos = new ArrayList<>();
    }

    public Animal(String id, String nombre, Especie especie, String raza, LocalDate fechaNacimiento, EstadoAnimal estado) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.descripcion = "Sin descripción disponible.";
        this.vacunado = false;
        this.esterilizado = false;
        this.rutasFotos = new ArrayList<>();
    }
    
    public Animal(String id, String nombre, Especie especie, String raza, LocalDate fechaNacimiento,
    		              EstadoAnimal estado, String descripcion, boolean vacunado, boolean esterilizado,
    		              List<String> rutasFotos) {
    		    this(id, nombre, especie, raza, fechaNacimiento, estado);
    		    this.descripcion = descripcion;
    		    this.vacunado = vacunado;
    		    this.esterilizado = esterilizado;
    	     if (rutasFotos != null) {
   	          this.rutasFotos = rutasFotos;
  		      }
    	    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public EstadoAnimal getEstado() { return estado; }
    public void setEstado(EstadoAnimal estado) { this.estado = estado; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public boolean isVacunado() { return vacunado; }
    public void setVacunado(boolean vacunado) { this.vacunado = vacunado; }
    
    public boolean isEsterilizado() { return esterilizado; }
    public void setEsterilizado(boolean esterilizado) { this.esterilizado = esterilizado; }
    
    public List<String> getRutasFotos() { return rutasFotos; }
    public void setRutasFotos(List<String> rutasFotos) { this.rutasFotos = rutasFotos; }
    

    // Método para calcular la edad aproximada
    public int getEdadAnios() {
        if (fechaNacimiento == null) return 0;
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nombre + " (" + especie + ", " + raza + ") - " + estado;
    }
}