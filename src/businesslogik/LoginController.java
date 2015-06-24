package businesslogik;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


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
				ControllerMediator.getInstance().setBenutzerkonto(Datenhaltung.leseDB(txtEmail.getText()));
				
				((Node) (event.getSource())).getScene().getWindow().hide();
				ControllerMediator.getInstance().neuesHauptfenster();
			}
		} catch(PersistenzException e) {
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
		ControllerMediator.getInstance().neueRegistrierungsMaske();
	}
}