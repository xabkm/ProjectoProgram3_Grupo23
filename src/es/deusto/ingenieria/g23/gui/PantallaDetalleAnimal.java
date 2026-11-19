package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import es.deusto.ingenieria.g23.domain.Animal;
import es.deusto.ingenieria.g23.domain.Especie;
import es.deusto.ingenieria.g23.domain.EstadoAnimal;

public class PantallaDetalleAnimal extends JFrame {
    private static final long serialVersionUID = 1L;

    private Animal animal;
    private JFrame ventanaCatalogo;

    private JLabel lblFoto;
    private JLabel lblNumeroFoto;
    private JButton btnAbrirVideo;
    private File archivoMediaActual;
    private int fotoActual = 0;
    private volatile boolean ejecutandoCarrusel = true;

    public PantallaDetalleAnimal(Animal animal, JFrame ventanaCatalogo) {
        this.animal = animal;
        this.ventanaCatalogo = ventanaCatalogo;

        // Configuracion basica de la ventana
        setTitle("Detalle del Animal");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Cabecera en la parte norte
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(38, 50, 56));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton btnAtras = new JButton("Atrás");
        JLabel lblTitulo = new JLabel("DETALLE DEL ANIMAL", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        panelNorte.add(btnAtras, BorderLayout.WEST);
        panelNorte.add(lblTitulo, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        // Panel central dividido en dos columnas (fotos/videos y datos)
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 15, 15));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Columna izquierda para las fotos y videos
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Fotos y vídeos"));

        lblFoto = new JLabel("Sin foto", SwingConstants.CENTER);
        panelIzquierdo.add(lblFoto, BorderLayout.CENTER);

        JPanel panelContador = new JPanel(new FlowLayout());
        lblNumeroFoto = new JLabel("0 / 0");
        btnAbrirVideo = new JButton("Abrir vídeo");
        btnAbrirVideo.setVisible(false);
        panelContador.add(lblNumeroFoto);
        panelContador.add(btnAbrirVideo);
        panelIzquierdo.add(panelContador, BorderLayout.SOUTH);

