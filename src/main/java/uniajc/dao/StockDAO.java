/**
 *  Clase encargada de las operaciones del Stock en la base de datos
 */
package uniajc.dao;

// Importaciones necesarias
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import uniajc.modelo.Stock;


public class StockDAO {

    // Conexión a la base de datos
    private Connection conexion;

    // Constructor que recibe la conexión
    public StockDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Registrar nuevo stock
    public boolean registrarStock(Stock stock) throws SQLException {
        boolean registrado = false;
        String sql = "{ CALL RegistrarStock(?, ?, ?) }";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setInt(1, stock.getId_Producto());
            cs.setInt(2, stock.getId_Almacenamiento());
            cs.setInt(3, stock.getCantidadDisponibleProducto());

            cs.execute();
            registrado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al registrar stock: " + e.getMessage());
        }

        return registrado;
    }

    // Actualizar un stock existente
    public boolean actualizarStock(Stock stock) throws SQLException {
        boolean actualizado = false;
        String sql = "{ CALL ActualizarStock(?, ?, ?, ?) }";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setInt(1, stock.getId_Stock());
            cs.setInt(2, stock.getId_Producto());
            cs.setInt(3, stock.getId_Almacenamiento());
            cs.setInt(4, stock.getCantidadDisponibleProducto());

            cs.execute();
            actualizado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al actualizar stock: " + e.getMessage());
        }

        return actualizado;
    }

    // Eliminar un registro de stock
    public boolean eliminarStock(int id_Stock) throws SQLException {
        boolean eliminado = false;
        String sql = "{ CALL EliminarStock(?) }";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setInt(1, id_Stock);
            cs.execute();
            eliminado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al eliminar stock: " + e.getMessage());
        }

        return eliminado;
    }

    // Listar todos los registros de stock
    public List<Stock> listarStock() throws SQLException {
        List<Stock> lista = new ArrayList<>();
        String sql = "{ CALL ListarStock() }";

        try (CallableStatement cs = conexion.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Stock s = new Stock();
                s.setId_Stock(rs.getInt("id_Stock"));
                s.setId_Producto(rs.getInt("id_Producto"));
                s.setId_Almacenamiento(rs.getInt("id_Almacenamiento"));
                s.setCantidadDisponibleProducto(rs.getInt("cantidadDisponibleProducto"));
                lista.add(s);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al listar stock: " + e.getMessage());
        }

        return lista;
    }

    // Buscar un stock por su ID
    public Stock buscarPorId(int idStock) throws SQLException {
        Stock s = null;
        String sql = "{ CALL BuscarStockPorId(?) }";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setInt(1, idStock);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    s = new Stock();
                    s.setId_Stock(rs.getInt("id_Stock"));
                    s.setId_Producto(rs.getInt("id_Producto"));
                    s.setId_Almacenamiento(rs.getInt("id_Almacenamiento"));
                    s.setCantidadDisponibleProducto(rs.getInt("cantidadDisponibleProducto"));
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al buscar stock: " + e.getMessage());
        }

        return s;
    }
}

    
