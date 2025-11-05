package uniajc.Roles;

// Importaciones necesarias
import javax.swing.JOptionPane;

public class RolDespachador extends Rol {
    
    // Constructor
    public RolDespachador() {
        super("Despachador");
    }

    @Override
    public void mostrarPermisos() {
        JOptionPane.showMessageDialog(null, "Permisos del Despachador: Gestión de envíos y entregas.");
    }
    
}