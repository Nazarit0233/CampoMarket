/**
 *  Clase encargada de las operaciones de la cuenta en la base de datos
 */
package uniajc.dao;

// Importaciones necesarias
import uniajc.modelo.Cuenta;
import uniajc.Roles.*;

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
        String sql = "{ CALL CrearCuenta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, cuenta.getNombre());
            cs.setString(2, cuenta.getCorreo_Electronico());
            cs.setString(3, cuenta.getContraseña());
            cs.setString(4, cuenta.getTelefono());
            cs.setString(5, cuenta.getRol().getNombre());

            // Campos específicos por rol
            switch (cuenta.getRol().getNombre()) {
                case "Cliente":
                    cs.setString(6, cuenta.getComprobanteIdentidad());
                    if (cuenta.getFecha_Nacimiento() != null && !cuenta.getFecha_Nacimiento().isEmpty()) {
                        cs.setDate(7, java.sql.Date.valueOf(cuenta.getFecha_Nacimiento()));
                    } else {
                        cs.setNull(7, Types.DATE);
                    }
                    cs.setNull(8, Types.VARCHAR);
                    cs.setNull(9, Types.VARCHAR);
                    cs.setNull(10, Types.VARCHAR);
                    cs.setNull(11, Types.VARCHAR);
                    cs.setNull(12, Types.VARCHAR);
                    cs.setNull(13, Types.VARCHAR);
                    cs.setNull(14, Types.VARCHAR);
                    cs.setNull(15, Types.VARCHAR);
                    break;

                case "Administrador":
                    cs.setNull(6, Types.VARCHAR);
                    cs.setNull(7, Types.DATE);
                    cs.setString(8, cuenta.getNivel_Acceso());
                    cs.setString(9, cuenta.getArea_Responsable());
                    cs.setNull(10, Types.VARCHAR);
                    cs.setNull(11, Types.VARCHAR);
                    cs.setNull(12, Types.VARCHAR);
                    cs.setNull(13, Types.VARCHAR);
                    cs.setNull(14, Types.VARCHAR);
                    cs.setNull(15, Types.VARCHAR);
                    break;

                case "Repartidor":
                    cs.setNull(6, Types.VARCHAR);
                    cs.setNull(7, Types.VARCHAR);
                    cs.setNull(8, Types.VARCHAR);
                    cs.setNull(9, Types.VARCHAR);
                    cs.setString(10, cuenta.getTurno_Trabajo());
                    cs.setString(11, cuenta.getTipo_Vehiculo());
                    cs.setString(12, cuenta.getLicencia_Conducion());
                    cs.setString(13, cuenta.getPlaca_Vehiculo());
                    cs.setNull(14, Types.VARCHAR);
                    cs.setNull(15, Types.VARCHAR);
                    break;

                case "Cajero":
                    cs.setNull(6, Types.VARCHAR);
                    cs.setNull(7, Types.VARCHAR);
                    cs.setNull(8, Types.VARCHAR);
                    cs.setNull(9, Types.VARCHAR);
                    cs.setNull(10, Types.VARCHAR);
                    cs.setNull(11, Types.VARCHAR);
                    cs.setNull(12, Types.VARCHAR);
                    cs.setString(13, cuenta.getTurno_Trabajo());
                    cs.setInt(14, cuenta.getCaja_Asignada());
                    cs.setString(15, cuenta.getForma_Pago());
                    break;

                case "Despachador":
                    cs.setNull(6, Types.VARCHAR);
                    cs.setNull(7, Types.VARCHAR);
                    cs.setNull(8, Types.VARCHAR);
                    cs.setString(9, cuenta.getArea_Responsable());
                    cs.setString(10, cuenta.getTurno_Trabajo());
                    cs.setNull(11, Types.VARCHAR);
                    cs.setNull(12, Types.VARCHAR);
                    cs.setNull(13, Types.VARCHAR);
                    cs.setNull(14, Types.VARCHAR);
                    cs.setNull(15, Types.VARCHAR);
                    break;

                default:
                    for (int i = 6; i <= 10; i++)
                        cs.setNull(i, Types.VARCHAR);
                    break;
            }
            cs.execute();
            registrado = true;

        } catch (SQLException e) {
            if (e.getMessage().contains("Rol no válido")) {
                JOptionPane.showMessageDialog(null, "El rol seleccionado no es válido.");
            } else if (e.getMessage().contains("correo electrónico ya está registrado")) {
                JOptionPane.showMessageDialog(null, "El correo ya está registrado.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al crear cuenta: " + e.getMessage());
            }
        }
        return registrado;
    }

    // Actualizar con prodeciminentos almacenados
    public boolean actualizarCuenta(Cuenta cuenta) throws SQLException {
        boolean actualizado = false;
        String sql = "{ CALL ActualizarCuenta(?, ?, ?, ?, ?, ?) }";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, cuenta.getId_Cuenta());
            cs.setString(2, cuenta.getRol().getNombre());
            cs.setString(3, cuenta.getNombre());
            cs.setString(4, cuenta.getCorreo_Electronico());
            cs.setString(5, cuenta.getContraseña());
            cs.setString(6, cuenta.getTelefono());

            cs.execute();
            actualizado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar cuenta: " + e.getMessage());
        }
        return actualizado;
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

    // Login Cuenta
    public Cuenta loginCuenta(String correo, String contrasena) {
    String sql = "{ CALL LoginCuenta(?, ?) }";

    try (CallableStatement stmt = conexion.prepareCall(sql)) {
        stmt.setString(1, correo);
        stmt.setString(2, contrasena);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Rol rol = obtenerRolDesdeNombre(rs.getString("Rol"));

            return new Cuenta(
                    rs.getInt("id_Cuenta"),
                    rol,
                    rs.getString("Nombre"),
                    rs.getString("Correo_Electronico"),
                    null, // nunca almacenamos la contraseña en memoria
                    rs.getString("Telefono"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}

    // Método auxiliar para obtener el rol desde su nombre
    private Rol obtenerRolDesdeNombre(String nombreRol) {
        switch (nombreRol) {
            case "Cliente":
                return new RolCliente();
            case "Administrador":
                return new RolAdministrador();
            case "Repartidor":
                return new RolRepartidor();
            case "Cajero":
                return new RolCajero();
            case "Despachador":
                return new RolDespachador();
            default:
                throw new IllegalArgumentException("Rol desconocido: " + nombreRol);
        }
    }
}