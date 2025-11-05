/**
 *  Clase ItemCarrito que representa un ítem en el carrito de compras.
 */
package uniajc.modelo;

public class ItemCarrito {
    // Atributos
    private Producto producto; // Atributo Producto
    private int cantidad; // Atributo Cantidad
    private double precioUnitario; // Atributo Precio unitario

    // Constructor
    public ItemCarrito(Producto producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
