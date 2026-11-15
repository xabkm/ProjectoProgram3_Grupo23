package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import es.deusto.ingenieria.g23.domain.Animal;
import es.deusto.ingenieria.g23.domain.Especie;
import es.deusto.ingenieria.g23.domain.EstadoAnimal;
import es.deusto.ingenieria.g23.domain.Administrador;
import es.deusto.ingenieria.g23.domain.Persona;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableCellRenderer;



public class PantallaAdministracion extends JFrame {

	private static final long serialVersionUID = 1L;
	private Persona adminActual;
	private List<Animal> listaAnimales;
	private AnimalTableModel modeloAnimales;
	private JTable tablaAnimales;

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

		setTitle("Panel de Administración - Usuario: " + adminActual.getNombre());
		setSize(950, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JTabbedPane pestanas = new JTabbedPane();
		pestanas.addTab("Gestión de Animales", crearPanelAnimales());
		pestanas.addTab("Solicitudes de Adopción", crearPanelAdopciones());
		pestanas.addTab("Agenda de Citas", crearPanelAgenda());

		add(pestanas, BorderLayout.CENTER);
	}
	
	
	private JPanel crearPanelAnimales() {
	    JPanel panel = new JPanel(new BorderLayout());

	    modeloAnimales = new AnimalTableModel(listaAnimales);
	    tablaAnimales = new JTable(modeloAnimales);
	    
	    
	    
	 // Solo dejamos seleccionar una fila a la vez y ajustamos un poco el aspecto
	 		tablaAnimales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 		tablaAnimales.setRowHeight(24);
	 		tablaAnimales.getColumnModel().getColumn(0).setPreferredWidth(55);

	 		// Renderizado personalizado para la columna de Estado (colores según disponibilidad)
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
	    panel.add(scrollTabla, BorderLayout.CENTER);

	    JPanel panelBotones = new JPanel(new FlowLayout());
	    JButton btnNuevo = new JButton("Nuevo Animal");
	    JButton btnEditar = new JButton("Editar");
	    JButton btnEliminar = new JButton("Eliminar");

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
	// hacer luego
	private JPanel crearPanelAdopciones() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(new JLabel("Historial de solicitudes de adopción", SwingConstants.CENTER), BorderLayout.CENTER);
	    return panel;
	}
	// hacer luego
	private JPanel crearPanelAgenda() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(new JLabel("Agenda de citas de la Fundación", SwingConstants.CENTER), BorderLayout.CENTER);
	    return panel;
	}

	
	// lista temporal de animales
	private List<Animal> cargarAnimalesPrueba() {
	    List<Animal> mocks = new ArrayList<>();

	    mocks.add(new Animal("A001", "Toby", Especie.PERRO, "Mestizo", LocalDate.of(2022, 5, 10), EstadoAnimal.DISPONIBLE));
	    mocks.add(new Animal("A002", "Luna", Especie.GATO, "Siamés", LocalDate.of(2023, 8, 20), EstadoAnimal.EN_ACOGIDA));
	    mocks.add(new Animal("A003", "Max", Especie.PERRO, "Labrador", LocalDate.of(2021, 2, 15), EstadoAnimal.ADOPTADO));
	    mocks.add(new Animal("A004", "Pablo", Especie.PERRO, "Chihuahua", LocalDate.of(2011, 12, 15), EstadoAnimal.ADOPTADO));

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