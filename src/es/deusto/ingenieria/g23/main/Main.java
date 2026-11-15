package es.deusto.ingenieria.g23.main;

import javax.swing.SwingUtilities;
import es.deusto.ingenieria.g23.gui.PantallaCatalogo;

public class Main {


	public static void main(String[] args) {
		
		
		
		SwingUtilities.invokeLater(() -> new PantallaCatalogo().setVisible(true));

	}

}
