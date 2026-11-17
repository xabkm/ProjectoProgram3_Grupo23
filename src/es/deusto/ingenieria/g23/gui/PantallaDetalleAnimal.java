package es.deusto.ingenieria.g23.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private int fotoActual = 0;
    private boolean ejecutandoCarrusel = true;

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
        panelNorte.setBackground(new Color(45, 106, 79));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton btnAtras = new JButton("Atrás");
        JLabel lblTitulo = new JLabel("DETALLE DEL ANIMAL", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        panelNorte.add(btnAtras, BorderLayout.WEST);
        panelNorte.add(lblTitulo, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        // Panel central dividido en dos columnas (fotos y datos)
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 15, 15));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Columna izquierda para las fotos(Antes de meter un thread en vez de botones)
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Fotos"));

        lblFoto = new JLabel("Sin foto", SwingConstants.CENTER);
        panelIzquierdo.add(lblFoto, BorderLayout.CENTER);

//        JPanel panelBotonesFoto = new JPanel(new FlowLayout());
//        JButton btnFotoAnterior = new JButton("<");
//        lblNumeroFoto = new JLabel("0 / 0");
//        JButton btnFotoSiguiente = new JButton(">");
//
//        panelBotonesFoto.add(btnFotoAnterior);
//        panelBotonesFoto.add(lblNumeroFoto);
//        panelBotonesFoto.add(btnFotoSiguiente);
//        panelIzquierdo.add(panelBotonesFoto, BorderLayout.SOUTH);
         JPanel panelContador = new JPanel(new FlowLayout());
         lblNumeroFoto = new JLabel("0 / 0");
         panelContador.add(lblNumeroFoto);
         panelIzquierdo.add(panelContador, BorderLayout.SOUTH);

        // Columna derecha para la informacion del animal
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 10));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Información"));

        JPanel panelDatos = new JPanel(new GridLayout(5, 2, 5, 8));

        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(new JLabel(animal.getNombre()));

        panelDatos.add(new JLabel("Raza:"));
        panelDatos.add(new JLabel(animal.getRaza()));

        panelDatos.add(new JLabel("Edad:"));
        panelDatos.add(new JLabel(animal.getEdadAnios() + " años"));

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
        JButton btnAdoptar = new JButton("Ir a adoptar");
        btnAdoptar.setFont(new Font("Arial", Font.BOLD, 14));

        // Desactivar boton si no esta disponible
        if (animal.getEstado() != EstadoAnimal.DISPONIBLE) {
            btnAdoptar.setEnabled(false);
        }

        panelSur.add(btnAdoptar);
        add(panelSur, BorderLayout.SOUTH);

        // Mostrar la primera foto al iniciar
        mostrarFoto();
        iniciarCarruselFotos();
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

        // Evento foto anterior
//        btnFotoAnterior.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (animal.getRutasFotos() != null && !animal.getRutasFotos().isEmpty()) {
//                    fotoActual--;
//                    if (fotoActual < 0) {
//                        fotoActual = animal.getRutasFotos().size() - 1;
//                    }
//                    mostrarFoto();
//                }
//            }
//        });
//
//        // Evento foto siguiente
//        btnFotoSiguiente.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (animal.getRutasFotos() != null && !animal.getRutasFotos().isEmpty()) {
//                    fotoActual++;
//                    if (fotoActual >= animal.getRutasFotos().size()) {
//                        fotoActual = 0;
//                    }
//                    mostrarFoto();
//                }
//            }
//        });

        // Evento boton Ir a adoptar
        btnAdoptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 ejecutandoCarrusel = false;
            	 PantallaFormulario.main(null);
            }
        });
    }

 // Hilo que pasa las fotos automaticamente cada 3 segundos
    private void iniciarCarruselFotos() {
        List<String> fotos = animal.getRutasFotos();

        // Solo arrancamos el hilo si hay mas de 1 foto
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

                    // Si la ventana sigue abierta, pasamos a la siguiente foto
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
    
    // Carga y escala la foto actual en el JLabel
    private void mostrarFoto() {
        List<String> fotos = animal.getRutasFotos();

        if (fotos == null || fotos.isEmpty()) {
            lblFoto.setIcon(null);
            lblFoto.setText("Sin fotos disponibles");
            lblNumeroFoto.setText("0 / 0");
            return;
        }

        lblNumeroFoto.setText((fotoActual + 1) + " / " + fotos.size());
        String ruta = fotos.get(fotoActual);
        File archivo = new File(ruta);

        if (archivo.exists()) {
            ImageIcon icono = new ImageIcon(ruta);
            Image imagen = icono.getImage().getScaledInstance(300, 280, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(imagen));
            lblFoto.setText("");
        } else {
            lblFoto.setIcon(null);
            lblFoto.setText("No se encuentra la imagen");
        }
    }

    // Main para probar la ventana
    public static void main(String[] args) {
        List<String> fotosPrueba = new ArrayList<>();
        fotosPrueba.add("resources/images/max1.png");
        fotosPrueba.add("resources/images/max2.png");

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