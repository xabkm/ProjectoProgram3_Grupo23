package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import es.deusto.ingenieria.g23.domain.Administrador;
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



public class PantallaAdministracion extends JFrame {

	private static final long serialVersionUID = 1L;
	private Persona adminActual;
	private List<Animal> listaAnimales;
	private AnimalTableModel modeloAnimales;
	private JTable tablaAnimales;
	private List<SolicitudAdopcion> listaSolicitudes;
	private SolicitudTableModel modeloSolicitudes;
	private JTable tablaSolicitudes;
	private List<CitaAgenda> listaCitas;
	private CitaTableModel modeloCitas;
	private JTable tablaCitas;
	
	public PantallaAdministracion(Persona usuario) {
		
		if (!(usuario instanceof Administrador)) {
			JOptionPane.showMessageDialog(null, 
				"Acceso denegado. Esta vista es exclusiva para administradores.", 
				"Error de permisos", JOptionPane.ERROR_MESSAGE);
			dispose();
			return;
		}

		this.adminActual = usuario;
		this.listaAnimales = cargarAnimalesPrueba();
		this.listaSolicitudes = cargarSolicitudesPrueba();
		this.listaCitas = cargarCitasPrueba();

		setTitle("Panel de Administración - Usuario: " + adminActual.getNombre());
		setSize(950, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		JPanel panelNorte = new JPanel(new BorderLayout());
		panelNorte.setBackground(new Color(45, 106, 79));
		panelNorte.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		JLabel lblTitulo = new JLabel("PANEL DE ADMINISTRACION", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setForeground(Color.WHITE);
		panelNorte.add(lblTitulo, BorderLayout.CENTER);

		JTabbedPane pestanas = new JTabbedPane();
		pestanas.addTab("Gestión de Animales", crearPanelAnimales());
		pestanas.addTab("Solicitudes de Adopción", crearPanelAdopciones());
		pestanas.addTab("Agenda de Citas", crearPanelAgenda());

		JPanel panelCentro = new JPanel(new BorderLayout());
		panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		panelCentro.add(pestanas, BorderLayout.CENTER);

		add(panelNorte, BorderLayout.NORTH);
		add(panelCentro, BorderLayout.CENTER);
	}
	
	
	private JPanel crearPanelAnimales() {
	    JPanel panel = new JPanel(new BorderLayout(5, 10));
	    panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

	    modeloAnimales = new AnimalTableModel(listaAnimales);
	    tablaAnimales = new JTable(modeloAnimales);
	    
	    
	    
	 // Solo dejamos seleccionar una fila a la vez y ajustamos un poco el aspecto
	 		tablaAnimales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 		tablaAnimales.setRowHeight(24);
	 		tablaAnimales.getColumnModel().getColumn(0).setPreferredWidth(55);

	 		// Renderizado personalizado para la columna de Estado 
	 		tablaAnimales.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					if (isSelected) {
						c.setBackground(table.getSelectionBackground());
						c.setForeground(table.getSelectionForeground());
					} else if (value instanceof EstadoAnimal) {
						c.setForeground(Color.BLACK);
						EstadoAnimal estado = (EstadoAnimal) value;
						// IAG Gemini
						// creado los colores
						if (estado == EstadoAnimal.DISPONIBLE) {
							c.setBackground(new Color(215, 245, 220)); // verde claro
						} else if (estado == EstadoAnimal.ADOPTADO) {
							c.setBackground(new Color(255, 215, 215)); // Rojo claro
						} else {
							c.setBackground(new Color(255, 248, 200)); // Amarillo claro 
						}
					}
					return c;
				}
			});

	 		// Doble clic en una fila para abrir directamente la ventana de editar
	 		tablaAnimales.addMouseListener(new MouseAdapter() {
	 			@Override
	 			public void mouseClicked(MouseEvent e) {
	 				if (e.getClickCount() == 2 ) {
	 					editarAnimal();
	 				}
	 			}
	 		});

	 		// Tecla supr para borrar el animal seleccionado
	 		tablaAnimales.addKeyListener(new KeyAdapter() {
	 			@Override
	 			public void keyPressed(KeyEvent e) {
	 				if (e.getKeyCode() == KeyEvent.VK_DELETE ) {
	 					eliminarAnimal();
	 				}
	 			}
	 		});
	    
	    
	    
