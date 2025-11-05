package uniajc.Roles;

// Importaciones necesarias
import javax.swing.JOptionPane;

public class RolCliente extends Rol {
    
    // Constructor
    public RolCliente() {
        super("Cliente");
    }

    @Override
    public void mostrarPermisos() {
        JOptionPane.showMessageDialog(null, "Permisos del Cliente: Acceso a productos y realización de compras.");
    }

}