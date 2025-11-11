package uniajc.dao;

// Importaciones necesarias
import uniajc.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de realizar las operaciones del producto en la base de datos
 */
public class ProductoDAO {

    // Conexión a la base de datos
    private Connection conexion;

    // Constructor que recibe la conexión
    public ProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Crear con procedimientos almacenados
    public boolean registrarProducto(Producto producto) throws SQLException {
        String sql = "{ CALL RegistrarProducto(?, ?, ?, ?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, producto.getId_stock());
            cs.setString(2, producto.getNombre());
            cs.setDouble(3, producto.getPrecio());
            cs.setInt(4, producto.getCantidad_disponible());
            cs.executeUpdate();
            return true;
        }
    }

    // Mostrar con procediminetos almacenados
    public List<Producto> listarProductos() throws SQLException {
        String sql = "{ CALL ListarProductos() }";
        List<Producto> lista = new ArrayList<>();
        try (CallableStatement cs = conexion.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId_producto(rs.getInt("id_Producto"));
                p.setId_stock(rs.getInt("id_Stock"));
                p.setNombre(rs.getString("Nombre"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setCantidad_disponible(rs.getInt("Cantidad_Disponible"));
                lista.add(p);
            }
        }
        return lista;
    }

    // Eliminar con procediminetos almacenados
    public boolean eliminarProducto(int idProducto) throws SQLException {
        String sql = "{ CALL EliminarProducto(?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idProducto);
            cs.execute();
            return true;
        }
    }

    // Actualizar con procediminetos almacenados
    public boolean actualizarProducto(Producto producto) throws SQLException {
        String sql = "{ CALL ActualizarProducto(?, ?, ?, ?, ?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, producto.getId_producto());
            cs.setInt(2, producto.getId_stock());
            cs.setString(3, producto.getNombre());
            cs.setDouble(4, producto.getPrecio());
            cs.setInt(5, producto.getCantidad_disponible());
            cs.execute();
            return true;
        }
    }

    // Buscar Producto por ID
    public Producto buscarPorId(int idProducto) throws SQLException {
        String sql = "{ CALL BuscarProductoPorId(?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idProducto);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Producto p = new Producto();
                    p.setId_producto(rs.getInt("id_Producto"));
                    p.setId_stock(rs.getInt("id_Stock"));
                    p.setNombre(rs.getString("Nombre"));
                    p.setPrecio(rs.getDouble("Precio"));
                    p.setCantidad_disponible(rs.getInt("Cantidad_Disponible"));
                    return p;
                }
            }
        }
        return null;
    }
}
