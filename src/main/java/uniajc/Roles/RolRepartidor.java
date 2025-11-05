package uniajc.Roles;

// Importaciones necesarias
import javax.swing.JOptionPane;

public class RolRepartidor extends Rol {
    
    // Constructor
    public RolRepartidor() {
        super("Repartidor");
    }

    @Override
    public void mostrarPermisos() {
        JOptionPane.showMessageDialog(null, "Permisos del Repartidor: Entrega de productos a los clientes.");
    }
    
}