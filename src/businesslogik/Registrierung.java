package businesslogik;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class Registrierung implements Initializable {

	// Zwischenzeitlich, bis bessere Loesung gefunden ist.
	int id = 5;

	private BenutzerkontoGeschuetzt bg = new BenutzerkontoGeschuetzt();

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
	 * mit den Strings 'Realname' und 'Nickname' (Standardauswahl ist 'Realname').
	 * Je nach Auswahl in der Choice-Box werden unterschiedliche Textfelder und 
	 * Label angezeigt.
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
	 * 
	 * @throws IOException Falls login.fxml nicht geladen werden kann.
	 */
	@FXML
	private void abbrechenGeklickt(ActionEvent event) {
		try {
			((Node) (event.getSource())).getScene().getWindow().hide();
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
	 * Wenn der Button 'Registrieren' geklickt wird, wird ein Benutzerkonto mit den in den
	 * Textfeldern eingegebenen Daten (je nach Auswahl in der Choice-Box) erstellt.
	 */
	@FXML
	private void registrierenGeklickt(ActionEvent event) {
		choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				if (choiceBox.getValue().equals("Realname")) {
					erstelleReal(txtEmail.getText(), txtPasswort.getText(), txtVorname.getText(), txtNachname.getText());
				} else{
					erstelleNick(txtEmail.getText(), txtPasswort.getText(), txtNickname.getText());
				}
			}
		});
	}

	/**
	 * Erstellt ein Benutzerkonto (Nickname) mit den uebergebenen Parametern.
	 */
	private BenutzerkontoOriginal erstelleNick(String email, String pw, String name) {
		return bg.erstelleNickKonto(email, pw, name,  id);
	}

	/**
	 * Erstellt ein Benutzerkonto (Realname) mit den uebergebenen Parametern.
	 */
	private BenutzerkontoOriginal erstelleReal(String email, String pw, String vor, String nach) {
		return bg.erstelleRealKonto(email, pw, vor, nach, id);
	}
}
