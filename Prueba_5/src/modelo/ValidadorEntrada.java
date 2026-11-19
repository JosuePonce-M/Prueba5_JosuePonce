package modelo;

import java.io.File;


public class ValidadorEntrada {

   private static final char[] Caracteres_invalidos = {'\\', '/', ':', '*', '?', '"', '<', '>', '|'};

   
    public static File validarDirectorio(String ruta) throws RutaInvalidaException {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new RutaInvalidaException("Faalta la ruta");
        }
        File carpeta = new File(ruta.trim());
        if (carpeta.exists() == false) {
            throw new RutaInvalidaException("La ruta no se encontro " + carpeta.getAbsolutePath());
        }
        if (carpeta.isDirectory() == false) {
            throw new RutaInvalidaException("La ruta no es un directorio " + carpeta.getAbsolutePath());
        }
        return carpeta;
    }

    
    public static int validarProfundidad(String texto) throws FiltroInvalidoException {
        if (texto == null || texto.trim().isEmpty()) {
            return 0;
        }
        int valor;
        try {
            valor = Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new FiltroInvalidoException("La profundidad debe ser un número entero");
        }
        if (valor < 0) {
            throw new FiltroInvalidoException("La profundidad no puede ser negativa");
        }
        return valor;
    }

   
    public static void validarNombre(String nombre) throws OperacionArchivoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new OperacionArchivoException("El nombre no puede estar vacío");
        }
        if (nombre.equals(".") || nombre.equals("..")) {
            throw new OperacionArchivoException("El nombre \"" + nombre + "\" no es un nombre valido.");
        }
          for (int i = 0; i < nombre.length(); i++) {
        char c = nombre.charAt(i);
        for (int j = 0; j < Caracteres_invalidos.length; j++) {
        if (c == Caracteres_invalidos[j]) {
            throw new OperacionArchivoException("El nombre contiene un carácter inválido: " + c);
        }
    }
}
        if (nombre.endsWith(" ") || nombre.endsWith(".")) {
            throw new OperacionArchivoException("El nombre no puede terminar en espacio o punto");
        }
    }
}