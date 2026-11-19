
package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Bitacora {

    public static final String Nombre_Archivo = "bitacora.log";

    private final File archivo;

    public Bitacora() {
        archivo = new File(Nombre_Archivo);
    }

    
    public synchronized void registrar(String accion, String ruta) {
        try (FileWriter escritor = new FileWriter(archivo, true)) {
            escritor.write(Formato.fechaHoraActual() + " | " + accion + " | " + ruta  + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("No se pudo escribir en la bitácora: " + e.getMessage());
        }
    }

   
    public synchronized String leerTodo() throws IOException {
        if (archivo.exists() == false) {
            return "(La bitácora todavía está vacía)";
        }
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea = lector.readLine();
            while ((linea == null) == false) {
                contenido.append(linea).append("\n");
                linea = lector.readLine();
            }
        }
        return contenido.toString();
    }

    public String getRuta() {
        return archivo.getAbsolutePath();
    }
}