package uniajc.dao;

// Importaciones necesarias
import uniajc.modelo.Cuenta;
import uniajc.Roles.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CuentaDAO {

    // Conexion a la base de datos
    private Connection conexion;

    // Constructor que recibe la conexión
    public CuentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Métodos CRUD para la cuenta
    // Crear con procedimientos almacenados
    public boolean registrarCuenta(Cuenta cuenta) throws SQLException {
        boolean registrado = false;
        String sql = "{ CALL CrearCuenta(?, ?, ?, ?, ?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, cuenta.getRol().getNombre());
            cs.setString(2, cuenta.getNombre());
            cs.setString(3, cuenta.getCorreoElectronico());
            cs.setString(4, cuenta.getContraseña());
            cs.setString(5, cuenta.getTelefono());

            cs.execute();
            registrado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear cuenta: " + e.getMessage());
        }
        return registrado;
    }

    // Mostrar con procediminetos almacenados
    public List<Cuenta> listarCuentas() throws SQLException {
        List<Cuenta> lista = new ArrayList<>();
        String sql = "{ CALL ListarCuentas() }";
        try (CallableStatement cs = conexion.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Rol rol = obtenerRolDesdeNombre(rs.getString("rol"));
                Cuenta cuenta = new Cuenta(
                        rs.getInt("id_Cuenta"),
                        rol,
                        rs.getString("nombre"),
                        rs.getString("correoElectronico"),
                        rs.getString("contraseña"),
                        rs.getString("telefono"));
                lista.add(cuenta);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar cuentas: " + e.getMessage());
        }
        return lista;
    }

    // Eliminar con procedimientos almacenados
    public boolean eliminarCuenta(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "{ CALL EliminarCuenta(?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id);

            cs.execute();
            eliminado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cuenta: " + e.getMessage());
        }
        return eliminado;
    }

    // Actualizar con prodeciminentos almacenados
    public boolean actualizarCuenta(Cuenta cuenta) throws SQLException {
        boolean actualizado = false;
        String sql = "{ CALL ActualizarCuenta(?, ?, ?, ?, ?, ?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, cuenta.getId_Cuenta());
            cs.setString(2, cuenta.getRol().getNombre());
            cs.setString(3, cuenta.getNombre());
            cs.setString(4, cuenta.getCorreoElectronico());
            cs.setString(5, cuenta.getContraseña());
            cs.setString(6, cuenta.getTelefono());

            cs.execute();
            actualizado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar cuenta: " + e.getMessage());
        }
        return actualizado;
    }

    // Buscar Cuenta
    public Cuenta buscarPorId(int idCuenta) throws SQLException {
        Cuenta cuenta = null;
        String sql = "{ CALL BuscarCuentaPorId(?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idCuenta);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Rol rol = obtenerRolDesdeNombre(rs.getString("rol"));
                    cuenta = new Cuenta(
                            rs.getInt("id_Cuenta"),
                            rol,
                            rs.getString("nombre"),
                            rs.getString("correoElectronico"),
                            rs.getString("contraseña"),
                            rs.getString("telefono"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cuenta: " + e.getMessage());
        }
        return cuenta;
    }

    // Método auxiliar para obtener el rol desde su nombre
    private Rol obtenerRolDesdeNombre(String nombreRol) {
        switch (nombreRol) {
            case "Administrador":
                return new uniajc.Roles.RolAdministrador();
            case "Cajero":
                return new uniajc.Roles.RolCajero();
            case "Cliente":
                return new uniajc.Roles.RolCliente();
            case "Repartidor":
                return new uniajc.Roles.RolRepartidor();
            case "Despachador":
                return new uniajc.Roles.RolDespachador();
            default:
                throw new IllegalArgumentException("Rol desconocido: " + nombreRol);
        }
    }
}