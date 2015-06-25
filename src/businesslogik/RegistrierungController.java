package businesslogik;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrierungController implements Initializable {

	// Zwischenzeitlich, bis bessere Loesung gefunden ist.
	int id = 5;

	private Benutzerkonto konto;
	private ProfilModell profilModell;

	@FXML
	private VBox vboxRoot;

	@FXML
	private HBox hboxVorname;

	@FXML
	private HBox hboxNachname;

	@FXML
	private HBox hboxNickname;

	@FXML
	private ChoiceBox<String> choiceBox;

	@FXML
	private TextField txtVorname;

	@FXML
	private TextField txtNachname;

	@FXML
	private TextField txtNickname;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtPasswort;

	/**
	 * Initialisiert die Registrierungsmaske wie folgt: Fuellt die Choice-Box
	 * mit den Strings 'Realname' und 'Nickname' (Standardauswahl ist
	 * 'Realname'). Je nach Auswahl in der Choice-Box werden unterschiedliche
	 * Textfelder und Label angezeigt.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		hboxNickname.setVisible(false);
		hboxVorname.managedProperty().bind(hboxVorname.visibleProperty());
		hboxNachname.managedProperty().bind(hboxNachname.visibleProperty());
		hboxNickname.managedProperty().bind(hboxNickname.visibleProperty());
		choiceBox.setItems(FXCollections.observableArrayList("Realname", "Nickname"));
		choiceBox.getSelectionModel().select(0);
		choiceBox.setTooltip(new Tooltip("Mit welchem Namen m�chtest du dich registrieren?"));
		choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				if (choiceBox.getValue().equals("Realname")) {
					hboxNickname.setVisible(false);
					hboxVorname.setVisible(true);
					hboxNachname.setVisible(true);
				} else {
					hboxVorname.setVisible(false);
					hboxNachname.setVisible(false);
					hboxNickname.setVisible(true);
				}
			}
		});
	}

	/**
	 * Wenn der Button 'Abbrechen' geklickt wird, schliesst die
	 * Registrierungsmaske und die Loginmaske wird geoeffnet.
	 */
	@FXML
	private void abbrechenGeklickt(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/login.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("Login");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wenn der Button 'Registrieren' geklickt wird, werden die in den
	 * Textfeldern eingegebenen Daten (je nach Auswahl in der Choice-Box)
	 * validiert und bei Erfolg ein Benutzerkonto mit diesen Daten erstellt.
	 * Anschliessend wird der Benutzer zum Hauptfenster weitergeleitet.
	 */
	@FXML
	private void registrierenGeklickt(ActionEvent event) {
		if (choiceBox.getValue().equals("Realname") && BenutzerkontoGeschuetzt.ueberpruefeReal(txtVorname.getText(), 
				txtNachname.getText(), txtEmail.getText(), txtPasswort.getText())) {

			konto = new BenutzerkontoGeschuetzt(txtEmail.getText(), txtPasswort.getText(), 
					txtVorname.getText(), txtNachname.getText(), id);

			// Hauptfenster das bk geben
			ControllerMediator.getInstance().setBenutzerkonto(konto);
			profilModell = new ProfilModell(txtEmail.getText());
			
			try {
				Datenhaltung.schreibeProfil(profilModell);
			} catch (PersistenzException e) {
				//Das fenster wird bis jetzt immer aufgerufen. Siehe Datenhaltung Zeile 202
				new CodingSessionDialog().erstellePersistenzExceptionDialog();
			}

		} else if (choiceBox.getValue().equals("Nickname") && BenutzerkontoGeschuetzt.ueberpruefeNick(txtNickname.getText(), 
				txtEmail.getText(), txtPasswort.getText())) {

			konto = new BenutzerkontoGeschuetzt(txtEmail.getText(), txtPasswort.getText(), 
					txtNickname.getText(), id);

			// Hauptfenster das bk geben
			ControllerMediator.getInstance().setBenutzerkonto(konto);
			profilModell = new ProfilModell(txtEmail.getText());
			
			try {
				Datenhaltung.schreibeProfil(profilModell);
			} catch (PersistenzException e) {
				//Das fenster wird bis jetzt immer aufgerufen. Siehe Datenhaltung Zeile 202
				new CodingSessionDialog().erstellePersistenzExceptionDialog();
			}
		}

		if (konto.getBenutzerkontoOriginal() != null) {
			((Node) (event.getSource())).getScene().getWindow().hide();
			try {
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
	}
}
