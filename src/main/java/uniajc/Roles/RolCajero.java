package uniajc.Roles;

// Importaciones necesarias
import javax.swing.JOptionPane;

public class RolCajero extends Rol {

    // Constructor
    public RolCajero() {
        super("Cajero");
    }

    @Override
    public void mostrarPermisos() {
        JOptionPane.showMessageDialog(null, "Permisos del Cajero: Manejo de caja y procesamiento de pagos.");
    }
}