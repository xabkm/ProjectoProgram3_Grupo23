package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PantallaCatalogo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panelNorte;
	private JPanel panelOeste;
	private JPanel panelCentro;
	private JTextField txtBuscar;
	private JButton btnBuscar;
	
	public PantallaCatalogo() {
		setTitle("Catálogo de Adopciones");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setLayout(new BorderLayout(10, 10));
		panelNorte = new JPanel();
		txtBuscar = new JTextField(15);
		btnBuscar = new JButton("Buscar");
		
		panelNorte.add(new JLabel("Nombre: "));
		panelNorte.add(txtBuscar);
		panelNorte.add(btnBuscar);
		
		panelOeste = new JPanel();
		panelCentro = new JPanel();
		
		add(panelNorte, BorderLayout.NORTH);
		add(panelOeste, BorderLayout.WEST);
		add(panelCentro, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		PantallaCatalogo v = new PantallaCatalogo();
		v.setVisible(true);
	}

}
