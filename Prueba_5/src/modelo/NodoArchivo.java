package modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class NodoArchivo {

    private final File archivo;
    private final List<NodoArchivo> hijos;
    private boolean sinPermiso;

    public NodoArchivo(File archivo) {
        this.archivo = archivo;
        this.hijos = new ArrayList<>();
        this.sinPermiso = false;
    }

    public void agregarHijo(NodoArchivo hijo) {
        hijos.add(hijo);
    }

    public File getArchivo() {
        return archivo;
    }

    public List<NodoArchivo> getHijos() {
        return hijos;
    }

    public boolean esCarpeta() {
        return archivo.isDirectory();
    }

    public boolean isSinPermiso() {
        return sinPermiso;
    }

    public void setSinPermiso(boolean sinPermiso) {
        this.sinPermiso = sinPermiso;
    }

    /** Texto que se muestra en el árbol*/
    @Override
    public String toString() {
        String nombre = archivo.getName();
        if (nombre.isEmpty()) {
            nombre = archivo.getAbsolutePath();
        }
        if (sinPermiso) {
            nombre = nombre + "  [sin permiso]";
        }
        return nombre;
    }
}
