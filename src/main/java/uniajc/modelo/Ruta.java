/**
 *  Clase Ruta que representa una ruta de entrega en el sistema.
 */
package uniajc.modelo;

// Importaciones necesarias
import java.util.*;

public class Ruta {
    // Atributos
    private int id_ruta; // Atributo ID de la ruta
    private String destino; // Atributo Destino de la ruta
    private String salida; // Atributo Salida de la ruta
    private double distancia; // Atributo Distancia de la ruta
    private double tiempoEstimado; // Atributo Tiempo estimado de la ruta
    private List<String> pedidos; // Atributo Lista de pedidos en la ruta
    private String estado; // Atributo Estado de la ruta

    // Constructor vacío
    public Ruta() {
    }

    // Constructor con todos los campos (excepto id autogenerado)
    public Ruta(String salida, String destino, double distancia, double tiempoEstimado, List<String> pedidos,
            String estado) {
        this.salida = salida;
        this.destino = destino;
        this.distancia = distancia;
        this.tiempoEstimado = tiempoEstimado;
        this.pedidos = pedidos;
        this.estado = estado;
    }

    // Constructor completo (por si se carga desde BD)
    public Ruta(int id_ruta, String salida, String destino, double distancia, double tiempoEstimado,
            List<String> pedidos, String estado) {
        this.id_ruta = id_ruta;
        this.salida = salida;
        this.destino = destino;
        this.distancia = distancia;
        this.tiempoEstimado = tiempoEstimado;
        this.pedidos = pedidos;
        this.estado = estado;
    }

    // Agregar pedido
    public void agregarPedido(String pedido) {
        if (this.pedidos == null) {
            this.pedidos = new ArrayList<>();
        }
        this.pedidos.add(pedido);
    }

    // Eliminar pedido
    public void eliminarPedido(String pedido) {
        if (this.pedidos != null) {
            this.pedidos.remove(pedido);
        }
    }

    // Limpiar lista de pedidos
    public void clearPedidos() {
        if (this.pedidos != null) {
            this.pedidos.clear();
        }
    }

    // Calcular tiempo estimado basado en distancia y velocidad promedio
    public void calcularTiempoEstimado(double velocidadPromedio) {
        if (velocidadPromedio > 0) {
            this.tiempoEstimado = this.distancia / velocidadPromedio;
        } else {
            this.tiempoEstimado = 0;
        }
    }

    // Actualizar estado de la ruta
    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Getters y Setters
    public int getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(double tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public List<String> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<String> pedidos) {
        this.pedidos = pedidos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
