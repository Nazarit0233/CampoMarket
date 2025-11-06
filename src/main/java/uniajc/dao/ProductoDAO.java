/**
 *  Clase encargada de realizar las operaciones del producto en la base de datos
 */
package uniajc.dao;

// Importaciones necesarias
import uniajc.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class ProductoDAO {
    
    // Conexión a la base de datos
    private Connection conexion;

    public ProductoDAO() {
        
    }

    // Constructor que recibe la conexión
    public ProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    // Crear con procedimientos almacenados
    public boolean registrarProducto(Producto producto) throws SQLException {
        boolean registrado = false;
        String sql = "{ CALL RegistrarProducto(?, ?, ?, ?) }";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setInt(1, producto.getId_stock());
            cs.setString(2, producto.getNombre());
            cs.setDouble(3, producto.getPrecio());
            cs.setInt(4, producto.getCantidad_disponible());

            cs.execute();
            registrado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al registrar producto: " + e.getMessage());
        }

        return registrado;
    }

    // Mostrar con procediminetos almacenados
    public List<Producto> listarProductos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "{ CALL ListarProductos() }";

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

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    // Eliminar con procediminetos almacenados
    public boolean eliminarProducto(int idProducto) throws SQLException {
        boolean eliminado = false;
        String sql = "{ CALL EliminarProducto(?) }";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setInt(1, idProducto);
            cs.execute();
            eliminado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al eliminar producto: " + e.getMessage());
        }

        return eliminado;
    }

    // Actualizar con procediminetos almacenados
    public boolean actualizarProducto(Producto producto) throws SQLException {
        boolean actualizado = false;
        String sql = "{ CALL ActualizarProducto(?, ?, ?, ?, ?) }";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setInt(1, producto.getId_producto());
            cs.setInt(2, producto.getId_stock());
            cs.setString(3, producto.getNombre());
            cs.setDouble(4, producto.getPrecio());
            cs.setInt(5, producto.getCantidad_disponible());

            cs.execute();
            actualizado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al actualizar producto: " + e.getMessage());
        }

        return actualizado;
    }

    // Buscar Producto por ID
    public Producto buscarPorId(int idProducto) throws SQLException {
        Producto p = null;
        String sql = "{ CALL BuscarProductoPorId(?) }";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setInt(1, idProducto);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    p = new Producto();
                    p.setId_producto(rs.getInt("id_Producto"));
                    p.setId_stock(rs.getInt("id_Stock"));
                    p.setNombre(rs.getString("Nombre"));
                    p.setPrecio(rs.getDouble("Precio"));
                    p.setCantidad_disponible(rs.getInt("Cantidad_Disponible"));
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al buscar producto: " + e.getMessage());
        }

        return p;
    }

}


