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
import es.deusto.ingenieria.g23.domain.EstadoAnimal;

import es.deusto.ingenieria.g23.domain.Administrador;
import es.deusto.ingenieria.g23.domain.Persona;

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
		setSize(900, 600);
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
	    JScrollPane scrollTabla = new JScrollPane(tablaAnimales);
	    panel.add(scrollTabla, BorderLayout.CENTER);

	    JPanel panelBotones = new JPanel(new FlowLayout());
	    JButton btnNuevo = new JButton("Nuevo Animal");
	    JButton btnEditar = new JButton("Editar Seleccionado");
	    JButton btnEliminar = new JButton("Eliminar");

	    panelBotones.add(btnNuevo);
	    panelBotones.add(btnEditar);
	    panelBotones.add(btnEliminar);
	    panel.add(panelBotones, BorderLayout.SOUTH);

	    return panel;
	}

	// hacer luego
	private JPanel crearPanelAdopciones() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(new JLabel("Historial de Solicitudes de Adopción", SwingConstants.CENTER), BorderLayout.CENTER);
	    return panel;
	}
	// hacer luego
	private JPanel crearPanelAgenda() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(new JLabel("Agenda de Citas de la Fundación", SwingConstants.CENTER), BorderLayout.CENTER);
	    return panel;
	}

	
	// lista temporal de animaels
	private List<Animal> cargarAnimalesPrueba() {
	    List<Animal> mocks = new ArrayList<>();
	    EstadoAnimal estadoPorDefecto = EstadoAnimal.values()[0];

	    mocks.add(new Animal("A001", "Toby", "Perro", "Mestizo", LocalDate.of(2022, 5, 10), estadoPorDefecto));
	    mocks.add(new Animal("A002", "Luna", "Gato", "Siamés", LocalDate.of(2023, 8, 20), estadoPorDefecto));
	    mocks.add(new Animal("A003", "Max", "Perro", "Labrador", LocalDate.of(2021, 2, 15), estadoPorDefecto));

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