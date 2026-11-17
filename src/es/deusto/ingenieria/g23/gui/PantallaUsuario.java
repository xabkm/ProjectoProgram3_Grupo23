
package es.deusto.ingenieria.g23.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import es.deusto.ingenieria.g23.domain.Animal;
import es.deusto.ingenieria.g23.domain.CitaAgenda;
import es.deusto.ingenieria.g23.domain.Especie;
import es.deusto.ingenieria.g23.domain.EstadoAnimal;
import es.deusto.ingenieria.g23.domain.EstadoCita;
import es.deusto.ingenieria.g23.domain.EstadoSolicitud;
import es.deusto.ingenieria.g23.domain.Persona;
import es.deusto.ingenieria.g23.domain.SolicitudAdopcion;
import es.deusto.ingenieria.g23.domain.TipoCita;
import es.deusto.ingenieria.g23.domain.Usuario;
public class PantallaUsuario extends JFrame {
	private static final long serialVersionUID = 1L;
	private Persona usuarioActual;
	
	private List<Animal> listaAnimales;
	private AnimalTableModel modeloAnimales;
	private JTable tablaAnimales;
	
	private List<SolicitudAdopcion> misSolicitudes;
	private SolicitudTableModel modeloSolicitudes;
	private JTable tablaSolicitudes;
	
	private List<CitaAgenda> misCitas;
	private CitaTableModel modeloCitas;
	private JTable tablaCitas;
	
	public PantallaUsuario(Persona usuario) {
		this.usuarioActual = usuario;
		
		
		this.listaAnimales = cargarAnimalesPrueba();
		this.misSolicitudes = cargarMisSolicitudesPrueba();
		this.misCitas = cargarMisCitasPrueba();
		setTitle("Panel de Usuario - " + usuarioActual.getNombre());
		setSize(950, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));
		
		JPanel panelNorte = new JPanel(new BorderLayout());
		panelNorte.setBackground(new Color(45, 106, 79));
		panelNorte.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		JLabel lblTitulo = new JLabel("BIENVENIDO/A, " + usuarioActual.getNombre().toUpperCase(), JLabel.CENTER); 
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setForeground(Color.WHITE);
		panelNorte.add(lblTitulo, BorderLayout.CENTER);
		
