package businesslogik;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class ProfilbearbeitungController implements Initializable{

	//Regulaere Ausdruecke fuer die Eingabevalidierung
	private final String vornameRegex = "^$|[a-zA-Z]{3,20}";
	private final String nachnameRegex = "^$|[a-zA-Z]{3,20}";
	private final String nicknameRegex = "^$|[a-zA-Z][\\w_-]{3,25}";
	private final String geburtsdatumRegex = "$|";
	private final String geburtsortRegex= "^$|[a-zA-Z-\\s]{3,20}";
	private final String wohnortRegex = "^$|[a-zA-Z-\\s]{3,20}";
	private final String aktuellerJobRegex = "^$|[a-zA-Z\\s]{3,100}";
	private final String pKenntnisseRegex = "^$|[a-zA-Z0-9#+-/.,!\\s]{3,100}";
	private final String passwortRegex = "^$|[a-zA-Z0-9!§$%&/()=?@#^+-_*~'\"\\s]{8,25}";
	
	@FXML
	private HBox hboxVorname;
	
	@FXML
	private HBox hboxNachname;
	
	@FXML
	private HBox hboxNickname;
	
	@FXML
	private ChoiceBox<String> choiceBox;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		hboxVorname.managedProperty().bind(hboxVorname.visibleProperty());
		hboxNachname.managedProperty().bind(hboxNachname.visibleProperty());
		hboxNickname.managedProperty().bind(hboxNickname.visibleProperty());
		choiceBox.setItems(FXCollections.observableArrayList("Weiblich", "Männlich"));
		choiceBox.setTooltip(new Tooltip("Wähle dein Geschlecht"));
	}
	
	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}
	
	@FXML
	public void aenderungenSpeichernGeklickt(ActionEvent event){
		
	}
	
	@FXML
	public void zurueckZumProfilGeklickt(ActionEvent event){
		ControllerMediator.getInstance().neuesProfil();
	}
}
