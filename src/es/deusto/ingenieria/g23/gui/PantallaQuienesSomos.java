package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// IAG (herramienta: Claude)
// Claude ha ayudado con: la ventana "Quiénes somos" (descripción en HTML, GIF animado y botón para abrir el MP4).
public class PantallaQuienesSomos extends JFrame {
	private static final long serialVersionUID = 1L;

	// colores sacados del logo (los mismos que en el catálogo)
	private static final Color OSCURO = new Color(52, 61, 79);
	private static final Color TERRACOTA = new Color(184, 107, 80);
	private static final Color BEIGE = new Color(247, 243, 237);

	private static final String RUTA_GIF = "resources/images/anuncio.gif"; // el vídeo que se ve dentro de la ventana
	private static final String RUTA_MP4 = "resources/images/anuncio.mp4"; // el mismo vídeo en alta calidad

	public PantallaQuienesSomos(JFrame padre) {
		setTitle("Planeta Animal");
		setIconImage(new ImageIcon("resources/images/logo.jpg").getImage());
		setBounds(padre.getBounds()); // misma posición y tamaño que el catálogo
		setMinimumSize(new Dimension(950, 580));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		add(crearCabecera(), BorderLayout.NORTH); // <-- Cambiado de crearTitulo() a crearCabecera()
		add(crearPanelVideo(), BorderLayout.WEST);
		add(crearPanelTexto(), BorderLayout.CENTER);
		
		// Oculta el catálogo y lo vuelve a mostrar al cerrar esta ventana (con Volver o con la X) 
				padre.setVisible(false);
				addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						padre.setVisible(true);
					}
				});
				
	}

	// cabecera: logo y título a la izquierda, botón para volver a la derecha
	private JPanel crearCabecera() {
		Image logoEsc = new ImageIcon("resources/images/logo.jpg").getImage().getScaledInstance(60, 52, Image.SCALE_SMOOTH);
		JLabel lblTitulo = new JLabel("Quiénes somos", new ImageIcon(logoEsc), JLabel.LEFT);
		lblTitulo.setIconTextGap(12);
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblTitulo.setForeground(Color.DARK_GRAY);

		JButton btnVolver = new JButton("\u2190 Volver al catálogo");
		btnVolver.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnVolver.setForeground(Color.ORANGE.darker());
		btnVolver.setOpaque(true);
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.ORANGE.darker(), 2), BorderFactory.createEmptyBorder(8, 16, 8, 16)));
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.setContentAreaFilled(false);
		btnVolver.setFocusPainted(false);
		btnVolver.addActionListener(e -> dispose()); // cierra esta pantalla y dispara windowClosed

		JPanel panelBoton = new JPanel(new BorderLayout());
		panelBoton.setOpaque(false);
		panelBoton.setBorder(BorderFactory.createEmptyBorder(9, 0, 9, 0));
		panelBoton.add(btnVolver, BorderLayout.CENTER);

		JPanel cabecera = new JPanel(new BorderLayout());
		cabecera.setBackground(Color.WHITE);
		cabecera.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 3, 0, Color.ORANGE.darker()), BorderFactory.createEmptyBorder(8, 15, 8, 20)));
		cabecera.add(lblTitulo, BorderLayout.WEST);
		cabecera.add(panelBoton, BorderLayout.EAST);
		return cabecera;
	}

	// parte izquierda: el vídeo y el botón para abrirlo en el reproductor
	private JPanel crearPanelVideo() {
		// un GIF animado se reproduce solo dentro de un JLabel
		JLabel lblVideo = new JLabel("", JLabel.CENTER);
		if (new File(RUTA_GIF).exists()) {
			lblVideo.setIcon(new ImageIcon(RUTA_GIF));
		} else {
			lblVideo.setText("Vídeo no disponible");
			lblVideo.setPreferredSize(new Dimension(480, 270));
		}
		lblVideo.setBorder(BorderFactory.createLineBorder(TERRACOTA, 2));

		JButton btnVideo = new JButton("Abrir el vídeo completo");
		btnVideo.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnVideo.setBackground(TERRACOTA);
		btnVideo.setForeground(Color.WHITE);
		btnVideo.setFocusPainted(false);
		btnVideo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVideo.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		btnVideo.addActionListener(e -> abrirVideoCompleto());

		JPanel bloqueVideo = new JPanel(new BorderLayout(0, 12)); // vídeo + botón juntos, para que el vídeo no se estire
		bloqueVideo.setOpaque(false);
		bloqueVideo.add(lblVideo, BorderLayout.CENTER);
		bloqueVideo.add(btnVideo, BorderLayout.SOUTH);

		JPanel panelVideo = new JPanel(new BorderLayout());
		panelVideo.setBackground(Color.WHITE);
		panelVideo.setBorder(BorderFactory.createEmptyBorder(40, 25, 18, 10));
		panelVideo.add(bloqueVideo, BorderLayout.NORTH);
		return panelVideo;
	}

	// parte derecha: la descripción de la empresa (JEditorPane permite escribirla en HTML)
	private JPanel crearPanelTexto() {
		JEditorPane txtDescripcion = new JEditorPane("text/html",
				"<html><body style='font-family:SansSerif; font-size:14pt; color:#343D4F'>"
				+ "<h2 style='color:#B86B50'>Planeta Animal</h2>"
				+ "<p>Somos un refugio que trabaja para que perros, gatos y otras mascotas "
				+ "encuentren una familia que los quiera.</p>"
				+ "<h3 style='color:#B86B50'>Qué hacemos</h3>"
				+ "<ul style='margin-left:18px'>"
				+ "<li><b>Rescate:</b> recogemos animales abandonados o sin hogar.</li>"
				+ "<li><b>Cuidados:</b> atención veterinaria y mucho cariño mientras esperan.</li>"
				+ "<li><b>Adopción:</b> buscamos la familia adecuada para cada animal.</li>"
				+ "<li><b>Acogida y apadrinamiento:</b> si no puedes adoptar, puedes acoger o apadrinar.</li>"
				+ "<li><b>Seguimiento:</b> te acompañamos antes y después de la adopción.</li>"
				+ "</ul>"
				+ "<p>Desde este catálogo puedes conocer a los animales que buscan hogar "
				+ "y empezar el proceso de adopción.</p>"
				+ "</body></html>");
		txtDescripcion.setEditable(false);
		txtDescripcion.setCaretPosition(0); // para que el texto empiece arriba del todo

		JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
		scrollDescripcion.setBorder(BorderFactory.createEmptyBorder());
	

		JPanel panelTexto = new JPanel(new BorderLayout());
		panelTexto.setBackground(Color.WHITE);
		panelTexto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelTexto.add(scrollDescripcion, BorderLayout.CENTER);
		return panelTexto;
	}

	// abre el MP4 con el reproductor que tenga el ordenador
	private void abrirVideoCompleto() {
		File mp4 = new File(RUTA_MP4);
		try {
			if (!mp4.exists() || !Desktop.isDesktopSupported()) {
				throw new IOException("No se encuentra el vídeo");
			}
			Desktop.getDesktop().open(mp4);
		} catch (IOException | UnsupportedOperationException ex) {
			JOptionPane.showMessageDialog(this, "No se ha podido abrir " + RUTA_MP4, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
