package es.deusto.ingenieria.g23.domain;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    // Carpeta donde están todas las fotos de los animales
    private static final String CARPETA_FOTOS = "resources/images";

    private String id;
    private String nombre;
    private Especie especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private EstadoAnimal estado;
    private String descripcion;
    private boolean vacunado;
    private boolean esterilizado;
    private List<String> rutasFotos;

    public Animal() {
        this.rutasFotos = new ArrayList<>();
    }

    public Animal(String id, String nombre, Especie especie, String raza, LocalDate fechaNacimiento, EstadoAnimal estado) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.descripcion = "Sin descripción disponible.";
        this.vacunado = false;
        this.esterilizado = false;
        this.rutasFotos = new ArrayList<>();
    }

    public Animal(String id, String nombre, Especie especie, String raza, LocalDate fechaNacimiento,
                  EstadoAnimal estado, String descripcion, boolean vacunado, boolean esterilizado,
                  List<String> rutasFotos) {
        this(id, nombre, especie, raza, fechaNacimiento, estado);
        this.descripcion = descripcion;
        this.vacunado = vacunado;
        this.esterilizado = esterilizado;
        if (rutasFotos != null) {
            this.rutasFotos = rutasFotos;
        }
    }

    // Genera el siguiente código disponible tipo A001, A002... a partir de la lista actual
    public static String generarSiguienteId(List<Animal> animalesExistentes) {
        int maxNumero = 0;

        if (animalesExistentes != null) {
            for (Animal a : animalesExistentes) {
                if (a != null && a.getId() != null) {
                    String codigo = a.getId().trim().toUpperCase();
                    if (codigo.matches("^A\\d+$")) {
                        try {
                            int num = Integer.parseInt(codigo.substring(1));
                            if (num > maxNumero) {
                                maxNumero = num;
                            }
                        } catch (NumberFormatException ignored) {
                            // Si el número supera el rango de int, se ignora
                        }
                    }
                }
            }
        }

        return String.format("A%03d", maxNumero + 1);
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public EstadoAnimal getEstado() { return estado; }
    public void setEstado(EstadoAnimal estado) { this.estado = estado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isVacunado() { return vacunado; }
    public void setVacunado(boolean vacunado) { this.vacunado = vacunado; }

    public boolean isEsterilizado() { return esterilizado; }
    public void setEsterilizado(boolean esterilizado) { this.esterilizado = esterilizado; }

    public List<String> getRutasFotos() { return rutasFotos; }
    public void setRutasFotos(List<String> rutasFotos) { this.rutasFotos = rutasFotos; }

    // Busca en resources/images todas las fotos de este animal usando su código (id) y las guarda en rutasFotos.
    // Devuelve cuántas fotos ha encontrado. Si no encuentra ninguna, la lista de fotos se queda como estaba.
    public int cargarFotosAutomaticamente() {
        File[] ficheros = new File(CARPETA_FOTOS).listFiles();
        if (id == null || id.trim().isEmpty() || ficheros == null) {
            return 0;
        }
        Pattern patron = Pattern.compile(
                "^" + Pattern.quote(id.trim()) + "(?:[_-]?(\\d{1,2}))?\\.(?:png|jpe?g|gif)$", Pattern.CASE_INSENSITIVE);

        List<File> fotos = new ArrayList<>();
        for (File f : ficheros) {
            if (f.isFile() && patron.matcher(f.getName()).matches()) {
                fotos.add(f);
            }
        }
        if (fotos.isEmpty()) {
            return 0;
        }
        // Primero la que no lleva número (la principal), después por número y, si empatan, por nombre
        fotos.sort(Comparator.comparingInt((File f) -> numeroDeFoto(patron, f))
                .thenComparing(File::getName, String.CASE_INSENSITIVE_ORDER));

        List<String> rutas = new ArrayList<>();
        for (File f : fotos) {
            rutas.add(CARPETA_FOTOS + "/" + f.getName());
        }
        this.rutasFotos = rutas;
        return rutas.size();
    }

    // Número que lleva el nombre de la foto (A001_3.png -> 3). Sin número vale 0
    private static int numeroDeFoto(Pattern patron, File f) {
        Matcher m = patron.matcher(f.getName());
        if (m.matches() && m.group(1) != null) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }

    // Método para calcular la edad aproximada
    public int getEdadAnios() {
        if (fechaNacimiento == null) return 0;
        LocalDate hoy = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, hoy);
        if (periodo.getYears() < 0) {
            return 0;
        }
        return periodo.getYears();
    }

    // Método para calcular la edad exacta
    public String getEdadTexto() {
        if (fechaNacimiento == null) {
            return "Edad desconocida";
        }
        LocalDate hoy = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, hoy);
        if (periodo.isNegative()) {
            return "Edad desconocida";
        }

        int anios = periodo.getYears();
        int meses = periodo.getMonths();

        if (anios > 0) {
            if (anios == 1) {
                return "1 año";
            } else {
                return anios + " años";
            }
        } else if (meses > 0) {
            if (meses == 1) {
                return "1 mes";
            } else {
                return meses + " meses";
            }
        } else {
            return "Menos de 1 mes";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nombre + " (" + especie + ", " + raza + ") - " + estado;
    }
}