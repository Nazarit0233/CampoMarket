package uniajc.controlador;

// Importaciones necesarias
import uniajc.modelo.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoControlador {

    private List<ItemCarrito> carrito;

    public CarritoControlador() {
        carrito = new ArrayList<>();
    }

    /**
     * Agrega un producto al carrito.
     * Si el producto ya existe, suma la cantidad.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId_producto() == producto.getId_producto()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return; // ya se actualizó, no agregamos otro
            }
        }
        carrito.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un producto del carrito por su ID.
     */
    public void eliminarProducto(int idProducto) {
        carrito.removeIf(item -> item.getProducto().getId_producto() == idProducto);
    }

    /**
     * Calcula el total del carrito.
     */
    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : carrito) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Devuelve todos los ítems del carrito.
     */
    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(carrito);
    }

    /**
     * Vacía el carrito.
     */
    public void vaciarCarrito() {
        carrito.clear();
    }

    /**
     * Modifica la cantidad de un producto existente en el carrito.
     * Si la cantidad nueva es 0 o menor, elimina el producto.
     */
    public void modificarCantidad(int idProducto, int nuevaCantidad) {
        for (int i = 0; i < carrito.size(); i++) {
            ItemCarrito item = carrito.get(i);
            if (item.getProducto().getId_producto() == idProducto) {
                if (nuevaCantidad <= 0) {
                    carrito.remove(i);
                } else {
                    item.setCantidad(nuevaCantidad);
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Producto no encontrado en el carrito.");
    }

    /**
     * Reemplaza un producto en un ítem del carrito.
     */
    public void reemplazarProducto(int idActual, Producto nuevoProducto, int nuevaCantidad) {
        for (int i = 0; i < carrito.size(); i++) {
            ItemCarrito item = carrito.get(i);
            if (item.getProducto().getId_producto() == idActual) {
                carrito.set(i, new ItemCarrito(nuevoProducto, nuevaCantidad));
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Producto a reemplazar no encontrado.");
    }

}