        // Columna derecha para la informacion del animal
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 10));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Información"));

        JPanel panelDatos = new JPanel(new GridLayout(7, 2, 5, 8));

        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(new JLabel(animal.getNombre()));

        panelDatos.add(new JLabel("Especie:"));
        panelDatos.add(new JLabel(animal.getEspecie() != null ? animal.getEspecie().toString() : "-"));

        panelDatos.add(new JLabel("Raza:"));
        panelDatos.add(new JLabel(animal.getRaza()));

        panelDatos.add(new JLabel("Edad:"));
        panelDatos.add(new JLabel(animal.getEdadTexto()));

        panelDatos.add(new JLabel("Estado:"));
        panelDatos.add(new JLabel(animal.getEstado() != null ? animal.getEstado().toString() : "-"));

        panelDatos.add(new JLabel("Vacunado:"));
        if (animal.isVacunado()) {
            panelDatos.add(new JLabel("Sí"));
        } else {
            panelDatos.add(new JLabel("No"));
        }

        panelDatos.add(new JLabel("Esterilizado:"));
        if (animal.isEsterilizado()) {
            panelDatos.add(new JLabel("Sí"));
        } else {
            panelDatos.add(new JLabel("No"));
        }

        panelDerecho.add(panelDatos, BorderLayout.NORTH);

        // Zona de descripcion con scroll
        JPanel panelDescripcion = new JPanel(new BorderLayout());
        panelDescripcion.add(new JLabel("Descripción:"), BorderLayout.NORTH);

        JTextArea txtDescripcion = new JTextArea(animal.getDescripcion());
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);

        panelDescripcion.add(scrollDescripcion, BorderLayout.CENTER);
        panelDerecho.add(panelDescripcion, BorderLayout.CENTER);

        panelCentro.add(panelIzquierdo);
        panelCentro.add(panelDerecho);
        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior con el boton de adoptar
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblMotivo = new JLabel("");
        lblMotivo.setFont(new Font("Arial", Font.ITALIC, 12));

        JButton btnAdoptar = new JButton("Ir a adoptar");
        btnAdoptar.setFont(new Font("Arial", Font.BOLD, 14));

        // Desactivar boton si no esta disponible
        if (animal.getEstado() != EstadoAnimal.DISPONIBLE) {
            btnAdoptar.setEnabled(false);
            String estadoTxt = animal.getEstado() != null ? animal.getEstado().toString().toLowerCase() : "no disponible";
            String motivo = "No disponible para adopción (" + estadoTxt + ")";
            lblMotivo.setText(motivo);
            btnAdoptar.setToolTipText(motivo);
        }

        panelSur.add(lblMotivo);
        panelSur.add(btnAdoptar);
        add(panelSur, BorderLayout.SOUTH);

        // Reescalar la foto al maximizar o cambiar el tamaño del panel
        lblFoto.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mostrarFoto();
            }
        });

        // Abrir el video en el reproductor del sistema
        btnAbrirVideo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (archivoMediaActual != null && archivoMediaActual.exists()) {
                    try {
                        Desktop.getDesktop().open(archivoMediaActual);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                                PantallaDetalleAnimal.this,
                                "No se ha podido abrir el vídeo: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        // Mostrar la primera foto o video al iniciar
        mostrarFoto();
        iniciarCarruselFotos();

        // parar el hilo cuando la ventana se cierre (con la X o con Atrás)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ejecutandoCarrusel = false;
            }
        });

        // Evento boton Atras
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutandoCarrusel = false;
                if (ventanaCatalogo != null) {
                    ventanaCatalogo.setVisible(true);
                }
                dispose();
            }
        });

        // Evento boton Ir a adoptar
        btnAdoptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PantallaFormulario formulario = new PantallaFormulario(animal);
                formulario.setVisible(true);
            }
        });
    }

    // Hilo que pasa las fotos/videos automaticamente cada 3 segundos
    private void iniciarCarruselFotos() {
        List<String> fotos = animal.getRutasFotos();

        // Solo arrancamos el hilo si hay mas de 1 elemento
        if (fotos == null || fotos.size() <= 1) {
            return;
        }

        Thread hiloFotos = new Thread(new Runnable() {
            @Override
            public void run() {
                while (ejecutandoCarrusel) {
                    try {
                        // Espera 3 segundos (3000 milisegundos)
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        break;
                    }

                    // Si la ventana sigue abierta, pasamos al siguiente elemento
                    if (ejecutandoCarrusel) {
                        fotoActual++;
                        if (fotoActual >= fotos.size()) {
                            fotoActual = 0;
                        }

                        // Actualizamos el JLabel de forma segura en Swing
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                mostrarFoto();
                            }
                        });
                    }
                }
            }
        });

        hiloFotos.start();
    }

    private boolean esVideo(String ruta) {
        if (ruta == null) {
            return false;
        }
        String minuscula = ruta.toLowerCase();
        return minuscula.endsWith(".mp4")
                || minuscula.endsWith(".avi")
                || minuscula.endsWith(".mov")
                || minuscula.endsWith(".mkv")
                || minuscula.endsWith(".webm");
    }

    // Carga y escala la foto actual en el JLabel, o prepara el acceso si es un video
    private void mostrarFoto() {
        List<String> fotos = animal.getRutasFotos();
        archivoMediaActual = null;
        btnAbrirVideo.setVisible(false);

        if (fotos == null || fotos.isEmpty()) {
            lblFoto.setIcon(null);
            lblFoto.setText("Sin fotos disponibles");
            lblNumeroFoto.setText("0 / 0");
            return;
        }

        lblNumeroFoto.setText((fotoActual + 1) + " / " + fotos.size());
        String ruta = fotos.get(fotoActual);
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            lblFoto.setIcon(null);
            lblFoto.setText("No se encuentra el archivo");
            return;
        }

        if (esVideo(ruta)) {
            archivoMediaActual = archivo;
            lblFoto.setIcon(null);
            lblFoto.setText("Vídeo: " + archivo.getName());
            btnAbrirVideo.setVisible(true);
            return;
        }

        ImageIcon icono = new ImageIcon(ruta);
        int anchoOriginal = icono.getIconWidth();
        int altoOriginal = icono.getIconHeight();
        if (anchoOriginal > 0 && altoOriginal > 0) {
            int maxAncho = Math.max(300, lblFoto.getWidth() - 16);
            int maxAlto = Math.max(280, lblFoto.getHeight() - 16);
            double escala = Math.min((double) maxAncho / anchoOriginal, (double) maxAlto / altoOriginal);
            int nuevoAncho = Math.max(1, (int) Math.round(anchoOriginal * escala));
            int nuevoAlto = Math.max(1, (int) Math.round(altoOriginal * escala));
            Image imagen = icono.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(imagen));
            lblFoto.setText("");
        } else {
            lblFoto.setIcon(null);
            lblFoto.setText("No se puede mostrar el archivo");
        }
    }

    // Main para probar la ventana
    public static void main(String[] args) {
        List<String> fotosPrueba = new ArrayList<>();
        fotosPrueba.add("resources/images/max1.png");
        fotosPrueba.add("resources/images/max2.png");
        fotosPrueba.add("resources/images/anuncio.mp4");

        Animal perroPrueba = new Animal(
                "A01",
                "Max",
                Especie.PERRO,
                "Golden Retriever",
                LocalDate.of(2023, 5, 10),
                EstadoAnimal.DISPONIBLE,
                "Es un perro muy bueno y juguetón.",
                true,
                true,
                fotosPrueba
        );

        PantallaDetalleAnimal ventana = new PantallaDetalleAnimal(perroPrueba, null);
        ventana.setVisible(true);
    }
}