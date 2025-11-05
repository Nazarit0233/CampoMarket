/**
 *  Clase Carrito que representa un carrito de compras en el sistema.
 */
package uniajc.modelo;

// Importaciones necesarias
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    // Atributos
    private int id_Cliente; // Atributo ID del cliente
    private List<ItemCarrito> items; // Atributo Lista de ítems en el carrito
    private double total; // Atributo Total del carrito
    private LocalDateTime fechaCreacion; // Atributo Fecha de creación del carrito

    // Constructor vacío
    public Carrito() {
    }

    // Constructor con id_Cliente
    public Carrito(int id_Cliente) {
        this.id_Cliente = id_Cliente;
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor con todos los campos
    public Carrito(int id_Cliente, List<ItemCarrito> items, double total, LocalDateTime fechaCreacion) {
        this.id_Cliente = id_Cliente;
        this.items = items;
        this.total = total;
        this.fechaCreacion = fechaCreacion;
    }

    // Agregar producto
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getId_producto() == producto.getId_producto()) {
                item.setCantidad(item.getCantidad() + cantidad);
                calcularTotal();
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad, producto.getPrecio()));
        calcularTotal();
    }

    // Eliminar producto
    public void eliminarProducto(int idProducto) {
        items.removeIf(item -> item.getProducto().getId_producto() == idProducto);
        calcularTotal();
    }

    // Calcular total
    public void calcularTotal() {
        total = 0;
        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }
    }

    // Vaciar carrito
    public void vaciarCarrito() {
        items.clear();
        total = 0;
    }

    // Getters y Setters
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId_Cliente() {
        return id_Cliente;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
