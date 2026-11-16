
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
		
		JTabbedPane pestanas = new JTabbedPane();
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
		JPanel panel = new JPanel(new BorderLayout(5, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		modeloAnimales = new AnimalTableModel(listaAnimales);
		tablaAnimales = new JTable(modeloAnimales);
		
		
		return panel;
	}
	
	
	private JPanel crearPanelMisSolicitudes() {
		JPanel panel = new JPanel(new BorderLayout(5, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		modeloSolicitudes = new SolicitudTableModel(misSolicitudes);
		tablaSolicitudes = new JTable(modeloSolicitudes);
		tablaSolicitudes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaSolicitudes.setRowHeight(24);
		
		return panel;
	}
	
	private JPanel crearPanelMisCitas() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		
		modeloCitas = new CitaTableModel(misCitas);
		tablaCitas = new JTable(modeloCitas);
		tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaCitas.setRowHeight(24);
		
		return panel;
	}
	
	private List<Animal> cargarAnimalesPrueba() {
		List<Animal> animales = new ArrayList<>();
		animales.add(new Animal("A001", "Bizkor", Especie.PERRO, "Golden", LocalDate.of(2022, 5, 10), EstadoAnimal.DISPONIBLE));
		animales.add(new Animal("A002", "Pilot", Especie.GATO, "Persa", LocalDate.of(2023, 8, 20), EstadoAnimal.EN_ACOGIDA));
		animales.add(new Animal("A003", "Zuri", Especie.PERRO, "Pastor", LocalDate.of(2021, 2, 15), EstadoAnimal.DISPONIBLE));
		return animales;
	}
	
	private List<SolicitudAdopcion> cargarMisSolicitudesPrueba() {
		List<SolicitudAdopcion> solicitudes = new ArrayList<>();
		if(usuarioActual instanceof Usuario) {
			solicitudes.add(new SolicitudAdopcion("S001", listaAnimales.get(1), (Usuario)usuarioActual, LocalDateTime.now().minusDays(2), EstadoSolicitud.PENDIENTE));
		}
		return solicitudes;
	}
	private List<CitaAgenda> cargarMisCitasPrueba() {
		List<CitaAgenda> citas = new ArrayList<>();
		citas.add(new CitaAgenda("C001", LocalDateTime.now().plusDays(2).withHour(17).withMinute(0), TipoCita.ENTREVISTA_ADOPCION, listaAnimales.get(1), usuarioActual, EstadoCita.PENDIENTE));
		return citas;
	}
	public static void main(String[] args) {
		Usuario usuarioPrueba = new Usuario();
		usuarioPrueba.setNombre("Carlos Usuario");
		PantallaUsuario ventana = new PantallaUsuario(usuarioPrueba);
		ventana.setVisible(true);
	}
}
