package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import es.deusto.ingenieria.g23.domain.Administrador;
import es.deusto.ingenieria.g23.domain.Persona;

public class PantallaAdministracion extends JFrame {

	private static final long serialVersionUID = 1L;
	private Persona adminActual;

	public PantallaAdministracion(Persona usuario) {
		
		if (!(usuario instanceof Administrador)) {
			JOptionPane.showMessageDialog(null, 
				"Acceso denegado. Esta vista es exclusiva para administradores.", 
				"Error de permisos", JOptionPane.ERROR_MESSAGE);
			dispose();
			return;
		}

		this.adminActual = usuario;

		setTitle("Panel de Administración - Usuario: " + adminActual.getNombre());
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JLabel lblTitulo = new JLabel("Bienvenido al Panel de Administración", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
		add(lblTitulo, BorderLayout.CENTER);
	}

	// metodo para ver la ventana desde aqui
	public static void main(String[] args) {
		Administrador adminPrueba = new Administrador();
		PantallaAdministracion ventana = new PantallaAdministracion(adminPrueba);
		ventana.setVisible(true);
	}
}