package es.deusto.ingenieria.g23.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Acogida {

	private final String id_a;
	private final Especie especie;
	private LocalDate fecha_ac;
	private EstadoAnimal estado;
	
	public Acogida(String id_a, Especie especie, LocalDate fecha_ac, EstadoAnimal estado) {
		this.id_a = id_a;
		this.especie = especie;
		this.fecha_ac = fecha_ac;
		this.estado = estado;
	}


	public LocalDate getFecha_ac() {return fecha_ac;}
	public void setFecha_ac(LocalDate fecha_ac) {this.fecha_ac = fecha_ac;}
	public EstadoAnimal getEstado() {return estado;}
	public void setEstado(EstadoAnimal estado) {this.estado = estado;}
	public String getId_a() {return id_a;}
	public Especie getEspecie() {return especie;}


	@Override
	public String toString() {
		 return "Acogida [ID=" + id_a + ", especie=" + especie + ", fecha_ac=" + fecha_ac + ", estado=" + estado + "]"; // correcion hecha por la IA
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(especie, estado);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Acogida other = (Acogida) obj;
		return Objects.equals(especie, other.especie) && estado == other.estado
				&& Objects.equals(fecha_ac, other.fecha_ac) && Objects.equals(id_a, other.id_a);
	}


}
