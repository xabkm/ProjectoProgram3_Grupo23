import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Herramienta para analizar el aporte individual de cada persona al repositorio.
 * Se rocesa la información de los commits de Git y genera estadísticas del trabajo
 * de cada integrante del equipo.
 */
public class AnalizadorContribuciones {

    public static void main(String[] args) {
        System.out.println("=== ANALIZADOR DE CONTRIBUCIONES DEL EQUIPO ===");
        
        try {
            // Ejecuta el comando git log para obtener el autor de cada commit
            Process process = Runtime.getRuntime().exec("git log --pretty=format:%an");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            Map<String, Integer> commitsPorAutor = new HashMap<>();
            String autor;
            int totalCommits = 0;

            while ((autor = reader.readLine()) != null) {
                if (!autor.trim().isEmpty()) {
                    commitsPorAutor.put(autor, commitsPorAutor.getOrDefault(autor, 0) + 1);
                    totalCommits++;
                }
            }

            // Mostrar resultados del aporte individual
            System.out.println("\nTotal de commits analizados: " + totalCommits);
            System.out.println("----------------------------------------------");
            for (Map.Entry<String, Integer> entry : commitsPorAutor.entrySet()) {
                double porcentaje = (double) entry.getValue() / totalCommits * 100;
                System.out.printf("Autor: %-20s | Commits: %d (%.2f%%)\n", 
                                  entry.getKey(), entry.getValue(), porcentaje);
            }

        } catch (Exception e) {
            System.err.println("Error al analizar los commits del repositorio: " + e.getMessage());
            System.err.println("Asegúrate de ejecutar esta clase dentro de la carpeta del proyecto Git local.");
        }
    }
}
//Guillermo Muga: Desarrollado mediante programación asistida por IA
