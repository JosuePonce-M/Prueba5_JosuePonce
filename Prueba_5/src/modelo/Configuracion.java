
package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class Configuracion {

    public static final String NOMBRE_ARCHIVO = "config.txt";

    public static final String RUTA = "ultimaRuta";
    public static final String PROFUNDIDAD = "profundidad";
    public static final String TEXTO = "texto";
    public static final String EXTENSION = "extension";
    public static final String TAM_MIN = "tamanoMin";
    public static final String TAM_MAX = "tamanoMax";
    public static final String FECHA_DESDE = "fechaDesde";
    public static final String FECHA_HASTA = "fechaHasta";

    private final File archivo;
    private final Map<String, String> valores;

    public Configuracion() {
        archivo = new File(NOMBRE_ARCHIVO);
        valores = new LinkedHashMap<>();
    }

    
    public void cargar() throws IOException {
        valores.clear();
        if (archivo.exists() == false) {
            return;
        }
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea = lector.readLine();
            while ((linea == null) == false) {
                int igual = linea.indexOf('=');
                if (igual > 0) {
                    valores.put(linea.substring(0, igual), linea.substring(igual + 1));
                }
                linea = lector.readLine();
            }
        }
    }

   
    public void guardar() throws IOException {
        try (FileWriter escritor = new FileWriter(archivo)) {
            for (Map.Entry<String, String> entrada : valores.entrySet()) {
                escritor.write(entrada.getKey() + "=" + entrada.getValue() + System.lineSeparator());
            }
        }
    }

   
    public String obtener(String clave) {
        String valor = valores.get(clave);
        if (valor == null) {
            return "";
        }
        return valor;
    }

    
    public void establecer(String clave, String valor) {
        if (valor == null) {
            valor = "";
        }
        valores.put(clave, valor.replace("\n", " ").replace("\r", " "));
    }
}