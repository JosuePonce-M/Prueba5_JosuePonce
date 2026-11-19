package modelo;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FiltroBusqueda {

    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    public static final long SIN_LIMITE = -1;

    private String texto;
    private String extension;
    private long tamanoMin;
    private long tamanoMax;
    private Date fechaDesde;
    private Date fechaHasta;

   
    public static FiltroBusqueda crear(String texto, String extension, String minKB, String maxKB, String desde, String hasta) throws FiltroInvalidoException {
        FiltroBusqueda filtro = new FiltroBusqueda();
        filtro.texto = limpiar(texto).toLowerCase();
        filtro.extension = limpiar(extension).toLowerCase();
        if (filtro.extension.startsWith(".")) {
            filtro.extension = filtro.extension.substring(1);
        }
        filtro.tamanoMin = convertirTamano(minKB, "mínimo");
        filtro.tamanoMax = convertirTamano(maxKB, "máximo");
        filtro.fechaDesde = ConFecha(desde, "desde");
        filtro.fechaHasta = ConFecha(hasta, "hasta");
        filtro.validar();
        return filtro;
    }

  
    private static String limpiar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim();
    }

  
    private static long convertirTamano(String valor, String nombre) throws FiltroInvalidoException {
        String limpio = limpiar(valor);
        if (limpio.isEmpty()) {
            return SIN_LIMITE;
        }
        long numero;
        try {
            numero = Long.parseLong(limpio);
        } catch (NumberFormatException e) {
            throw new FiltroInvalidoException("El tamaño " + nombre + " debe ser un número entero .");
        }
        if (numero < 0) {
            throw new FiltroInvalidoException("El tamaño " + nombre + " no puede ser negativo");
        }
        return numero;
    }

   
    private static Date ConFecha(String valor, String nombre) throws FiltroInvalidoException {
        String limpio = limpiar(valor);
        if (limpio.isEmpty()) {
            return null;
        }
        SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
        formato.setLenient(false);
        try {
            return formato.parse(limpio);
        } catch (ParseException e) {
            throw new FiltroInvalidoException("La fecha \"" + nombre + "\" debe tener el formato dd/MM/yyyy");
        }
    }

   
    public void validar() throws FiltroInvalidoException {
        if (tamanoMin > SIN_LIMITE && tamanoMax > SIN_LIMITE && tamanoMin > tamanoMax) {
            throw new FiltroInvalidoException("El tamaño mínimo no puede ser mayor que el máximo");
        }
        if ((fechaDesde == null) == false && (fechaHasta == null) == false && fechaDesde.after(fechaHasta)) {
            throw new FiltroInvalidoException("La fecha \"desde\" no puede ser posterior a la fecha \"hasta\"");
        }
        boolean sinCriterios = texto.isEmpty() && extension.isEmpty()
                && tamanoMin == SIN_LIMITE && tamanoMax == SIN_LIMITE
                && fechaDesde == null && fechaHasta == null;
        if (sinCriterios) {
            throw new FiltroInvalidoException("Escriba un texto de búsqueda o al menos un filtro");
        }
    }

  
    public boolean cumple(File archivo) {
        return cumpleNombre(archivo) && cumpleExtension(archivo)
                && cumpleTamano(archivo) && cumpleFecha(archivo);
    }

    private boolean cumpleNombre(File archivo) {
        if (texto.isEmpty()) {
            return true;
        }
        return archivo.getName().toLowerCase().contains(texto);
    }

    private boolean cumpleExtension(File archivo) {
        if (extension.isEmpty()) {
            return true;
        }
        return Formato.Extension(archivo).equals(extension);
    }

    private boolean cumpleTamano(File archivo) {
        long bytes = archivo.length();
        if (tamanoMin > SIN_LIMITE && bytes < tamanoMin * 1024) {
            return false;
        }
        if (tamanoMax > SIN_LIMITE && bytes > tamanoMax * 1024) {
            return false;
        }
        return true;
    }

    private boolean cumpleFecha(File archivo) {
        long modificado = archivo.lastModified();
        if ((fechaDesde == null) == false && modificado < fechaDesde.getTime()) {
            return false;
        }
      
        if ((fechaHasta == null) == false && modificado > fechaHasta.getTime() + 86399999L) {
            return false;
        }
        return true;
    }

    public String getTexto() {
        return texto;
    }

   
    public String descripcion() {
        return "texto='" + texto + "' ext='" + extension + "' min=" + tamanoMin
                + "KB max=" + tamanoMax + "KB";
    }
}