package es.deusto.ingenieria.g23.gui;

import javax.swing.JFrame;

public class PantallaCatalogo extends JFrame {
	private static final long serialVersionUID = 1L;
	public PantallaCatalogo() {
		setTitle("Catálogo de Adopciones");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		PantallaCatalogo v = new PantallaCatalogo();
		v.setVisible(true);
	}

}
