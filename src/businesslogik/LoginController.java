package businesslogik;

import java.io.IOException;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField pwdPasswort;

	/**
	 * Wenn der Button 'Anmelden' geklickt wird und die Daten valide sind wird
	 * die Loginmaske geschlossen und das Hauptfenster der Anwendung geladen.
	 */
	@FXML
	private void anmeldenGeklickt(ActionEvent event) {
		try {
			if (Datenhaltung.passwortRichtig(txtEmail.getText(), pwdPasswort.getText())) {
				try {
					ControllerMediator.getInstance().setBenutzerkonto(Datenhaltung.leseDB(txtEmail.getText()));
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/hauptfenster.fxml"));
					Parent root = (Parent) loader.load();
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setMaximized(true);
					stage.setOnCloseRequest(e -> {
						e.consume();
						new CodingSessionDialog().erstelleAbmeldeDialog();
					});
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (PersistenzException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wenn der Hyperlink geklickt wird, schliesst die Loginmaske und die
	 * Registrierungsmaske wird geladen.
	 */
	@FXML
	private void oeffneRegistrierungGeklickt(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/registrierung.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("Registrierung");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}