package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.deusto.ingenieria.g23.domain.Animal;
import es.deusto.ingenieria.g23.domain.Especie;
import es.deusto.ingenieria.g23.domain.EstadoAnimal;
import es.deusto.ingenieria.g23.domain.EstadoSolicitud;
import es.deusto.ingenieria.g23.domain.Persona;
import es.deusto.ingenieria.g23.domain.SolicitudAdopcion;

public class PantallaFormulario extends JFrame{


	private static final long serialVersionUID = 1L;

	 public PantallaFormulario(Animal animal) {
		JFrame panelPrincipal = this;
		JPanel separacion = new JPanel(new BorderLayout());
		separacion.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); 
		JPanel pnlFormulario = new JPanel();
		pnlFormulario.setLayout(new BoxLayout(pnlFormulario, BoxLayout.Y_AXIS));
//		JPanel pnlFormulario = new JPanel(new GridLayout(3,2));
		panelPrincipal.setTitle("FORMULARIO ADOPCION");
		
		
		
		// Titulo y boton de finalizar
		JLabel titulo = new JLabel("Solicitud de adopcion de " + animal.getNombre());
		titulo.setFont(new Font("Arial", Font.BOLD, 16));
		titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		separacion.add(titulo, BorderLayout.NORTH);
		JButton finalizarBtt = new JButton("Finalizar");
		JButton cancelarBtt = new JButton("Cancelar");
		JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelButton.add(cancelarBtt);
		panelButton.add(finalizarBtt);
		separacion.add(panelButton, BorderLayout.SOUTH);
		
		panelPrincipal.getRootPane().setDefaultButton(finalizarBtt);
		
		// Añadimos los datos que pedimos
		JTextField campoNombre = new JTextField(20);
		JTextField campoApellidos = new JTextField(20);
		JTextField campoDni = new JTextField(20);
		JTextField campoEmail = new JTextField(20);
		JTextField campoTlf = new JTextField(20);

		// Añadimos los 4 campos obligatorios de la Pantalla 4 fila por fila
		pnlFormulario.add(crearFila("Nombre:", campoNombre));
		pnlFormulario.add(crearFila("Apellidos:", campoApellidos));
		pnlFormulario.add(crearFila("DNI:", campoDni));
		pnlFormulario.add(crearFila("Email:", campoEmail));
		pnlFormulario.add(crearFila("Telefono:", campoTlf));
		
		
		separacion.add(pnlFormulario, BorderLayout.CENTER);
		panelPrincipal.add(separacion);
		
		
		// Cuando pulsemos el boton tiene que guardar la informacion
		// Crea una nueva persona y solicitud de adopcion con ella
		finalizarBtt.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {	
				
				String nombre = campoNombre.getText().trim();
				String apellidos = campoApellidos.getText().trim();
				String dni = campoDni.getText().trim().toUpperCase();
				String email = campoEmail.getText().trim();
				String tlf = campoTlf.getText().trim();
				
				if(nombre.isEmpty()) {
					JOptionPane.showMessageDialog(panelPrincipal,"El nombre es obligatorio"); return;
				}
				
				if(apellidos.isEmpty()) {
					JOptionPane.showMessageDialog(panelPrincipal,"Los apellidos son obligatorios"); return;
				}
				
				//IAG(Claude) 	Me ha dicho como alidar DNI, email y telefono
				if(!dni.matches("\\d{8}[A-Z]")) {
					JOptionPane.showMessageDialog(panelPrincipal,"El DNI debe tener 8 números y una letra (ej: 12345678Z)"); return;
				}
				
				if(!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
					JOptionPane.showMessageDialog(panelPrincipal,"El email no es válido (ej: nombre@correo.com)"); return;
				}
				if(!tlf.matches("\\d{9}")) {
					JOptionPane.showMessageDialog(panelPrincipal,"El teléfono debe tener 9 números"); return;
				}
				
				Persona persona = new Persona();
				persona.setNombre(nombre);
				persona.setApellidos(apellidos);
				persona.setDni(dni);
				persona.setEmail(email);
				persona.setTelefono(tlf);
				
				
				SolicitudAdopcion solicitud = new SolicitudAdopcion();
				solicitud.setSolicitante(persona);
				solicitud.setFechaSolicitud(LocalDateTime.now());
				solicitud.setEstado(EstadoSolicitud.PENDIENTE);
				solicitud.setAnimal(animal);
				animal.setEstado(EstadoAnimal.ADOPTADO);
				
				JOptionPane.showMessageDialog(panelPrincipal, "La solicitud ha sido confirmada.\nFecha: " + solicitud.getFechaSolicitud().toLocalDate() + "\nDNI: " + persona.getDni());
				
				panelPrincipal.dispose();
				
			}

		});
		
		cancelarBtt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				panelPrincipal.dispose();
				
			}
			
		});
		
		
		panelPrincipal.setSize(500, 350);
		panelPrincipal.setResizable(false);        
		panelPrincipal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panelPrincipal.setLocationRelativeTo(null);        
		panelPrincipal.setVisible(true);
	}
	
	
	// IAG (herramienta: Gemini)
	// Gemini me ha dado la idea y codigo de usar BoxLayout y FlowLayout dentro
	//SIN CAMBIOS
	private static JPanel crearFila(String texto, JTextField campoTexto) {
		// FlowLayout.LEFT alinea a la izquierda y deja 10px de espacio entre el JLabel y el JTextField
		JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
	    JLabel lbl = new JLabel(texto);
	    
	    lbl.setPreferredSize(new Dimension(85, 20));
	    
	    fila.add(lbl);
	    fila.add(campoTexto);
	    return fila;
				
	}
	
	
	public static void main(String[] args) {
		new PantallaFormulario(new Animal("A001", "Toby", Especie.PERRO, "Mestizo", LocalDate.of(2022, 5, 10), EstadoAnimal.DISPONIBLE)).setVisible(true);
	}
	
	

}




