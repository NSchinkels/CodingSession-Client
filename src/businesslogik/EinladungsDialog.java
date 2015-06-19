package businesslogik;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class EinladungsDialog {
	
	protected void erstelleStartDialog(String benutzer) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Einladung");
		alert.setHeaderText("Einladung erhalten");
		alert.setContentText("Sie haben eine Einladung vom Benutzer "+benutzer+" erhalten");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			ControllerMediator.getInstance().changeCodingSession();;
		} else {
			return;
		}
	}

	public EinladungsDialog() {
	}
}