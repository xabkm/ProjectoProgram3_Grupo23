
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
		panelNorte.setBackground(Color.blue);
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
		JPanel panelanimales = new JPanel(new BorderLayout(5, 10));
		panelanimales.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		modeloAnimales = new AnimalTableModel(listaAnimales);
		tablaAnimales = new JTable(modeloAnimales);
		tablaAnimales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaAnimales.setRowHeight(24);
		tablaAnimales.getColumnModel().getColumn(0).setPreferredWidth(55);
		tablaAnimales.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
			
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
						c.setBackground(Color.green);
					} else if (estado == EstadoAnimal.ADOPTADO) {
						c.setBackground(Color.red);
					} else {
						c.setBackground(Color.yellow);
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
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Selecciona un animal de la lista para adoptarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		Animal seleccionado = listaAnimales.get(fila);
		if (seleccionado.getEstado() != EstadoAnimal.DISPONIBLE) {
			JOptionPane.showMessageDialog(this, "Lo sentimos, " + seleccionado.getNombre() + " no está disponible actualmente.", "No disponible", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int opcion = JOptionPane.showConfirmDialog(this,
				"¿Deseas enviar una solicitud de adopción para " + seleccionado.getNombre() + "?",
				"Confirmar solicitud", JOptionPane.YES_NO_OPTION);
		if (opcion == JOptionPane.YES_OPTION) {
			
			SolicitudAdopcion nuevaSolicitud = new SolicitudAdopcion(
					"S" + (misSolicitudes.size() + 100),
					seleccionado,
					(Usuario)usuarioActual,
					LocalDateTime.now(),
					EstadoSolicitud.PENDIENTE
			);
			misSolicitudes.add(nuevaSolicitud);
			modeloSolicitudes.fireTableDataChanged(); 
			JOptionPane.showMessageDialog(this, "Solicitud enviada correctamente. El equipo la revisará pronto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		}
	}


	
	
	private JPanel crearPanelMisSolicitudes() {
		JPanel panelsolicitudes = new JPanel(new BorderLayout(5, 10));
		panelsolicitudes.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		modeloSolicitudes = new SolicitudTableModel(misSolicitudes);
		tablaSolicitudes = new JTable(modeloSolicitudes);
		tablaSolicitudes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaSolicitudes.setRowHeight(24);
		panelsolicitudes.add(new JScrollPane(tablaSolicitudes), BorderLayout.CENTER);
		return panelsolicitudes;
	}
	
	private JPanel crearPanelMisCitas() {
		JPanel panelcitas = new JPanel(new BorderLayout());
		panelcitas.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		
		modeloCitas = new CitaTableModel(misCitas);
		tablaCitas = new JTable(modeloCitas);
		tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaCitas.setRowHeight(24);
		panelcitas.add(new JScrollPane(tablaCitas), BorderLayout.CENTER);
		return panelcitas;
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
