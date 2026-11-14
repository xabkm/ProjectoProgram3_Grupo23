package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PantallaFormulario extends JFrame{

	public PantallaFormulario() {
	}
	
	public static void main(String[] args) {
		JFrame panelPrincipal = new JFrame();
		JPanel separacion = new JPanel(new BorderLayout());
		JPanel pnlFormulario = new JPanel(new GridLayout(0, 2, 20, 25));
		panelPrincipal.setTitle("FORMULARIO ADOPCION");
		
		// Titulo y boton de finalizar
		separacion.add(new JLabel("FORMULARIO ADOPCION"), BorderLayout.NORTH);
		separacion.add(new JButton("Enviar"), BorderLayout.SOUTH);
		
		// Añadimos los datos que pedimos
		pnlFormulario.add(new JLabel("Nombre:"));
		pnlFormulario.add(new JTextField());
		pnlFormulario.add(new JLabel("Primer Apellido:"));
		pnlFormulario.add(new JTextField());
		pnlFormulario.add(new JLabel("Segundo Apellido:"));
		pnlFormulario.add(new JTextField());
		pnlFormulario.add(new JLabel("DNI:"));
		pnlFormulario.add(new JTextField());
		pnlFormulario.add(new JLabel("Email:"));
		pnlFormulario.add(new JTextField());
		pnlFormulario.add(new JLabel("Telefono:"));
		pnlFormulario.add(new JTextField());
		
		
		separacion.add(pnlFormulario, BorderLayout.CENTER);
		
		panelPrincipal.add(separacion);
		
		panelPrincipal.setSize(800, 500);
		panelPrincipal.setResizable(false);
        
		panelPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelPrincipal.setLocationRelativeTo(null);        
        
		panelPrincipal.setVisible(true);
	}

}



//VA A HACER FALTA JTextField, JButton, JLabel
