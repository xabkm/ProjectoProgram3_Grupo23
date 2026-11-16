package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.deusto.ingenieria.g23.domain.EstadoSolicitud;
import es.deusto.ingenieria.g23.domain.Persona;
import es.deusto.ingenieria.g23.domain.SolicitudAdopcion;

public class PantallaFormulario extends JFrame{

	public PantallaFormulario() {
	}//VA A HACER FALTA JTextField, JButton, JLabel
	
	public static void main(String[] args) {
		JFrame panelPrincipal = new JFrame();
		JPanel separacion = new JPanel(new BorderLayout());
		JPanel pnlFormulario = new JPanel();
		pnlFormulario.setLayout(new BoxLayout(pnlFormulario, BoxLayout.Y_AXIS));
//		JPanel pnlFormulario = new JPanel(new GridLayout(3,2));
		panelPrincipal.setTitle("FORMULARIO ADOPCION");
		
		
		
		// Titulo y boton de finalizar
		JLabel titulo = new JLabel("FORMULARIO ADOPCION");
		separacion.add(titulo, BorderLayout.NORTH);
		JButton finalizar = new JButton("Finalizar");
		separacion.add(finalizar, BorderLayout.SOUTH);
		
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
		finalizar.addMouseListener(new MouseAdapter() {			
			public void mouseClicked(MouseEvent e) {				
				Persona persona = new Persona();
				persona.setNombre(campoNombre.getText());
				persona.setApellidos(campoApellidos.getText());
				persona.setDni(campoDni.getText());
				persona.setEmail(campoEmail.getText());
				persona.setTelefono(campoTlf.getText());
				
				SolicitudAdopcion solicitud = new SolicitudAdopcion();
				solicitud.setSolicitante(persona);
				solicitud.setFechaSolicitud(LocalDateTime.now());
				solicitud.setEstado(EstadoSolicitud.PENDIENTE);
				
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
		JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
	    JLabel lbl = new JLabel(texto);
	    
	    fila.add(lbl);
	    fila.add(campoTexto);
	    return fila;
				
	}
	
	
	

}




