package modelo;

import java.io.File;


public class ExploradorArchivo extends RecorridoBase {

    public ExploradorArchivo(Bitacora bitacora, ProgresoListener progreso, int profundidadMaxima) {
        super(bitacora, progreso, profundidadMaxima);
    }

    public NodoArchivo construirArbol(File raiz) {
        return construirNodo(raiz, 0);
    }

   // metodo recursivc que construye el arbol de carpetas
    private NodoArchivo construirNodo(File elemento, int profundidad) {
        NodoArchivo nodo = new NodoArchivo(elemento);
        informar(elemento);
        if (elemento.isDirectory() == false || puedeEntrar(elemento, profundidad) == false) {
            return nodo;
        }
        File[] hijos = listarContenido(elemento);
        if (hijos == null) {
            nodo.setSinPermiso(true);
            return nodo;
        }
        for (int i = 0; i < hijos.length; i++) {
            if (estaCancelado()) {
                break;
            }
            nodo.agregarHijo(construirNodo(hijos[i], profundidad + 1));
            informarPorcentaje(i + 1, hijos.length, profundidad);
        }
        return nodo;
    }
}