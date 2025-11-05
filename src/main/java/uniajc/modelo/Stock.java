/**
 * Clase Stock que representa el stock de un producto en el sistema.
 */
package uniajc.modelo;

import java.util.*;

public class Stock {
    private int id_Stock; // Atributo ID del stock
    private int id_Producto; // Atributo ID del producto
    private int id_Almacenamiento; // Atributo ID del almacenamiento
    private int cantidadTotalUsada; // Atributo Cantidad total en stock
    private int capacidadMaxima; // Atributo Capacidad máxima del stock
    private int cantidadDisponibleProducto; // Atributo Cantidad disponible en stock
    private int espaciodisponible = capacidadMaxima - cantidadTotalUsada; // Atributo Espacio disponibles en el stock
    private List<Producto> productos; // Atributo Lista de productos asociados al stock

    // Constructor vacío
    public Stock() {

    }

    // Constructor con todos los campos (excepto id autogenerado)
    public Stock(int id_Producto, int id_Almacenamiento, int cantidadDisponibleProducto) {
        this.id_Producto = id_Producto;
        this.id_Almacenamiento = id_Almacenamiento;
        this.cantidadDisponibleProducto = cantidadDisponibleProducto;
    }

    // Constructor completo (por si se carga desde BD)
    public Stock(int id_Stock, int id_Producto, int id_Almacenamiento, int cantidadDisponibleProducto) {
        this.id_Stock = id_Stock;
        this.id_Producto = id_Producto;
        this.id_Almacenamiento = id_Almacenamiento;
        this.cantidadDisponibleProducto = cantidadDisponibleProducto;
    }

    // Métodos para gestionar la lista de productos
    public void agregarProducto(Producto producto) {
        if (productos == null) {
            productos = new ArrayList<>();
        }
        productos.add(producto);
    }

    // Método para eliminar un producto de la lista
    public void eliminarProducto(Producto producto) {
        if (productos != null) {
            productos.remove(producto);
        }
    }

    // Método para mostrar los productos asociados al stock
    public void mostrarProductos() {
        if (productos != null && !productos.isEmpty()) {
            for (Producto producto : productos) {
                System.out.println("Producto ID: " + producto.getId_producto() + ", Nombre: " + producto.getNombre());
            }
        } else {
            System.out.println("No hay productos asociados a este stock.");
        }
    }

    // Método para limpiar la lista de productos
    public void limpiarProductos() {
        if (productos != null) {
            productos.clear();
        }
    }

    // Método para obtener la lista de productos
    public List<Producto> obtenerProductos() {
        return productos;
    }

   

    // Getters y Setters
    public int getId_Stock() {
        return id_Stock;
    }

    public void setId_Stock(int id_Stock) {
        this.id_Stock = id_Stock;
    }

    public int getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(int id_Producto) {
        this.id_Producto = id_Producto;
    }

    public int getId_Almacenamiento() {
        return id_Almacenamiento;
    }

    public void setId_Almacenamiento(int id_Almacenamiento) {
        this.id_Almacenamiento = id_Almacenamiento;
    }

    public int getCantidadTotalUsada() {
        return cantidadTotalUsada;
    }

    public void setCantidadTotalUsada(int cantidadTotalUsada) {
        this.cantidadTotalUsada = cantidadTotalUsada;
    }

    public int getCantidadDisponibleProducto() {
        return cantidadDisponibleProducto;
    }

    public void setCantidadDisponibleProducto(int cantidad) {
        this.cantidadDisponibleProducto = cantidad;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getEspaciodisponible() {
        return espaciodisponible;
    }

    public void setEspaciodisponible(int espaciodisponible) {
        this.espaciodisponible = espaciodisponible;
    }

}
