package uniajc.Roles;

// Importaciones necesarias
import javax.swing.JOptionPane;

public class RolAdministrador extends Rol {

    // Constructor
    public RolAdministrador() {
        super("Administrador");
    }

    @Override
    public void mostrarPermisos() {
        JOptionPane.showMessageDialog(null, "Permisos del Administrador: Acceso completo al sistema.");
    }
}