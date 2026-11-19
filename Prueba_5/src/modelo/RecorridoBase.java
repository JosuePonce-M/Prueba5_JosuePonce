package modelo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public abstract class RecorridoBase {

    protected final Bitacora bitacora;
    protected final ProgresoListener progreso;
    protected final int profundidadMaxima;
    private final Set<String> carpetasVisitadas;

    public RecorridoBase(Bitacora bitacora, ProgresoListener progreso, int profundidadMaxima) {
        this.bitacora = bitacora;
        this.progreso = progreso;
        this.profundidadMaxima = profundidadMaxima;
        this.carpetasVisitadas = new HashSet<>();
    }

    /** Decide si se entra a una carpeta  */
    protected boolean puedeEntrar(File carpeta, int profundidad) {
        if (profundidadMaxima > 0 && profundidad >= profundidadMaxima) {
            return false;
        }
        if (Files.isSymbolicLink(carpeta.toPath())) {
            bitacora.registrar("ENLACE SIMBOLICO OMITIDO", carpeta.getAbsolutePath());
            return false;
        }
        try {
            String canonica = carpeta.getCanonicalPath();
            if (carpetasVisitadas.contains(canonica)) {
                return false;
            }
            carpetasVisitadas.add(canonica);
            return true;
        } catch (IOException e) {
            bitacora.registrar("CARPETA OMITIDA (error de ruta)", carpeta.getAbsolutePath());
            return false;
        }
    }

    /** Lista carpetas primero y luego archivos */
    protected File[] listarContenido(File carpeta) {
        File[] hijos = carpeta.listFiles();
        if (hijos == null) {
            bitacora.registrar("SIN PERMISO (omitida)", carpeta.getAbsolutePath());
            return null;
        }
        List<File> carpetas = new ArrayList<>();
        List<File> archivos = new ArrayList<>();
        for (File hijo : hijos) {
            if (hijo.isDirectory()) {
                carpetas.add(hijo);
            } else {
                archivos.add(hijo);
            }
        }
        carpetas.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        archivos.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        carpetas.addAll(archivos);
        return carpetas.toArray(new File[0]);
    }

    protected boolean estaCancelado() {
        if (progreso == null) {
            return false;
        }
        return progreso.estaCancelado();
    }

    protected void informar(File elemento) {
        if (progreso == null) {
            return;
        }
        progreso.avanzar(elemento.getAbsolutePath());
    }

    /** Informa el porcentaje*/
    protected void informarPorcentaje(int hechos, int total, int profundidad) {
        if (progreso == null || profundidad > 0 || total == 0) {
            return;
        }
        progreso.porcentaje(hechos * 100 / total);
    }
}