		JTabbedPane pestanas = new JTabbedPane(); // crear las pestañas para poder cambiar de unas a otras
		pestanas.addTab("Animales en Adopción", crearPanelAnimales());
		pestanas.addTab("Mis Solicitudes", crearPanelMisSolicitudes());
		pestanas.addTab("Mis Citas", crearPanelMisCitas());
		JPanel panelCentro = new JPanel(new BorderLayout());
		panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		panelCentro.add(pestanas, BorderLayout.CENTER);
		add(panelNorte, BorderLayout.NORTH);
		add(panelCentro, BorderLayout.CENTER);
	}
	
	
	private JPanel crearPanelAnimales() {
		JPanel panelanimales = new JPanel(new BorderLayout(5, 10));
		panelanimales.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		modeloAnimales = new AnimalTableModel(listaAnimales);
		tablaAnimales = new JTable(modeloAnimales);
		tablaAnimales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaAnimales.setRowHeight(24);
		
		tablaAnimales.getColumnModel().getColumn(0).setPreferredWidth(55); 
		tablaAnimales.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() { // cambiar los colores dependiendo del estado 
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if(isSelected) {
					c.setBackground(table.getBackground());
					c.setForeground(table.getForeground());
					// IAG Gemini
				} else if (value instanceof EstadoAnimal) {
					c.setForeground(Color.black);
					EstadoAnimal estado =(EstadoAnimal) value;
					if(estado == EstadoAnimal.DISPONIBLE) {
						c.setBackground(new Color(215, 245, 220)); //verde
					} else if (estado == EstadoAnimal.ADOPTADO) {
						c.setBackground(new Color(255, 215, 215)); // rojo
					} else {
						c.setBackground(new Color(255, 248, 200));//Amarillo
					}
				}
				return c;
			}
		});
		JScrollPane scroll = new JScrollPane(tablaAnimales);
		JPanel panelTabla = new JPanel(new BorderLayout());
		panelTabla.setBorder(BorderFactory.createTitledBorder("Nuestros animales"));
		panelTabla.add(scroll, BorderLayout.CENTER);
		panelanimales.add(panelTabla, BorderLayout.CENTER);
		JPanel panelBotones = new JPanel(new FlowLayout());
		JButton btnAdoptar = new JButton("Solicitar Adopción");
		btnAdoptar.setFont(new Font("Arial", Font.BOLD, 14));
		btnAdoptar.setBackground(Color.black);
		btnAdoptar.setForeground(Color.WHITE);
		panelBotones.add(btnAdoptar);
		panelanimales.add(panelBotones, BorderLayout.SOUTH);
		
		btnAdoptar.addActionListener(e -> solicitarAdopcion());

		
		return panelanimales;
	}
	
	private void solicitarAdopcion() {
		int fila = tablaAnimales.getSelectedRow();
		if (fila == -1) { // si no se selecciona animal se avisa que hay que seleccionar uno
			JOptionPane.showMessageDialog(this, "Selecciona un animal de la lista para adoptarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		Animal seleccionado = listaAnimales.get(fila);
		if (seleccionado.getEstado() != EstadoAnimal.DISPONIBLE) { // si en el momento el animal no esta disponible (aunque no sea que este directamente adoptado) se le avisa
			JOptionPane.showMessageDialog(this, "Lo sentimos, " + seleccionado.getNombre() + " no está disponible actualmente.", "No disponible", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int pregunta = JOptionPane.showConfirmDialog(this, // pedimos confirmación por si acaso
				"¿Deseas enviar una solicitud de adopción para " + seleccionado.getNombre() + "?",
				"Confirmar solicitud", JOptionPane.YES_NO_OPTION);
		if (pregunta == JOptionPane.YES_OPTION) {
			
			SolicitudAdopcion nuevaSolicitud = new SolicitudAdopcion( // creamos la solicitud
					"S" + (misSolicitudes.size() + 1),
					seleccionado,
					(Usuario)usuarioActual, //nos aseguramos que es un usuario y no un administrador
					LocalDateTime.now(),
					EstadoSolicitud.PENDIENTE
			);
			misSolicitudes.add(nuevaSolicitud);
			modeloSolicitudes.fireTableDataChanged(); // actualizamos la información
			JOptionPane.showMessageDialog(this, "Solicitud enviada. Se revisará pronto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private JPanel crearPanelMisSolicitudes() {
		
		JPanel panelsolicitudes = new JPanel(new BorderLayout(5, 10));
		panelsolicitudes.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		modeloSolicitudes = new SolicitudTableModel(misSolicitudes);
		tablaSolicitudes = new JTable(modeloSolicitudes);
		tablaSolicitudes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaSolicitudes.setRowHeight(24);
		
		tablaSolicitudes.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
		private static final long serialVersionUID = 1L;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (isSelected) {
				c.setBackground(table.getSelectionBackground());
				c.setForeground(table.getSelectionForeground());
			} else if (value instanceof EstadoSolicitud) {
				c.setForeground(Color.BLACK);
				EstadoSolicitud solicitud = (EstadoSolicitud) value;
				
				if (solicitud == EstadoSolicitud.APROBADA) {
					c.setBackground(new Color(215, 245, 220)); // Verde
				} else if (solicitud == EstadoSolicitud.RECHAZADA) {
					c.setBackground(new Color(255, 215, 215)); // Rojo
				} else {
					c.setBackground(new Color(255, 248, 200)); // Amarillo
				}
			}
			return c;
			}
			});
		
		JPanel panelBotones = new JPanel(new FlowLayout());
		JButton btnCancelar = new JButton("Retirar Solicitud");
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
		btnCancelar.setBackground(Color.black);
		btnCancelar.setForeground(Color.WHITE);
		panelBotones.add(btnCancelar);
		panelsolicitudes.add(panelBotones, BorderLayout.SOUTH);
		btnCancelar.addActionListener(e -> {
			int fila = tablaSolicitudes.getSelectedRow();
			
			if (fila != -1) {
				int conf = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres cancelar tu solicitud?", "Confirmar", JOptionPane.YES_NO_OPTION);
				if (conf == JOptionPane.YES_OPTION) {
					misSolicitudes.remove(fila);
					modeloSolicitudes.fireTableDataChanged();
				} 
			}else {
				JOptionPane.showMessageDialog(this, "Selecciona una solicitud para retirarla","Aviso", JOptionPane.WARNING_MESSAGE);
				
			}
		});

		
		JScrollPane scroll = new JScrollPane(tablaSolicitudes);
		JPanel panelTablaSol = new JPanel(new BorderLayout());
		panelTablaSol.setBorder(BorderFactory.createTitledBorder("Tus Solicitudes"));
		panelTablaSol.add(scroll, BorderLayout.CENTER);
		panelsolicitudes.add(panelTablaSol, BorderLayout.CENTER);
		return panelsolicitudes;
	}
	
	private JPanel crearPanelMisCitas() {
		JPanel panelcitas = new JPanel(new BorderLayout());
		panelcitas.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		
		modeloCitas = new CitaTableModel(misCitas);
		tablaCitas = new JTable(modeloCitas);
		tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaCitas.setRowHeight(24);
		
		tablaCitas.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (isSelected) {
					c.setBackground(table.getSelectionBackground());
					c.setForeground(table.getSelectionForeground());
				} else if (value instanceof TipoCita) {
					c.setForeground(Color.BLACK);
					TipoCita tipo = (TipoCita) value;
					if (tipo == TipoCita.VETERINARIO) {
						c.setBackground(new Color(220, 235, 255)); // Azul claro
					} else if (tipo == TipoCita.ENTREVISTA_ADOPCION) {
						c.setBackground(new Color(255, 235, 210)); // Naranja claro
					} else if (tipo == TipoCita.PASEO) {
						c.setBackground(new Color(215, 245, 220)); // Verde claro
					} else if (tipo == TipoCita.VISITA_PADRINO) {
						c.setBackground(new Color(240, 220, 255)); // Morado claro
					} else {
						c.setBackground(Color.WHITE); // otro 
					}
				}
				return c;
			}
			
		});
		
		tablaCitas.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (isSelected) {
					c.setBackground(table.getSelectionBackground());
					c.setForeground(table.getSelectionForeground());
				} else if (value instanceof EstadoCita) {
					c.setForeground(Color.BLACK);
					EstadoCita estado = (EstadoCita) value;
					if (estado == EstadoCita.ATENDIDA) {
						c.setBackground(new Color(215, 245, 220)); // Verde claro
					} else if (estado == EstadoCita.CANCELADA) {
						c.setBackground(new Color(255, 215, 215)); // Rojo claro
					} else {
						c.setBackground(new Color(255, 248, 200)); // Amarillo claro 
					}
				}
				return c;
			}
		});

		JPanel panelbotones =new JPanel(new FlowLayout());
		JButton btnCit = new JButton("Detalles de la Cita");
		btnCit.setFont(new Font("Arial", Font.BOLD, 14));
		btnCit.setBackground(Color.black);
		btnCit.setForeground(Color.WHITE);
		panelbotones.add(btnCit);
		
		JButton botonCancel = new JButton("Cancelar Cita");
		botonCancel.setFont(new Font("Arial", Font.BOLD, 14));
		botonCancel.setBackground(Color.black);
		botonCancel.setForeground(Color.WHITE);
		panelbotones.add(botonCancel);
		
		panelcitas.add(panelbotones,BorderLayout.SOUTH); 
		botonCancel.addActionListener(e-> cancelarCita());
		btnCit.addActionListener(e ->{
			int fila = tablaCitas.getSelectedRow();
			CitaAgenda selec = misCitas.get(fila);
			if(fila != -1) {
				JOptionPane.showMessageDialog(this, "Acude a tu cita en la sede de la asociación 10 minutos antes del "+selec.getFechaHora()+" para la "+selec.getTipoCita() +".\nNo olvides traer tu DNI.", "Información", JOptionPane.INFORMATION_MESSAGE);
			} 

		});
		
		JScrollPane scroll = new JScrollPane(tablaCitas);
		JPanel panelTablaCit = new JPanel(new BorderLayout());
		panelTablaCit.setBorder(BorderFactory.createTitledBorder("Tus Citas"));
		panelTablaCit.add(scroll, BorderLayout.CENTER);
		panelcitas.add(panelTablaCit, BorderLayout.CENTER);
		
		
		return panelcitas;
	}
	
	
	private void cancelarCita() {
		int fila = tablaCitas.getSelectedRow();
		if (fila == -1) { // si no se selecciona animal se avisa que hay que seleccionar uno
			JOptionPane.showMessageDialog(this, "Selecciona una cita de la lista para cancelarla.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		CitaAgenda seleccionado = misCitas.get(fila);
		
		int pregunta = JOptionPane.showConfirmDialog(this, // pedimos confirmación por si acaso
				"¿Deseas cancelar la cita del día"+seleccionado.getFechaHora() +" para " + seleccionado.getTipoCita() + "?",
				"Confirmar solicitud", JOptionPane.YES_NO_OPTION);
		if (pregunta == JOptionPane.YES_OPTION) {
			seleccionado.setEstado(EstadoCita.CANCELADA);
		
			
			modeloCitas.fireTableDataChanged(); // actualizamos la información
			JOptionPane.showMessageDialog(this, "Cita cancelada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private List<Animal> cargarAnimalesPrueba() {
		List<Animal> animales = new ArrayList<>();
		animales.add(new Animal("A1", "Bizkor", Especie.PERRO, "Golden", LocalDate.of(2022, 5, 10), EstadoAnimal.DISPONIBLE));
		animales.add(new Animal("A2", "Pilot", Especie.GATO, "Persa", LocalDate.of(2023, 8, 20), EstadoAnimal.EN_ACOGIDA));
		animales.add(new Animal("A3", "Zuri", Especie.PERRO, "Pastor", LocalDate.of(2021, 2, 15), EstadoAnimal.DISPONIBLE));
		return animales;
	}
	
	private List<SolicitudAdopcion> cargarMisSolicitudesPrueba() {
		List<SolicitudAdopcion> solicitudes = new ArrayList<>();
		if(usuarioActual instanceof Usuario) {
			solicitudes.add(new SolicitudAdopcion("S1", listaAnimales.get(1), (Usuario)usuarioActual, LocalDateTime.now().minusDays(2), EstadoSolicitud.PENDIENTE));
		}
		return solicitudes;
	}
	private List<CitaAgenda> cargarMisCitasPrueba() {
		List<CitaAgenda> citas = new ArrayList<>();
		citas.add(new CitaAgenda("C1", LocalDateTime.of(2026,12,26,14,20), TipoCita.ENTREVISTA_ADOPCION, listaAnimales.get(1), usuarioActual, EstadoCita.PENDIENTE));
		return citas;
	}
	public static void main(String[] args) {
		Usuario usuarioPrueba = new Usuario(); // más que nada para poder probar la pantalla usuario sin tener que abrir todo el rato el main
		usuarioPrueba.setNombre("Guille"); 
		PantallaUsuario ventana = new PantallaUsuario(usuarioPrueba);
		ventana.setVisible(true);
	}
	
}
