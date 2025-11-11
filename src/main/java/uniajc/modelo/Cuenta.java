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
    private String nivel_Acceso;
    private String areaResponsable; 
    private String tipoVehiculo;
    private String licenciaConduccion;
    private String placaVehiculo;
    private int cajaAsignada;
    private String formaPago;
    private Double totalRecaudado;
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
    public Cuenta(Rol rol, String nombre, String correo_Electronico, String contraseña, String telefono) {
        this.rol = rol;
        this.nombre = nombre;
        this.correoElectronico = correo_Electronico;
        this.contraseña = contraseña;
        this.telefono = telefono;

    }

    // Constructor completo (por si se carga desde BD)
    public Cuenta(int id_Cuenta, Rol rol, String nombre, String correo_Electronico, String contraseña, String telefono) {
        this.id_Cuenta = id_Cuenta;
        this.rol = rol;
        this.nombre = nombre;
        this.correoElectronico = correo_Electronico;
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

    public String getCorreo_Electronico() {
        return correoElectronico;
    }

    public void setCorreo_Electronico(String correo_Electronico) {
        this.correoElectronico = correo_Electronico;
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

    public String getFecha_Nacimiento() {
        return fechaNacimiento;
    }

    public void setFecha_Nacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNivel_Acceso(){
        return nivel_Acceso;
    }

    public void setNivel_Acceso(String nivel_Acceso) {
        this.nivel_Acceso = nivel_Acceso;
    }

    public String getArea_Responsable() {
        return areaResponsable;
    }

    public void setArea_Responsable(String areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    public String getTipo_Vehiculo() {
        return tipoVehiculo;
    }

    public void setTipo_Vehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getLicencia_Conducion() {
        return licenciaConduccion;
    }

    public void setLicencia_Conducion(String licenciaConduccion) {
        this.licenciaConduccion = licenciaConduccion;
    }

    public String getPlaca_Vehiculo() {
        return placaVehiculo;
    }

    public void setPlaca_Vehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public int getCaja_Asignada() {
        return cajaAsignada;
    }

    public void setCaja_Asignada(int cajaAsignada) {
        this.cajaAsignada = cajaAsignada;
    }

    public String getForma_Pago() {
        return formaPago;
    }

    public void setForma_Pago(String formaPago) {
        this.formaPago = formaPago;
    }

    public double getTotal_Recaudado() {
        return totalRecaudado;
    }

    public void setTotal_Recaudado(double totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
    }

    public String getTurno_Trabajo() {
        return turnoTrabajo;
    }

    public void setTurno_Trabajo(String turnoTrabajo) {
        this.turnoTrabajo = turnoTrabajo;
    }
}