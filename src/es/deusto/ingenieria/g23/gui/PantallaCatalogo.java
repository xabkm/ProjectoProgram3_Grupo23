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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import es.deusto.ingenieria.g23.domain.EstadoAnimal;
import es.deusto.ingenieria.g23.domain.Usuario;

public class PantallaCatalogo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panelNorte, panelIzq, panelCentro, panelFiltros, pControles, contenedor;
	private JTextField txtBuscar;
	private JButton btnBuscar, btnPerfil,  btnLimpiar;
	private Map<Especie, JCheckBox> checksEspecie;
	private JCheckBox chkDisponibles;
	private List<Animal> animales;
	
	public PantallaCatalogo() {
	
		
		setTitle("Catálogo de Adopciones");
		setSize(950, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		// logo de la empresa:
		ImageIcon logoSinEscalar = new ImageIcon("resources/images/logo.jpg");
		setIconImage(logoSinEscalar.getImage());
		
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

		
		panelIzq = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 12));
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
		
		// boton quienes somos
		JButton btnQuienes = new JButton("Quiénes somos");
		btnQuienes.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnQuienes.setOpaque(true);
		btnQuienes.setForeground(Color.ORANGE.darker());
		btnQuienes.setBackground(Color.WHITE);
		btnQuienes.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.ORANGE.darker(), 2), BorderFactory.createEmptyBorder(7, 14, 7, 14)));
		btnQuienes.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnQuienes.setContentAreaFilled(false);
		btnQuienes.addActionListener(e -> new PantallaQuienesSomos(this).setVisible(true));
		
		// logo y título de la aplicación (se hace lo mismo que con la foto de perfil)
		Image logoEsc = logoSinEscalar.getImage().getScaledInstance(60, 52, Image.SCALE_SMOOTH);
		JLabel lblTitulo = new JLabel("Planeta Animal", new ImageIcon(logoEsc), JLabel.LEFT);
		lblTitulo.setIconTextGap(12);
		JPanel panelCuenta = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 9));
		panelCuenta.setBackground(Color.WHITE);
		panelCuenta.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelCuenta.add(btnQuienes);
		panelCuenta.add(btnPerfil);
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		panelNorte.add(panelCuenta, BorderLayout.EAST);
		panelNorte.add(lblTitulo, BorderLayout.WEST);
		panelNorte.add(panelIzq, BorderLayout.CENTER);
		
		
		
		
		
	
		
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
		panelCentro = new JPanel(new GridLayout(0, 3, 15, 15));
		panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panelCentro.setBackground(Color.WHITE);
		contenedor = new JPanel(new BorderLayout());
		contenedor.setBackground(Color.WHITE);
		contenedor.add(panelCentro, BorderLayout.NORTH);
		JScrollPane scrollCentro = new JScrollPane(contenedor);	
		scrollCentro.setBorder(BorderFactory.createEmptyBorder());
		add(panelNorte, BorderLayout.NORTH);
		add(panelFiltros, BorderLayout.WEST);
		add(scrollCentro, BorderLayout.CENTER);
		
		// eventos del buscador y filtros
		btnBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarAnimales();
				
			}
		});
		
		btnLimpiar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txtBuscar.setText("");
				chkDisponibles.setSelected(false);
				for (JCheckBox chk : checksEspecie.values()) {
					chk.setSelected(false);
					
				}
				mostrarAnimales();
			}
			
		});
		// para filtrar al momento sin darle al boton de buscar
		for (JCheckBox chk : checksEspecie.values()) {
			chk.addActionListener(e -> mostrarAnimales());
		}
		chkDisponibles.addActionListener(e -> mostrarAnimales());
		txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {
				mostrarAnimales();
			}
		});
		
		animales = PantallaAdministracion.cargarAnimalesPrueba();
		mostrarAnimales();
	}
	
	// abrir el panel de administracion:
	private void abrirCuenta() {
		JTextField txtUsuario = new JTextField(15);
		javax.swing.JPasswordField txtClave = new javax.swing.JPasswordField(15);
		JPanel formulario = new JPanel(new GridLayout(0, 1, 5, 5));
		formulario.add(new JLabel("Usuario:"));
		formulario.add(txtUsuario);
		formulario.add(new JLabel("Contraseña:"));
		formulario.add(txtClave);
		
		int opcion = JOptionPane.showConfirmDialog(this,
				formulario,
				"Iniciar Sesion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (opcion != JOptionPane.OK_OPTION) {
			return;
		} 
		String nombre = txtUsuario.getText().trim();
		String clave = new String(txtClave.getPassword());

		if (nombre.equals("usuario") && clave.equals("1234")) {        // PARA ACCEDER AL PANEL USUARIO NOMBRE = usuario, CONTRASEÑA = 1234
			Usuario usuario = new Usuario("00000000T", "usuario", "", "usuario@refugio", "Adolf");
			new PantallaUsuario(usuario).setVisible(true);
		} else if (nombre.equals("admin") && clave.equals("1234")) {	 // PARA ACCEDER AL PANEL USUARIO NOMBRE = admin, CONTRASEÑA = 1234
			Administrador admin = new Administrador("00000000T", "admin", "", "admin@refugio.es", "000000000");
			new PantallaAdministracion(admin).setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void abrirDetalle(Animal animal) {
		PantallaDetalleAnimal detalle = new PantallaDetalleAnimal(animal, this);
		detalle.setVisible(true);
	}
	
	// metodo para mostrar los animales
	private void mostrarAnimales() {
		panelCentro.removeAll();

		String texto = txtBuscar.getText().trim().toLowerCase();
		boolean hayEspecie = false;
		for (JCheckBox chk : checksEspecie.values()) {
			if (chk.isSelected()) {
				hayEspecie = true;
			}}
		
		for(Animal a : animales) {
			if (!a.getNombre().toLowerCase().contains(texto)) {
				continue;
			}
			if (hayEspecie && !checksEspecie.get(a.getEspecie()).isSelected()) {
				continue;
			}
			if (chkDisponibles.isSelected() && a.getEstado() != EstadoAnimal.DISPONIBLE) {
				continue;
			}
			JPanel tarjeta = new JPanel(new BorderLayout(0, 6));
			tarjeta.setPreferredSize(new Dimension(200, 235));
			tarjeta.setOpaque(true);
			tarjeta.setBackground(Color.WHITE);
			tarjeta.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
			tarjeta.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					tarjeta.setBackground(new Color(255, 244, 225));
					tarjeta.setBorder(BorderFactory.createLineBorder(Color.ORANGE.darker(), 2));
				}
				public void mouseExited(MouseEvent e) {
					tarjeta.setBackground(Color.WHITE);
					tarjeta.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
				}
				public void mouseClicked(MouseEvent e) {
					abrirDetalle(a);
				}
			});
			// Hueco central para la imagen
			JLabel lblImagen = new JLabel();
			lblImagen.setHorizontalAlignment(JLabel.CENTER);
			
			String rutaImagen = "resources/images/" + a.getId() + ".png"; 
			java.io.File archivoImagen = new java.io.File(rutaImagen);// Ayuda de la ia

			if (archivoImagen.exists()) {
			    // Si la imagen existe, la cargamos y la escalamos a 220x170 píxeles
			    ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
			    Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(220, 170, Image.SCALE_SMOOTH); // Ayuda de la ia
			    lblImagen.setIcon(new ImageIcon(imagenEscalada));
			} else {
			    // Si no hay foto en la carpeta, mostramos un texto por defecto
			    lblImagen.setText("Sin foto");
			    lblImagen.setForeground(Color.LIGHT_GRAY);
			}

			tarjeta.add(lblImagen, BorderLayout.CENTER);
			

			// Datos inferiores
			JLabel lblNombre = new JLabel(a.getNombre());
			lblNombre.setFont(new Font("SansSerif", Font.BOLD, 15));

			JLabel lblRaza = new JLabel(a.getRaza() + " - " + a.getEspecie());
			lblRaza.setFont(new Font("SansSerif", Font.PLAIN, 12));
			lblRaza.setForeground(Color.DARK_GRAY);
			
			// Preparar texto y color del estado
						String textoEstado = a.getEstado().toString().replace("_", " ").toLowerCase();
						textoEstado = textoEstado.substring(0, 1).toUpperCase() + textoEstado.substring(1);

						Color colorEstado = new Color(180, 110, 0); // colores sugeridos por la IA
						if (a.getEstado() == EstadoAnimal.DISPONIBLE) {
							colorEstado = new Color(45, 106, 79);
						} else if (a.getEstado() == EstadoAnimal.ADOPTADO) {
							colorEstado = new Color(170, 50, 50);
						}
						
			JLabel lblEstado = new JLabel("● "+ textoEstado);
			lblEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblEstado.setForeground(colorEstado);

			JPanel pDatos = new JPanel(new GridLayout(3, 1));
			pDatos.setOpaque(false);
			pDatos.add(lblNombre);
			pDatos.add(lblRaza);
			pDatos.add(lblEstado);
			tarjeta.add(pDatos, BorderLayout.SOUTH);

			panelCentro.add(tarjeta);
		}
		// si no queda ningún animal, avisamos
				if (panelCentro.getComponentCount() == 0) {
					panelCentro.add(new JLabel("No hay animales que cumplan la búsqueda"));
				}
		// refrescar el panel: 
		panelCentro.revalidate();
		panelCentro.repaint();
	}
	
	public static void main(String[] args) {
		PantallaCatalogo v = new PantallaCatalogo();
		v.setVisible(true);
	}

}
