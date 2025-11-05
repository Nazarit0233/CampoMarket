/**
 *  Clase Pedido que representa un pedido realizado por un cliente.
 */
package uniajc.modelo;

// Importaciones necesarias
import java.sql.Timestamp;

public class Pedido {
    // Atributos
    private int id_Pedido; // Atributo ID del pedido
    private int id_Cliente; // Atributo ID del cliente
    private int id_Estado_Pedido; // Atributo ID del estado del pedido
    private Timestamp fechaPedido;// Atributo Fecha del pedido
    private double totalPedido; // Atributo Total del pedido
    private String metodoPago; // Atributo Método de pago
    private String direccionEntrega; // Atributo Dirección de entrega

    
    // Constructor vacío
    public Pedido() {
    }

    // Constructor con todos los campos (excepto id autogenerado)
    public Pedido(
            int id_Cliente,
            int id_Estado_Pedido,
            Timestamp fechaPedido,
            double totalPedido,
            String metodoPago,
            String direccionEntrega) {

        this.id_Cliente = id_Cliente;
        this.id_Estado_Pedido = id_Estado_Pedido;
        this.fechaPedido = new Timestamp(System.currentTimeMillis());
        this.totalPedido = totalPedido;
        this.metodoPago = metodoPago;
        this.direccionEntrega = direccionEntrega;
    }

    // Constructor completo (por si se carga desde BD)
    public Pedido(
            int id_Pedido,
            int id_Cliente,
            int id_Estado_Pedido,
            Timestamp fechaPedido,
            double totalPedido,
            String metodoPago,
            String direccionEntrega) {

        this.id_Pedido = id_Pedido;
        this.id_Cliente = id_Cliente;
        this.id_Estado_Pedido = id_Estado_Pedido;
        this.fechaPedido = fechaPedido;
        this.totalPedido = totalPedido;
        this.metodoPago = metodoPago;
        this.direccionEntrega = direccionEntrega;
    }

    // Getters y Setters
    public int getid_Pedido() {
        return id_Pedido;
    }

    public void setid_Pedido(int id_Pedido) {
        this.id_Pedido = id_Pedido;
    }

    public int getid_Cliente() {
        return id_Cliente;
    }

    public void setid_Cliente(int id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public int getid_Estado_Pedido() {
        return id_Estado_Pedido;
    }

    public void setid_Estado_Pedido(int id_Estado_Pedido) {
        this.id_Estado_Pedido = id_Estado_Pedido;
    }

    public Timestamp getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Timestamp fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }
}