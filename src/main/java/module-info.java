module hospital.hospitalp2_cristina_fdez_peralvarez {
    requires javafx.controls;
    requires javafx.fxml;


    opens hospital.hospitalp2_cristina_fdez_peralvarez to javafx.fxml;
    exports hospital.hospitalp2_cristina_fdez_peralvarez;
}