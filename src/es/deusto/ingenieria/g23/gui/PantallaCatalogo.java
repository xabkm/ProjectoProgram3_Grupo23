package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import es.deusto.ingenieria.g23.domain.Administrador;
import es.deusto.ingenieria.g23.domain.Animal;
import es.deusto.ingenieria.g23.domain.Especie;

public class PantallaCatalogo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panelNorte, panelIzq, panelCentro, panelFiltros, pControles;
	private JTextField txtBuscar;
	private JButton btnBuscar, btnPerfil,  btnLimpiar;
	private Map<Especie, JCheckBox> checksEspecie;
	private JCheckBox chkDisponibles;
	
	public PantallaCatalogo() {
	
		
		setTitle("Catálogo de Adopciones");
		setSize(950, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// cabecera
		setLayout(new BorderLayout());
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
	
			public void actionPerformed(ActionEvent e) {
				abrirCuenta();
			}
			
		});
		panelNorte.add(btnPerfil, BorderLayout.EAST);
		panelNorte.add(panelIzq, BorderLayout.WEST);
		
		
		
		
		
	
		
		// Panel izquierdo de filtros
		panelFiltros = new JPanel(new BorderLayout());
		
		panelFiltros.setPreferredSize(new Dimension(210,0));
		panelFiltros.setBackground(Color.WHITE);

		Border margen = BorderFactory.createEmptyBorder(10, 15, 15, 0);
		Border linea = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true);
		TitledBorder titulo = BorderFactory.createTitledBorder(linea, "Filtros ");
		titulo.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
		titulo.setTitleColor(Color.ORANGE.darker());
		panelFiltros.setBorder(BorderFactory.createCompoundBorder(margen, titulo));
		pControles = new JPanel(new GridLayout(0, 1, 5, 8));
		pControles.setBackground(Color.WHITE);
		pControles.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
		
		JLabel lblEspecies = new JLabel("Especies :");
		lblEspecies.setFont(new Font("SansSerif", Font.BOLD, 12));
		pControles.add(lblEspecies);

		checksEspecie = new HashMap<>();
		for (Especie esp : Especie.values()) {
			JCheckBox chk = new JCheckBox(esp.toString());
			chk.setBackground(Color.WHITE);
			chk.setFont(new Font("SansSerif", Font.PLAIN, 13));
			chk.setFocusPainted(false);
			checksEspecie.put(esp, chk);
			pControles.add(chk);
		}

		pControles.add(new JSeparator()); //linea horizontal que divide secciones (metodo sugerido por IA)
		
		chkDisponibles = new JCheckBox("Solo disponibles");
		chkDisponibles.setBackground(Color.WHITE);
		chkDisponibles.setFont(new Font("SansSerif", Font.PLAIN, 13));
		chkDisponibles.setFocusPainted(false);
		
		btnLimpiar = new JButton("Limpiar filtros");
		btnLimpiar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnLimpiar.setBackground(Color.WHITE);
		btnLimpiar.setFocusPainted(false);
		btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		Border lineaLimp = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true);
		Border relleno = BorderFactory.createEmptyBorder(6, 10, 6, 10);
		btnLimpiar.setBorder(BorderFactory.createCompoundBorder(lineaLimp, relleno));
		
		pControles.add(chkDisponibles);
		pControles.add(new JLabel(""));
		pControles.add(btnLimpiar);

		panelFiltros.add(pControles, BorderLayout.NORTH);
		
		// panel central (scroll)
		panelCentro = new JPanel(new BorderLayout());
		panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panelCentro.setBackground(Color.WHITE);
		JScrollPane scrollCentro = new JScrollPane(panelCentro);	
		add(panelNorte, BorderLayout.NORTH);
		add(panelFiltros, BorderLayout.WEST);
		add(scrollCentro, BorderLayout.CENTER);
	}
	
	// abrir el panel de administracion:
	private void abrirCuenta() {
		int opcion = JOptionPane.showConfirmDialog(this,
				"El perfil de usuario todavía no está hecho.\n¿Quieres abrir el panel de administración?",
				"Cuenta", JOptionPane.YES_NO_OPTION);

		if (opcion == JOptionPane.YES_OPTION) {
			Administrador admin = new Administrador("00000000T", "admin", "", "admin@refugio.es", "000000000");
		//	PantallaAdministracion.abrir(admin);
		}
	}
	
	private void abrirDetalle(Animal animal) {
		PantallaDetalleAnimal detalle = new PantallaDetalleAnimal(animal, this);
		setVisible(false);
		detalle.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		PantallaCatalogo v = new PantallaCatalogo();
		v.setVisible(true);
	}

}
