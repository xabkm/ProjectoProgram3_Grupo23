package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.deusto.ingenieria.g23.domain.Persona;

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
		JTextField campoApellido1 = new JTextField(20);
		JTextField campoApellido2 = new JTextField(20);
		JTextField campoDni = new JTextField(20);
		JTextField campoEmail = new JTextField(20);
		JTextField campoTlf = new JTextField(20);

		// Añadimos los 4 campos obligatorios de la Pantalla 4 fila por fila
		pnlFormulario.add(crearFila("Nombre:", campoNombre));
		pnlFormulario.add(crearFila("Primer Apellido:", campoApellido1));
		pnlFormulario.add(crearFila("Segundo Apellido:", campoApellido2));
		pnlFormulario.add(crearFila("DNI:", campoDni));
		pnlFormulario.add(crearFila("Email:", campoEmail));
		pnlFormulario.add(crearFila("Telefono:", campoTlf));
		
		
		separacion.add(pnlFormulario, BorderLayout.CENTER);
		panelPrincipal.add(separacion);
		
		
		// Cuando pulsemos el boton tiene que guardar la informacion
		// Crea una nueva persona y solicitud de adopcion con ella
		finalizar.addMouseListener(new MouseAdapter() {			
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showConfirmDialog(null, "Desea salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
				
				Persona persona = new Persona();
				persona.setNombre(campoNombre.getText());
				
			}
		
		});
		
		
		panelPrincipal.setSize(800, 500);
		panelPrincipal.setResizable(false);        
		panelPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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




