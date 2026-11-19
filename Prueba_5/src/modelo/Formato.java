package modelo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Formato {

    public static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
  // metodo para el tamno
    public static String tamno(long bytes) {
        double kb = 1024.0;
        double mb = kb * 1024;
        double gb = mb * 1024;
        if (bytes >= gb) {
            return String.format("%.2f GB", bytes / gb);
        }
        if (bytes >= mb) {
            return String.format("%.2f MB", bytes / mb);
        }
        if (bytes >= kb) {
            return String.format("%.2f KB", bytes / kb);
        }
        return bytes + " B";
    }

   
    public static String Fecha(long milisegundos) {
        SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA_HORA);
        return formato.format(new Date(milisegundos));
    }

  
    public static String fechaHoraActual() {
        return Fecha(System.currentTimeMillis());
    }

   
    public static String Extension(File archivo) {
        String nombre = archivo.getName();
        int punto = nombre.lastIndexOf('.');
        if (punto <= 0 || punto == nombre.length() - 1) {
            return "";
        }
        return nombre.substring(punto + 1).toLowerCase();
    }
}
