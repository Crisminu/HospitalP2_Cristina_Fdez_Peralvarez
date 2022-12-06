package hospital.hospitalp2_cristina_fdez_peralvarez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try{
            FXMLLoader fxmlPrincipal = new FXMLLoader(Main.class.getResource("inicio.fxml"));
            Scene escenaPrincipal= new Scene(fxmlPrincipal.load(), 520,540);

            FXMLLoader fxmlMantenimiento = new FXMLLoader(Main.class.getResource("mantenimiento.fxml"));
            Scene escenaManteninimiento = new Scene(fxmlMantenimiento.load(),620,540);

            ControladorHospital controladorHospital = fxmlPrincipal.getController();
            controladorHospital.setEscenaMantenimiento(escenaManteninimiento);

            ControladorMant controladorMant = fxmlMantenimiento.getController();
            controladorMant.setEscenaPrincipal(escenaPrincipal);

            stage.setUserData(controladorMant);

            stage.setTitle("Hospital Celia Viñas");
            Image image = new Image("file:src/main/resources/logocelia.png");
            stage.getIcons().add(image);
            stage.setScene(escenaPrincipal);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}