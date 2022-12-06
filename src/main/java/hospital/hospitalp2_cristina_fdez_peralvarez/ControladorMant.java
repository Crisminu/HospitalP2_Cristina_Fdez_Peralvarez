package hospital.hospitalp2_cristina_fdez_peralvarez;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ControladorMant implements Initializable {
    private Stage stage;
    private ManejarUsuario manejarUsuario;
    private Scene escenaPrincipal;
    @FXML
    private TableView<Usuario> tabla;
    @FXML
    private TableColumn columId;
    @FXML
    private TableColumn columNombre;
    @FXML
    private TableColumn columCargo;
    @FXML
    private TableColumn columAlta;
    @FXML
    private TableColumn columBaja;
    @FXML
    private ObservableList<Usuario> obUsuarios;

    TextField textNombre = null;
    TextField textCargo = null;
    TextField textNum = null;
    TextField bajaFecha = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.columId.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        this.columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.columCargo.setCellValueFactory(new PropertyValueFactory("cargo"));
        this.columAlta.setCellValueFactory(new PropertyValueFactory("fechaAlta"));
        this.columBaja.setCellValueFactory(new PropertyValueFactory("fechaBaja"));


        manejarUsuario = new ManejarUsuario();
        obUsuarios = FXCollections.observableArrayList(manejarUsuario.getUsuarios());
        tabla.setItems(obUsuarios);

    }

    void loadData() {
        obUsuarios = FXCollections.observableArrayList(manejarUsuario.getUsuarios());
        tabla.setItems(obUsuarios);
        tabla.refresh();

    }

    public void cambiaraEscena1(ActionEvent event) throws IOException {
        stage = (Stage) tabla.getScene().getWindow();
        stage.setTitle("Hospital Celia Viñas");
        stage.setScene(escenaPrincipal);
        stage.show();
    }

    public void setEscenaPrincipal(Scene escenaPrincipal) {
        this.escenaPrincipal = escenaPrincipal;
    }

    @FXML
    void displayContext(MouseEvent event) {

    }

    @FXML
    void modificarSel(ActionEvent event) {
        Usuario seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No ha seleccionado ningún usuario");
            alert.show();
        }
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Modificar datos");


        Label label1 = new Label("Nombre");
        Label label2 = new Label("Cargo");
        textNombre = new TextField(null);
        textCargo = new TextField(null);


        Button button1 = new Button("Guardar");

        button1.setOnAction(e ->
        {
            try {
                modificarUsuario(seleccionado);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            popupwindow.close();
        });

        VBox layout = new VBox(10);


        layout.getChildren().addAll(label1, textNombre, label2, textCargo, button1);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 300, 250);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }

    /*@FXML
    void bajaSele(ActionEvent event) {
        Usuario seleccionado = tabla.getSelectionModel().getSelectedItem();
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.WINDOW_MODAL);
        popupwindow.setTitle("Modificar datos");


        Label label1 = new Label("¿Quieres dar de baja al usuario?");
        Label label2 = new Label("Fecha Baja");

        bajaFecha = new TextField(null);

        Button button1 = new Button("SI");
        Button button2 = new Button("NO");

        button1.setOnAction(e ->
        {
            try {
                darDeBaja(seleccionado);
            } catch (SQLException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BAJA DE USUARIO");
            alert.setHeaderText("El usuario seleccionado ha sido dado de baja correctamente");
            alert.show();
            tabla.refresh();
            popupwindow.close();
        });


        VBox layout = new VBox(10);


        layout.getChildren().addAll(label1, label2, bajaFecha, button1, button2);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 300, 250);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();
    }*/

    private void modificarUsuario(Usuario seleccionado) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/hospital/hospitalp2_cristina_fdez_peralvarez/hospital.db");
        Statement stmt = conn.createStatement();
        String nombre = textNombre.getText();
        String cargo = textCargo.getText();
        String sql = "UPDATE USUARIO SET NOMBRE = '" + nombre + "',TIPO = '" + cargo + "' WHERE NUMPERS="+ seleccionado.getNumeroPersonal() + ";";
        stmt.executeUpdate(sql);
        manejarUsuario.recargarListaUsuarios();

        obUsuarios = FXCollections.observableArrayList(manejarUsuario.getUsuarios());
        tabla.setItems(obUsuarios);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Usuario modificado");
        alert.setContentText("Usuario modificado correctamente");
        alert.show();
        tabla.refresh();
    }
   /* private void darDeBaja(Usuario seleccionado) throws SQLException, ParseException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/hospital/hospitalp2_cristina_fdez_peralvarez/hospital.db");
        Statement stmt = conn.createStatement();
        String fechaBaja = bajaFecha.getText();
        Date fechaBajaDate = new SimpleDateFormat("yyyy/mm/dd").parse(fechaBaja);
        String sql = "UPDATE USUARIO SET FECHABAJA = '" + fechaBajaDate + "' WHERE NUMPERS="+ seleccionado.getNumeroPersonal() + ";";
        stmt.executeUpdate(sql);
        manejarUsuario.recargarListaUsuarios();

        obUsuarios = FXCollections.observableArrayList(manejarUsuario.getUsuarios());
        tabla.setItems(obUsuarios);
    }*/

}
