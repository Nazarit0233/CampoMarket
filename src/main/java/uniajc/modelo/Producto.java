/**
 *  Clase Producto que representa un producto en el sistema.
 */
package uniajc.modelo;

public class Producto {
    // Atributos
    private int id_producto;
    private int id_stock;
    private String nombre;
    private double precio;
    private int cantidad_disponible;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con todos los campos (excepto id autogenerado)
    public Producto(int id_stock, String nombre, double precio, int cantidad_disponible) {
        this.id_stock = id_stock;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad_disponible = cantidad_disponible;
    }

    // Constructor completo (por si se carga desde BD)
    public Producto(int id_producto, int id_stock, String nombre, double precio, int cantidad_disponible) {
        this.id_producto = id_producto;
        this.id_stock = id_stock;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad_disponible = cantidad_disponible;
    }

    // Getters y Setters
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_stock() {
        return id_stock;
    }

    public void setId_stock(int id_stock) {
        this.id_stock = id_stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad_disponible() {
        return cantidad_disponible;
    }

    public void setCantidad_disponible(int cantidad_disponible) {
        this.cantidad_disponible = cantidad_disponible;
    }
}