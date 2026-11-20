package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import javax.swing.JTextArea;
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

	   
	    
	     
	 // Solo dejamos seleccionar una fila a la vez y ajustamos un poco el aspecto
	 		
	 		
	 		modeloAnimales = new AnimalTableModel(listaAnimales) { // IAG Gemini
	 		    private static final long serialVersionUID = 1L;

	 		    // 1. Le decimos a la tabla que hay una columna extra
	 		    @Override
	 		    public int getColumnCount() {
	 		        return super.getColumnCount() + 1; 
	 		    }

	 		    // 2. Le ponemos un título a esa nueva columna (la última)
	 		    @Override
	 		    public String getColumnName(int column) {
	 		        if (column == super.getColumnCount()) {
	 		            return "Eliminar Animal"; // El nombre de tu nueva columna
	 		        }
	 		        return super.getColumnName(column);
	 		    }

	 		    // 3. Le decimos qué dato debe mostrar esa nueva columna
	 		    @Override
	 		    public Object getValueAt(int rowIndex, int columnIndex) {
	 		        if (columnIndex == super.getColumnCount()) {
	 		            return ""; // Aquí puedes devolver un texto, o dejarlo vacío si vas a poner un botón con el CellRenderer
	 		        }
	 		        return super.getValueAt(rowIndex, columnIndex);
	 		    }
	 		};
	 		
	 		tablaAnimales = new JTable(modeloAnimales);
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
	 		
	 		tablaAnimales.getColumnModel().getColumn(5).setHeaderValue("Editar Animal");
	 		tablaAnimales.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() { // aqui se crea el boton pero solo aparece como imagen
				private static final long serialVersionUID = 1L;
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					JButton botonCelda = new JButton("Editar");
					botonCelda.setBackground(Color.BLACK);
					botonCelda.setForeground(Color.WHITE);
					botonCelda.setFont(new Font("Arial", Font.BOLD, 12));
					return botonCelda;
					
					
				}
			});
			tablaAnimales.addMouseListener(new java.awt.event.MouseAdapter() { // le añadimos el listener porque sino seria como si solo fuera una imagen
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					int columnaClic = tablaAnimales.columnAtPoint(e.getPoint());
					int filaClic = tablaAnimales.rowAtPoint(e.getPoint());
					
					
					if (columnaClic == 5 && filaClic != -1) {
						editarAnimal(filaClic); 
					} else if (columnaClic == 6 && filaClic != -1) {
							eliminarAnimal(filaClic); 
						}
				}
			});

			tablaAnimales.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() { // aqui se crea el boton pero solo aparece como imagen
				private static final long serialVersionUID = 1L;
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					JButton botonCelda = new JButton("Eliminar");
					botonCelda.setBackground(Color.RED);
					botonCelda.setForeground(Color.WHITE);
					botonCelda.setFont(new Font("Arial", Font.BOLD, 12));
					return botonCelda;
					
					
				}
			});
			
	   
	    JScrollPane scrollTabla = new JScrollPane(tablaAnimales);
	    panel.add(scrollTabla, BorderLayout.CENTER);

	    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    JButton btnNuevo = new JButton("Nuevo Animal");
	    
	  
	    btnNuevo.setFont(new Font("Arial", Font.BOLD, 14));

	    panelBotones.add(btnNuevo);
	   
	   
	    panel.add(panelBotones, BorderLayout.SOUTH);
	 // Conectamos los eventos de los botones del CRUD
	 		btnNuevo.addActionListener(e -> nuevoAnimal());
	 		
	 		
	    return panel;
	}
	// Formulario con JOptionPane para dar de alta un nuevo animal
	private void nuevoAnimal() {
		String idGenerado = Animal.generarSiguienteId(listaAnimales);
		JTextField txtId = new JTextField(idGenerado);
		txtId.setEditable(false);
		txtId.setEnabled(false);

		JTextField txtNombre = new JTextField();
		JComboBox<Especie> comboEspecie = new JComboBox<>(Especie.values());
		JTextField txtRaza = new JTextField();
		JComboBox<EstadoAnimal> comboEstado = new JComboBox<>(EstadoAnimal.values());

		

		JTextArea txtDescripcion = new JTextArea(3, 20);
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);
		JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);

		final java.io.File[] fotoSeleccionada = new java.io.File[1];
		JTextField txtRutaFoto = new JTextField();
		txtRutaFoto.setEditable(false);
		JButton btnElegirFoto = new JButton("Seleccionar foto...");
		btnElegirFoto.addActionListener(e -> {
			javax.swing.JFileChooser chooser = new javax.swing.JFileChooser("resources/images");
			chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
					"Imágenes (png, jpg, jpeg, gif)", "png", "jpg", "jpeg", "gif"));
			if (chooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
				fotoSeleccionada[0] = chooser.getSelectedFile();
				txtRutaFoto.setText(fotoSeleccionada[0].getName());
			}
		});

		JPanel panelFoto = new JPanel(new BorderLayout(5, 0));
		panelFoto.add(txtRutaFoto, BorderLayout.CENTER);
		panelFoto.add(btnElegirFoto, BorderLayout.EAST);

		JComponent[] campos = new JComponent[] {
			new JLabel("ID del Animal (automático):"), txtId,
			new JLabel("Nombre:"), txtNombre,
			new JLabel("Especie:"), comboEspecie,
			new JLabel("Raza:"), txtRaza,
			new JLabel("Estado:"), comboEstado,
			new JLabel("Descripción:"), scrollDescripcion,
			new JLabel("Foto principal:"), panelFoto
		};

		int opcion = JOptionPane.showConfirmDialog(this, campos, "Nuevo Animal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (opcion == JOptionPane.OK_OPTION) {
			if (txtNombre.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El Nombre es obligatorio", "Datos incompletos", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String idFinal = Animal.generarSiguienteId(listaAnimales);
			Especie especieSeleccionada = (Especie) comboEspecie.getSelectedItem();
			EstadoAnimal estadoSeleccionado = (EstadoAnimal) comboEstado.getSelectedItem();

			Animal nuevo = new Animal(
				idFinal,
				txtNombre.getText().trim(),
				especieSeleccionada,
				txtRaza.getText().trim(),
				LocalDate.now(),
				estadoSeleccionado
			);

			String descTexto = txtDescripcion.getText().trim();
			if (!descTexto.isEmpty()) {
				nuevo.setDescripcion(descTexto);
			}

			if (fotoSeleccionada[0] != null && fotoSeleccionada[0].exists()) {
				guardarFotoConCodigo(fotoSeleccionada[0], idFinal);
			}

			nuevo.cargarFotosAutomaticamente(); // carga la foto guardada y cualquier otra con su código en resources/images

			listaAnimales.add(nuevo);
			modeloAnimales.fireTableDataChanged();
		}
	}

	private void guardarFotoConCodigo(java.io.File origen, String codigoAnimal) {
		String nombreOrig = origen.getName();
		int punto = nombreOrig.lastIndexOf('.');
		String ext = (punto >= 0) ? nombreOrig.substring(punto).toLowerCase() : ".png";

		java.io.File carpeta = new java.io.File("resources/images");
		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}

		java.io.File destino = new java.io.File(carpeta, codigoAnimal + ext);
		try {
			java.nio.file.Files.copy(
					origen.toPath(),
					destino.toPath(),
					java.nio.file.StandardCopyOption.REPLACE_EXISTING
			);
		} catch (java.io.IOException ex) {
			JOptionPane.showMessageDialog(
					this,
					"No se ha podido copiar la foto: " + ex.getMessage(),
					"Aviso",
					JOptionPane.WARNING_MESSAGE
			);
		}
	}

		//  modificar los datos del animal seleccionado en la tabla
		private void editarAnimal(int fila) {
			
			

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
		private void eliminarAnimal(int fila) {
			

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
			modeloSolicitudes = new SolicitudTableModel(listaSolicitudes) { // IAG Gemini
	 		    private static final long serialVersionUID = 1L;


	 		    // 1. Le decimos a la tabla que hay una columna extra
	 		    @Override
	 		    public int getColumnCount() {
	 		        return super.getColumnCount() + 1; 
	 		    }

	 		    // 2. Le ponemos un título a esa nueva columna (la última)
	 		    @Override
	 		    public String getColumnName(int column) {
	 		        if (column == super.getColumnCount()) {
	 		            return "Eliminar Solicitud"; // El nombre de tu nueva columna
	 		        }
	 		        return super.getColumnName(column);
	 		    }

	 		    // 3. Le decimos qué dato debe mostrar esa nueva columna
	 		    @Override
	 		    public Object getValueAt(int rowIndex, int columnIndex) {
	 		        if (columnIndex == super.getColumnCount()) {
	 		            return ""; // Aquí puedes devolver un texto, o dejarlo vacío si vas a poner un botón con el CellRenderer
	 		        }
	 		        return super.getValueAt(rowIndex, columnIndex);
	 		    }
	 		};
			
	 		
			tablaSolicitudes = new JTable(modeloSolicitudes);

			tablaSolicitudes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaSolicitudes.setRowHeight(24);
			tablaSolicitudes.getColumnModel().getColumn(0).setPreferredWidth(60);
			tablaSolicitudes.getColumnModel().getColumn(5).setHeaderValue("Gestionar Estado A");
			
			tablaSolicitudes.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() { // aqui se crea el boton pero solo aparece como imagen
				private static final long serialVersionUID = 1L;
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					JButton botonCelda = new JButton("Gestionar");
					botonCelda.setBackground(Color.BLACK);
					botonCelda.setForeground(Color.WHITE);
					botonCelda.setFont(new Font("Arial", Font.BOLD, 12));
					return botonCelda;
					
					
				}
			});
			tablaSolicitudes.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() { // aqui se crea el boton pero solo aparece como imagen
				private static final long serialVersionUID = 1L;
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					JButton botonCelda = new JButton("Eliminar");
					botonCelda.setBackground(Color.RED);
					botonCelda.setForeground(Color.WHITE);
					botonCelda.setFont(new Font("Arial", Font.BOLD, 12));
					return botonCelda;
					
					
				}
			});
			tablaSolicitudes.addMouseListener(new java.awt.event.MouseAdapter() { // le añadimos el listener porque sino seria como si solo fuera una imagen
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					int columnaClic = tablaSolicitudes.columnAtPoint(e.getPoint());
					int filaClic = tablaSolicitudes.rowAtPoint(e.getPoint());
					
					
					if (columnaClic == 5 && filaClic != -1) {
						gestionarSolicitud(filaClic); 
					} else if (columnaClic == 6 && filaClic != -1) {
						eliminarSolicitud(filaClic) ; 
						}
				}
			});
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
			
			

			JScrollPane scrollTabla = new JScrollPane(tablaSolicitudes);
			panel.add(scrollTabla, BorderLayout.CENTER);

			

			return panel;
		}

		// Abre un diálogo para aprobar, rechazar o cambiar el estado de la solicitud seleccionada
		private void gestionarSolicitud(int fila) {
			

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
		private void eliminarSolicitud(int fila) {
			

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
		
		tablaCitas.getColumnModel().getColumn(6).setHeaderValue("Atender Cita");
		tablaCitas.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() { // aqui se crea el boton pero solo aparece como imagen
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JButton botonCelda = new JButton("Atender");
				botonCelda.setBackground(Color.BLACK);
				botonCelda.setForeground(Color.WHITE);
				botonCelda.setFont(new Font("Arial", Font.BOLD, 12));
				return botonCelda;
				
				
			}
		});
		tablaCitas.getColumnModel().getColumn(7).setHeaderValue("Cancelar Cita");
		tablaCitas.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() { // aqui se crea el boton pero solo aparece como imagen
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JButton botonCelda = new JButton("Cancelar");
				botonCelda.setBackground(Color.RED);
				botonCelda.setForeground(Color.WHITE);
				botonCelda.setFont(new Font("Arial", Font.BOLD, 12));
				return botonCelda;
				
				
			}
		});
		tablaCitas.addMouseListener(new java.awt.event.MouseAdapter() { // le añadimos el listener porque sino seria como si solo fuera una imagen
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int columnaClic = tablaCitas.columnAtPoint(e.getPoint());
				int filaClic = tablaCitas.rowAtPoint(e.getPoint());
				
				
				if (columnaClic == 6 && filaClic != -1) {
					atenderCita(filaClic); 
				} else if (columnaClic == 7 && filaClic != -1) {
						cancelarCita(filaClic); 
					}
			}
		});
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

		return panel;
	}
	
	private void atenderCita(int fila) {
		CitaAgenda cita = listaCitas.get(fila);
		cita.setEstado(EstadoCita.ATENDIDA); // Cambiamos el estado
		modeloCitas.fireTableDataChanged();
		JOptionPane.showMessageDialog(this, "Cita " + cita.getId() + " marcada como atendida.", "Agenda", JOptionPane.INFORMATION_MESSAGE);
	
	}
	private void cancelarCita(int fila) {
		CitaAgenda cita = listaCitas.get(fila);
		int conf = JOptionPane.showConfirmDialog(this, "¿Cancelar la cita " + cita.getId() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
		if (conf == JOptionPane.YES_OPTION) {
			cita.setEstado(EstadoCita.CANCELADA); // cambiamos el estado
			modeloCitas.fireTableDataChanged();}
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
        List<String> fotosPruebaAnubis = new ArrayList<>();
        fotosPruebaAnubis.add("resources/images/A006.png");
        fotosPruebaAnubis.add("resources/images/A0061.png");
        List<String> fotosPruebaTito = new ArrayList<>();
        fotosPruebaTito.add("resources/images/A007.png");
        fotosPruebaTito.add("resources/images/A0071.png");

	        
	    List<Animal> mocks = new ArrayList<>();

	    mocks.add(new Animal("A001", "Toby", Especie.PERRO, "Mestizo", LocalDate.of(2022, 5, 10), EstadoAnimal.DISPONIBLE,"Es un perro miedoso",true,true,fotosPruebaToby));
	    mocks.add(new Animal("A002", "Luna", Especie.GATO, "Siamés", LocalDate.of(2023, 8, 20), EstadoAnimal.EN_ACOGIDA, "Es una gata que trepa muy bien",false, true,fotosPruebaLuna));
	    mocks.add(new Animal("A003", "Max", Especie.PERRO, "Labrador", LocalDate.of(2021, 2, 15), EstadoAnimal.ADOPTADO,"Es un perro jugueton",true,false,fotosPruebaMax));
	    mocks.add(new Animal("A004", "Kivi", Especie.PERRO, "Galgo", LocalDate.of(2016, 3, 15), EstadoAnimal.ADOPTADO,"Le encanta correr pero se cansa rápido",false, false,fotosPruebaKivi ));
	    mocks.add(new Animal("A005", "Coco", Especie.LORO, "Guacamayo", LocalDate.of(2007, 6, 8), EstadoAnimal.DISPONIBLE, "Le gusta cantar Rosalia", false, true, fotosPruebaCoco));
	    mocks.add(new Animal("A006", "Anubis", Especie.GATO, "Sphynx", LocalDate.of(2018, 9, 8), EstadoAnimal.DISPONIBLE, "Es un gato muy cariñoso. Además no suelta pelos", true, true, fotosPruebaAnubis));
	    mocks.add(new Animal("A007", "Tito", Especie.LORO, "Cacatúa", LocalDate.of(2010, 6, 21), EstadoAnimal.DISPONIBLE, "Es un loro muy inteligente y expresivo", false, false, fotosPruebaTito));

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