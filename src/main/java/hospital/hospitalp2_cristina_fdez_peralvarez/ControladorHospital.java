package hospital.hospitalp2_cristina_fdez_peralvarez;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;


public class ControladorHospital implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Scene escenaManteninimiento;
    private ManejarUsuario manejarUsuario;

    @FXML
    private TabPane tabs;

    @FXML
    private Tab tabFichar;

    @FXML
    private Tab tabAlta;

    @FXML
    private TextField nombreAlta;

    @FXML
    private TextField identAlta;

    @FXML
    private ChoiceBox<String> cbCargo;

    @FXML
    private Button btnAlta;

    @FXML
    private TextField identFichar;

    @FXML
    private TextField nombreFichar;

    @FXML
    private Button btnFichar;

    @FXML
    private Label labelSesion;

    static int num = 0;

    Usuario user = null;

    DateFormat dateFormat = null;

    String fechaAlta;


    @FXML
    void crearUsuario(ActionEvent event) {
        try {
            String nombre = nombreAlta.getText();
            String identif = identAlta.getText();
            String cargo = cbCargo.getSelectionModel().getSelectedItem();
            String fecha = fechaActual();
            fechaAlta = fecha;
            String fechaBaja = "31/12/2400";

            user = new Usuario(nombre, identif, cargo, fechaAlta, fechaBaja);


            manejarUsuario.crearUsuario(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Usuario creado");
            alert.setContentText("Usuario creado correctamente");
            alert.show();

            nombreAlta.setText("");
            identAlta.setText("");
            cbCargo.getSelectionModel().select(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void accFichar(ActionEvent event) throws SQLException {
        int n = manejarUsuario.estaLogeado(user);

        if(n == -1) {
            num = Integer.parseInt(identFichar.getText());
            user = ManejarUsuario.login(num);
            nombreFichar.setEditable(false);
            nombreFichar.setText(user.getNombre());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hospital Celia Viñas");
            alert.setHeaderText("Usuario " + user.getNombre());
            alert.setContentText("Ha entrado a trabajar");
            alert.show();
            dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss z");
            String date = dateFormat.format(new Date());
            labelSesion.setText("El usuario ha entrado a trabajar " + date);
            System.out.println("ComprobarLogin");

        }else{
            manejarUsuario.getUsuariosLoggeados().remove(n);
            dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss z");
            String date = dateFormat.format(new Date());
            labelSesion.setText("El usuario ha terminado de trabajar " + date);
            System.out.println("ComprobarLogout");
        }
    }


    public void cambiaraEscena2(ActionEvent event) throws IOException, SQLException {
        stage = (Stage) nombreAlta.getScene().getWindow();
        ControladorMant controladorMant = (ControladorMant) stage.getUserData();
        controladorMant.loadData();
        stage.setTitle("Mantenimiento de Empleados");
        stage.setScene(escenaManteninimiento);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] cargos = {"Médico", "Enfermero", "Auxiliar", "Otros"};
        cbCargo.setItems(FXCollections.observableArrayList(cargos));
        cbCargo.getSelectionModel().select(0);
        manejarUsuario = new ManejarUsuario();
    }

    public void setEscenaMantenimiento(Scene escenaManteninimiento) {
        this.escenaManteninimiento = escenaManteninimiento;

    }
    public String fechaActual(){
        String fecha = null;
        LocalDate date = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        fecha = dtf.format(date);
        return fecha;
    }
    @FXML
    void salir(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}