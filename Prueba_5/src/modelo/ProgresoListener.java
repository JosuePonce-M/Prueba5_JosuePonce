
package modelo;

// Interfaz para que los recorridos infromen su avance
public interface ProgresoListener {

    void avanzar(String ruta);

    void porcentaje(int porcentaje);

    boolean estaCancelado();
}