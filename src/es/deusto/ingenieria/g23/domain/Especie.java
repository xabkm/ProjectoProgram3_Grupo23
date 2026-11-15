package es.deusto.ingenieria.g23.domain;

public enum Especie {
	PERRO("Perro"),
	GATO("Gato"),
	OTRO("Otro");
	
	private final String etiqueta;
	
	Especie(String etiqueta){
		this.etiqueta = etiqueta;
	}
	
	@Override
	public String toString() {
		return etiqueta;
	}
}
