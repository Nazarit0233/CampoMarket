/**
 *  Clase Cuenta que representa una cuenta de usuario en el sistema.
 */
package uniajc.modelo;

// Importaciones necesarias
import javax.swing.JOptionPane;
import uniajc.Roles.Rol;

public class Cuenta {
    // Atributos
    private int id_Cuenta; // Atributo ID de la cuenta
    private Rol rol; // Atributo Rol de la cuenta
    private String nombre; // Atributo Nombre de usuario
    private String correoElectronico; // Atributo Correo electrónico
    private String contraseña; // Atributo Contraseña
    private String telefono; // Atributo Teléfono
    // Datos específicos opcionales por rol
    private String comprobanteIdentidad; 
    private String fechaNacimiento;
    private String areaResponsable; 
    private String tipoVehiculo;
    private String turnoTrabajo;

    // Constructor vacío
    public Cuenta() {
    }

    public Cuenta(String rol, String nombre, String contraseña) {
        this.rol = null; // Asignar el rol adecuadamente según tu lógica
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    // Constructor con todos los campos (excepto id autogenerado)
    public Cuenta(Rol rol, String nombre, String correoElectronico, String contraseña, String telefono) {
        this.rol = rol;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.contraseña = contraseña;
        this.telefono = telefono;

    }

    // Constructor completo (por si se carga desde BD)
    public Cuenta(int id_Cuenta, Rol rol, String nombre, String correoElectronico, String contraseña, String telefono) {
        this.id_Cuenta = id_Cuenta;
        this.rol = rol;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.contraseña = contraseña;
        this.telefono = telefono;

    }

    // Método para mostrar la información de la cuenta
    public void mostrarInfoCuenta() {
        JOptionPane.showMessageDialog(null, "ID cuenta: " + id_Cuenta +
                "\nUsuario: " + nombre +
                "\nRol: " + rol.getNombre() +
                "\nCorreo Electrónico: " + correoElectronico +
                "\nTeléfono: " + telefono);

        rol.mostrarPermisos();
    }

    // Getters y Setters
    public int getId_Cuenta() {
        return id_Cuenta;
    }

    public void setId_Cuenta(int id_Cuenta) {
        this.id_Cuenta = id_Cuenta;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getComprobanteIdentidad() {
        return comprobanteIdentidad;
    }

    public void setComprobanteIdentidad(String comprobanteIdentidad) {
        this.comprobanteIdentidad = comprobanteIdentidad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(String areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getTurnoTrabajo() {
        return turnoTrabajo;
    }

    public void setTurnoTrabajo(String turnoTrabajo) {
        this.turnoTrabajo = turnoTrabajo;
    }
}