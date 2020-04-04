package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertEmitter {
    public static void emit(String message, AlertType type) {
        Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle("Atenção");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
