package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PantallaCatalogo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panelNorte;
	private JPanel panelIzq;
	private JPanel panelCentro;
	private JTextField txtBuscar;
	private JButton btnBuscar, btnPerfil;
	
	public PantallaCatalogo() {
	
		
		setTitle("Catálogo de Adopciones");
		setSize(950, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setLayout(new BorderLayout(10, 10));
		panelNorte = new JPanel(new BorderLayout());
		panelNorte.setBorder(BorderFactory.createEmptyBorder(12, 15, 5, 20));
		panelNorte.setOpaque(true);
		panelNorte.setBackground(Color.WHITE);
		txtBuscar = new JTextField(15);
		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnBuscar.setBackground(Color.ORANGE.darker());
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setFocusPainted(false);
		btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBuscar.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));

		
		panelIzq = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Arial", Font.BOLD, 15));
		panelIzq.add(lblNombre);
		panelIzq.add(txtBuscar);
		panelIzq.add(btnBuscar);
		panelIzq.setOpaque(true);
		panelIzq.setBackground(Color.WHITE);
		
		ImageIcon imUsuarioSinEscalar = new ImageIcon("resources/images/usuario.jpg");
		Image usuarioEsc = imUsuarioSinEscalar.getImage().getScaledInstance(30, 30,Image.SCALE_SMOOTH); //recomendacion de la IA (Image.SCALE_SMOOTH) para que la imagen no salga pixelada
		ImageIcon imUsuario = new ImageIcon(usuarioEsc);
		
		btnPerfil = new JButton("Cuenta ", imUsuario);
		btnPerfil.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnPerfil.setForeground(Color.ORANGE.darker());
		btnPerfil.setOpaque(true);
		btnPerfil.setBackground(Color.WHITE);
		btnPerfil.setIconTextGap(10);
		btnPerfil.setBorder(BorderFactory.createLineBorder(Color.YELLOW.darker(),2));
		btnPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnPerfil.setContentAreaFilled(false); // linea de codigo hecha mediante IA para que al tocar el boton no salga el color de seleccion
		btnPerfil.addActionListener(new ActionListener() {
	
		
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Pantalla de perfil pendiente");
				
			}
		});
		panelNorte.add(btnPerfil, BorderLayout.EAST);
		panelNorte.add(panelIzq, BorderLayout.WEST);
		
		panelCentro = new JPanel();
		
		add(panelNorte, BorderLayout.NORTH);
		
		add(panelCentro, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		PantallaCatalogo v = new PantallaCatalogo();
		v.setVisible(true);
	}

}
