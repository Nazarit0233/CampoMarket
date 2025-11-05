package uniajc.Roles;

public abstract class Rol {
    
    // Atributo común
    protected String nombre;

    // Constructor
    public Rol(String nombre) {
        this.nombre = nombre;
    }

    // Getter del nombre del rol
    public String getNombre() {
        return nombre;
    }

    // Método que cada rol implementará de forma distinta
    public abstract void mostrarPermisos();
}