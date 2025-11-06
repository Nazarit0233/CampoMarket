// ClienteController.java
package uniajc.controlador;

import uniajc.modelo.*;
import uniajc.dao.*;
import uniajc.db.ConexionDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ClienteControlador {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colProducto;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Void> colAgregar;

    @FXML private TableView<ItemCarrito> tablaCarrito;
    @FXML private TableColumn<ItemCarrito, String> colCarritoProducto;
    @FXML private TableColumn<ItemCarrito, Integer> colCarritoCantidad;
    @FXML private TableColumn<ItemCarrito, Double> colCarritoSubtotal;
    @FXML private TableColumn<ItemCarrito, Void> colCarritoAcciones;

    @FXML private Button btnConfirmar;

    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private ObservableList<ItemCarrito> carrito = FXCollections.observableArrayList();

    private ProductoDAO productoDAO;

    @FXML
    public void initialize() {
        // Inicializar DAO con la conexión a la base de datos y cargar productos
        try {
            java.sql.Connection conexion = ConexionDatabase.getConnection();
            productoDAO = new ProductoDAO(conexion);
            productos.addAll(productoDAO.listarProductos());
        } catch (java.sql.SQLException ex) {
            ex.printStackTrace(); // o usa un logger
            showAlert("Error", "No se pudieron cargar los productos: " + ex.getMessage());
            // opcional: return; // si no quieres continuar la inicialización
        }
        

        // Configurar tabla de productos
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tablaProductos.setItems(productos);

        // Agregar botones "Agregar" a productos
        agregarBotonesProductos();

        // Configurar tabla carrito
        colCarritoProducto.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getProducto().getNombre()
        ));
        colCarritoCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCarritoSubtotal.setCellValueFactory(data -> new javafx.beans.property.ReadOnlyObjectWrapper<>(
            data.getValue().getSubtotal()
        ));
        tablaCarrito.setItems(carrito);

        agregarBotonesCarrito();

        // Confirmar pedido
        btnConfirmar.setOnAction(e -> {
            if(carrito.isEmpty()) {
                showAlert("Carrito vacío", "Agrega productos antes de confirmar el pedido.");
            } else {
                showAlert("Pedido confirmado", "Se ha realizado el pedido correctamente.");
                carrito.clear();
            }
        });
    }

    private void agregarBotonesProductos() {
        Callback<TableColumn<Producto, Void>, TableCell<Producto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Producto, Void> call(final TableColumn<Producto, Void> param) {
                final TableCell<Producto, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Agregar");
                    {
                        btn.setOnAction(e -> {
                            Producto p = getTableView().getItems().get(getIndex());
                            agregarAlCarrito(p);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : btn);
                    }
                };
                return cell;
            }
        };
        colAgregar.setCellFactory(cellFactory);
    }

    private void agregarAlCarrito(Producto p) {
        for(ItemCarrito item : carrito) {
            if(item.getProducto().getId_producto() == p.getId_producto()) {
                item.setCantidad(item.getCantidad() + 1);
                tablaCarrito.refresh();
                return;
            }
        }
        carrito.add(new ItemCarrito(p, 1));
    }

    private void agregarBotonesCarrito() {
        Callback<TableColumn<ItemCarrito, Void>, TableCell<ItemCarrito, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ItemCarrito, Void> call(final TableColumn<ItemCarrito, Void> param) {
                final TableCell<ItemCarrito, Void> cell = new TableCell<>() {
                    private final Button btnIncrement = new Button("+");
                    private final Button btnDecrement = new Button("-");
                    private final Button btnReemplazar = new Button("Reemplazar");
                    private final HBox pane = new HBox(5, btnIncrement, btnDecrement, btnReemplazar);

                    {
                        btnIncrement.setOnAction(e -> {
                            ItemCarrito item = getTableView().getItems().get(getIndex());
                            item.setCantidad(item.getCantidad() + 1);
                            tablaCarrito.refresh();
                        });

                        btnDecrement.setOnAction(e -> {
                            ItemCarrito item = getTableView().getItems().get(getIndex());
                            if(item.getCantidad() > 1) {
                                item.setCantidad(item.getCantidad() - 1);
                            } else {
                                carrito.remove(item);
                            }
                            tablaCarrito.refresh();
                        });

                        btnReemplazar.setOnAction(e -> {
                            ItemCarrito item = getTableView().getItems().get(getIndex());
                            ChoiceDialog<Producto> dialog = new ChoiceDialog<>(productos.get(0), productos);
                            dialog.setTitle("Reemplazar Producto");
                            dialog.setHeaderText("Selecciona un nuevo producto para reemplazar:");
                            dialog.setContentText("Producto:");
                            dialog.showAndWait().ifPresent(nuevo -> {
                                item.setCantidad(1);
                                item.getProducto().setNombre(nuevo.getNombre());
                                item.getProducto().setPrecio(nuevo.getPrecio());
                                tablaCarrito.refresh();
                            });
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : pane);
                    }
                };
                return cell;
            }
        };
        colCarritoAcciones.setCellFactory(cellFactory);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
