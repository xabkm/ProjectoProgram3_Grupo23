package es.deusto.ingenieria.g23.domain;

public class Usuario extends Persona{
	
	private static final long serialVersionUID = 1L;
	
	public Usuario() {
		
	}
	public Usuario(String dni, String nombre, String apellidos, String email, String telefono) {
		super(dni, nombre, apellidos, email, telefono);
	}
	
	
	
}