	    JScrollPane scrollTabla = new JScrollPane(tablaAnimales);
	    JPanel panelTabla = new JPanel(new BorderLayout());
	    panelTabla.setBorder(BorderFactory.createTitledBorder("Animales"));
	    panelTabla.add(scrollTabla, BorderLayout.CENTER);
	    panel.add(panelTabla, BorderLayout.CENTER);

	    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    JButton btnNuevo = new JButton("Nuevo Animal");
	    JButton btnEditar = new JButton("Editar");
	    JButton btnEliminar = new JButton("Eliminar");
	    btnNuevo.setFont(new Font("Arial", Font.BOLD, 14));

	    panelBotones.add(btnNuevo);
	    panelBotones.add(btnEditar);
	    panelBotones.add(btnEliminar);
	    panel.add(panelBotones, BorderLayout.SOUTH);
	 // Conectamos los eventos de los botones del CRUD
	 		btnNuevo.addActionListener(e -> nuevoAnimal());
	 		btnEditar.addActionListener(e -> editarAnimal());
	 		btnEliminar.addActionListener(e -> eliminarAnimal());
	    return panel;
	}
	//  formulario con JOptionPane para dar de alta un nuevo animal
		private void nuevoAnimal() {
			JTextField txtId = new JTextField();
			JTextField txtNombre = new JTextField();
			JComboBox<Especie> comboEspecie = new JComboBox<>(Especie.values());
			JTextField txtRaza = new JTextField();
			JComboBox<EstadoAnimal> comboEstado = new JComboBox<>(EstadoAnimal.values());

			JComponent[] campos = new JComponent[] {
				new JLabel("ID del Animal:"), txtId,
				new JLabel("Nombre:"), txtNombre,
				new JLabel("Especie:"), comboEspecie,
				new JLabel("Raza:"), txtRaza,
				new JLabel("Estado:"), comboEstado
			};

			int opcion = JOptionPane.showConfirmDialog(this, campos, "Nuevo Animal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (opcion == JOptionPane.OK_OPTION) {
				if (txtId.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(this, "El ID y el Nombre son obligatorios", "Datos incompletos", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				Especie especieSeleccionada = (Especie) comboEspecie.getSelectedItem();
				EstadoAnimal estadoSeleccionado = (EstadoAnimal) comboEstado.getSelectedItem();
				Animal nuevo = new Animal(
					txtId.getText().trim(),
					txtNombre.getText().trim(),
					especieSeleccionada,
					txtRaza.getText().trim(),
					LocalDate.now(),
					estadoSeleccionado
				);

				listaAnimales.add(nuevo);
				modeloAnimales.fireTableDataChanged();
			}
		}

		//  modificar los datos del animal seleccionado en la tabla
		private void editarAnimal() {
			int fila = tablaAnimales.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Selecciona primero un animal de la tabla para editarlo", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			Animal seleccionado = listaAnimales.get(fila);

			JTextField txtNombre = new JTextField(seleccionado.getNombre());
			JComboBox<Especie> comboEspecie = new JComboBox<>(Especie.values());
			JTextField txtRaza = new JTextField(seleccionado.getRaza());
			JComboBox<EstadoAnimal> comboEstado = new JComboBox<>(EstadoAnimal.values());
			comboEstado.setSelectedItem(seleccionado.getEstado());

			JComponent[] campos = new JComponent[] {
				new JLabel("Nombre:"), txtNombre,
				new JLabel("Especie:"), comboEspecie,
				new JLabel("Raza:"), txtRaza,
				new JLabel("Estado:"), comboEstado
			};

			int opcion = JOptionPane.showConfirmDialog(this, campos, "Editar Animal (" + seleccionado.getId() + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (opcion == JOptionPane.OK_OPTION) {
				if (txtNombre.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(this, "El nombre no puede quedar vacío", "Error de validación", JOptionPane.ERROR_MESSAGE);
					return;
				}

				seleccionado.setNombre(txtNombre.getText().trim());
				seleccionado.setEspecie((Especie) comboEspecie.getSelectedItem());
				seleccionado.setRaza(txtRaza.getText().trim());
				seleccionado.setEstado((EstadoAnimal) comboEstado.getSelectedItem());

				modeloAnimales.fireTableDataChanged();
			}
		}

		// Elimina el animal seleccionado con previa confirmación
		private void eliminarAnimal() {
			int fila = tablaAnimales.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Selecciona primero el animal que quieres eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			Animal seleccionado = listaAnimales.get(fila);
			int confirmacion = JOptionPane.showConfirmDialog(
				this,
				"¿Seguro que quieres eliminar a " + seleccionado.getNombre() + " (" + seleccionado.getId() + ")?",
				"Confirmar borrado",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);

			if (confirmacion == JOptionPane.YES_OPTION) {
				listaAnimales.remove(fila);
				modeloAnimales.fireTableDataChanged();
			}
		}
		
		
		
		
		
		private JPanel crearPanelAdopciones() {
			JPanel panel = new JPanel(new BorderLayout(5, 10));
			panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

			modeloSolicitudes = new SolicitudTableModel(listaSolicitudes);
			tablaSolicitudes = new JTable(modeloSolicitudes);

			tablaSolicitudes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaSolicitudes.setRowHeight(24);
			tablaSolicitudes.getColumnModel().getColumn(0).setPreferredWidth(60);

			// renderizado para la columna de estados
			tablaSolicitudes.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					if (isSelected) {
						c.setBackground(table.getSelectionBackground());
						c.setForeground(table.getSelectionForeground());
					} else if (value instanceof EstadoSolicitud) {
						c.setForeground(Color.BLACK);
						String estadoTexto = value.toString().toUpperCase();
						if (estadoTexto.contains("APROB") || estadoTexto.contains("ACEPT")) {
							c.setBackground(new Color(215, 245, 220)); // Verde claro
						} else if (estadoTexto.contains("RECHAZ") || estadoTexto.contains("DENEG")) {
							c.setBackground(new Color(255, 215, 215)); // Rojo claro
						} else {
							c.setBackground(new Color(255, 248, 200)); // Amarillo claro 
						}
					}
					return c;
				}
			});

			// Doble clic en una fila para revisar y cambiar el estado de la solicitud
			tablaSolicitudes.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						gestionarSolicitud();
					}
				}
			});

			JScrollPane scrollTabla = new JScrollPane(tablaSolicitudes);
			JPanel panelTabla = new JPanel(new BorderLayout());
			panelTabla.setBorder(BorderFactory.createTitledBorder("Solicitudes"));
			panelTabla.add(scrollTabla, BorderLayout.CENTER);
			panel.add(panelTabla, BorderLayout.CENTER);

			JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnGestionar = new JButton("Gestionar Estado");
			JButton btnEliminarSol = new JButton("Eliminar");
			btnGestionar.setFont(new Font("Arial", Font.BOLD, 14));

			panelBotones.add(btnGestionar);
			panelBotones.add(btnEliminarSol);
			panel.add(panelBotones, BorderLayout.SOUTH);

			btnGestionar.addActionListener(e -> gestionarSolicitud());
			btnEliminarSol.addActionListener(e -> eliminarSolicitud());

			return panel;
		}

		// Abre un diálogo para aprobar, rechazar o cambiar el estado de la solicitud seleccionada
		private void gestionarSolicitud() {
			int fila = tablaSolicitudes.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Selecciona primero una solicitud de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			SolicitudAdopcion seleccionada = listaSolicitudes.get(fila);
			String nombreAnimal = (seleccionada.getAnimal() != null) ? seleccionada.getAnimal().getNombre() : "Sin asignar";
			String nombreSolicitante = (seleccionada.getSolicitante() != null) ? seleccionada.getSolicitante().getNombre() : "Sin asignar";
			String fechaFormateada = (seleccionada.getFechaSolicitud() != null) 
					? seleccionada.getFechaSolicitud().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) 
					: "Sin fecha";

			JComboBox<EstadoSolicitud> comboEstado = new JComboBox<>(EstadoSolicitud.values());
			comboEstado.setSelectedItem(seleccionada.getEstado());

			JComponent[] campos = new JComponent[] {
				new JLabel("Solicitud: " + seleccionada.getId()),
				new JLabel("Solicitante: " + nombreSolicitante),
				new JLabel("Animal solicitado: " + nombreAnimal),
				new JLabel("Fecha de registro: " + fechaFormateada),
				new JLabel("Nuevo estado:"), comboEstado
			};

			int opcion = JOptionPane.showConfirmDialog(this, campos, "Gestionar Solicitud (" + seleccionada.getId() + ")",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (opcion == JOptionPane.OK_OPTION) {
				seleccionada.setEstado((EstadoSolicitud) comboEstado.getSelectedItem());
				modeloSolicitudes.fireTableDataChanged();
			}
		}

		// Elimina la solicitud seleccionada tras pedir confirmación
		private void eliminarSolicitud() {
			int fila = tablaSolicitudes.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Selecciona primero la solicitud que quieres eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			SolicitudAdopcion seleccionada = listaSolicitudes.get(fila);
			int confirmacion = JOptionPane.showConfirmDialog(
				this,
				"¿Seguro que quieres eliminar la solicitud " + seleccionada.getId() + "?",
				"Confirmar borrado",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);

			if (confirmacion == JOptionPane.YES_OPTION) {
				listaSolicitudes.remove(fila);
				modeloSolicitudes.fireTableDataChanged();
			}
		}
		
		
		
		
		

		// pestaña de citas
	private JPanel crearPanelAgenda() {
	    JPanel panel = new JPanel(new BorderLayout());
	    modeloCitas = new CitaTableModel(listaCitas);
		tablaCitas = new JTable(modeloCitas);

		tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaCitas.setRowHeight(24);
		tablaCitas.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
		tablaCitas.getColumnModel().getColumn(1).setPreferredWidth(120); // Fecha
		tablaCitas.getColumnModel().getColumn(2).setPreferredWidth(140); // Tipo

		// Renderizado para resaltar el Tipo de Cita (columna 2)
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
					
					// Usamos las constantes exactas del equipo
					if (tipo == TipoCita.VETERINARIO) {
						c.setBackground(new Color(220, 235, 255)); // Azul claro
					} else if (tipo == TipoCita.ENTREVISTA_ADOPCION) {
						c.setBackground(new Color(255, 235, 210)); // Naranja claro
					} else if (tipo == TipoCita.PASEO) {
						c.setBackground(new Color(215, 245, 220)); // Verde claro
					} else if (tipo == TipoCita.VISITA_PADRINO) {
						c.setBackground(new Color(240, 220, 255)); // Morado claro
					} else {
						c.setBackground(Color.WHITE); // OTRO
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

		JScrollPane scrollTabla = new JScrollPane(tablaCitas);
		panel.add(scrollTabla, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new FlowLayout());
		JButton btnAtender = new JButton("Atender Cita");
		JButton btnCancelar = new JButton("Cancelar Cita");

		panelBotones.add(btnAtender);
		panelBotones.add(btnCancelar);
		panel.add(panelBotones, BorderLayout.SOUTH);

		btnAtender.addActionListener(e -> {
			int fila = tablaCitas.getSelectedRow();
			if (fila != -1) {
				CitaAgenda cita = listaCitas.get(fila);
				cita.setEstado(EstadoCita.ATENDIDA); // Cambiamos el estado
				modeloCitas.fireTableDataChanged();
				JOptionPane.showMessageDialog(this, "Cita " + cita.getId() + " marcada como atendida.", "Agenda", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Selecciona una cita primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		});

		btnCancelar.addActionListener(e -> {
			int fila = tablaCitas.getSelectedRow();
			if (fila != -1) {
				CitaAgenda cita = listaCitas.get(fila);
				int conf = JOptionPane.showConfirmDialog(this, "¿Cancelar la cita " + cita.getId() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
				if (conf == JOptionPane.YES_OPTION) {
					cita.setEstado(EstadoCita.CANCELADA); // cambiamos el estado
					modeloCitas.fireTableDataChanged();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Selecciona una cita primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		});

		return panel;
	}
	
	
	// mocks IAG Gemini
	// mock de animales
	public static List<Animal> cargarAnimalesPrueba() {
		 List<String> fotosPruebaToby = new ArrayList<>();
		 fotosPruebaToby.add("resources/images/A001.png");
		 fotosPruebaToby.add("resources/images/A0012.png");
	     List<String> fotosPruebaLuna = new ArrayList<>();
        fotosPruebaLuna.add("resources/images/A002.png");
        fotosPruebaLuna.add("resources/images/A0022.png");
        fotosPruebaLuna.add("resources/images/A0023.png");
        List<String> fotosPruebaMax = new ArrayList<>();
        fotosPruebaMax.add("resources/images/max1.png");
        fotosPruebaMax.add("resources/images/max2.png");
        List<String> fotosPruebaKivi = new ArrayList<>();
        fotosPruebaKivi.add("resources/images/A004.png");
        fotosPruebaKivi.add("resources/images/A0041.png");
        List<String> fotosPruebaCoco = new ArrayList<>();
        fotosPruebaCoco.add("resources/images/A005.png");
        fotosPruebaCoco.add("resources/images/A0051.png");
        
	        
	    List<Animal> mocks = new ArrayList<>();

	    mocks.add(new Animal("A001", "Toby", Especie.PERRO, "Mestizo", LocalDate.of(2022, 5, 10), EstadoAnimal.DISPONIBLE,"Es un perro miedoso",true,true,fotosPruebaToby));
	    mocks.add(new Animal("A002", "Luna", Especie.GATO, "Siamés", LocalDate.of(2023, 8, 20), EstadoAnimal.EN_ACOGIDA, "Es una gata que trepa muy bien",false, true,fotosPruebaLuna));
	    mocks.add(new Animal("A003", "Max", Especie.PERRO, "Labrador", LocalDate.of(2021, 2, 15), EstadoAnimal.ADOPTADO,"Es un perro jugueton",true,false,fotosPruebaMax));
	    mocks.add(new Animal("A004", "Kivi", Especie.PERRO, "Galgo", LocalDate.of(2016, 3, 15), EstadoAnimal.ADOPTADO,"Le encanta correr pero se cansa rápido",false, false,fotosPruebaKivi ));
	    mocks.add(new Animal("A005", "Coco", Especie.LORO, "Guacamayo", LocalDate.of(2007, 6, 8), EstadoAnimal.DISPONIBLE, "Le gusta cantar Rosalia", false, true, fotosPruebaCoco));
	    
	    return mocks;
	}
 
	
	// mocks de adopcion
	private List<SolicitudAdopcion> cargarSolicitudesPrueba() {
		List<SolicitudAdopcion> mocks = new ArrayList<>();

		Usuario u1 = new Usuario();
		u1.setNombre("Laura Martínez");

		Usuario u2 = new Usuario();
		u2.setNombre("Carlos Gómez");

		mocks.add(new SolicitudAdopcion("S001", listaAnimales.get(0), u1, LocalDateTime.now().minusDays(2), EstadoSolicitud.PENDIENTE));
		mocks.add(new SolicitudAdopcion("S002", listaAnimales.get(1), u2, LocalDateTime.now().minusDays(1), EstadoSolicitud.APROBADA));
		mocks.add(new SolicitudAdopcion("S003", listaAnimales.get(2), u1, LocalDateTime.now().minusHours(5), EstadoSolicitud.RECHAZADA));

		return mocks;
	}
	
	// mocks de citas
	private List<CitaAgenda> cargarCitasPrueba() {
		List<CitaAgenda> mocks = new ArrayList<>();

		mocks.add(new CitaAgenda("C001", LocalDateTime.now().plusDays(1).withHour(10).withMinute(30), TipoCita.ENTREVISTA_ADOPCION, listaAnimales.get(0), adminActual, EstadoCita.PENDIENTE));
		mocks.add(new CitaAgenda("C002", LocalDateTime.now().plusDays(2).withHour(17).withMinute(0), TipoCita.VETERINARIO, listaAnimales.get(1), adminActual, EstadoCita.ATENDIDA));
		mocks.add(new CitaAgenda("C003", LocalDateTime.now().plusDays(3).withHour(12).withMinute(15), TipoCita.PASEO, listaAnimales.get(2), adminActual, EstadoCita.CANCELADA));

		return mocks;
	}
	// metodo para ver la ventana desde aqui
	public static void main(String[] args) {
		Administrador adminPrueba = new Administrador();
		adminPrueba.setNombre("Admin 1");
		PantallaAdministracion ventana = new PantallaAdministracion(adminPrueba);
		ventana.setVisible(true);
	}
}