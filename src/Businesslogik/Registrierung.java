package Businesslogik;

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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Registrierung implements Initializable {
	
	/*
	 * Der Code den Philipp geschrieben hat, wurde komplett in die Proxy-Klasse uebertragen.
	 * Dort findet die Ueberpruefung statt, und es wird dann die jeweilige Kontoklasse erstellt.
	 */
	
	private BenutzerkontoGeschuetzt bg = new BenutzerkontoGeschuetzt();
	private ArrayList<BenutzerkontoOriginal> benutzerliste = new ArrayList<BenutzerkontoOriginal>();
	
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
	
	/*
	 * Initialisiert die Registrierungsmaske wie folgt:
	 * Fuellt die Choice-Box mit den Strings 'Realname' und 'Nickname'. Standardauswahl ist 'Name'.
	 * Fuegt einen ChangeListener hinzu, welcher je nach Auswahl in der Choice-Box unterschiedliche 
	 * Elemente hinzufuegt.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		hboxNickname.setVisible(false);
		hboxVorname.managedProperty().bind(hboxVorname.visibleProperty());
		hboxNachname.managedProperty().bind(hboxNachname.visibleProperty());
		hboxNickname.managedProperty().bind(hboxNickname.visibleProperty());
		choiceBox.setItems(FXCollections.observableArrayList("Realname", "Nickname"));
		choiceBox.getSelectionModel().select(0);
		choiceBox.setTooltip(new Tooltip("Mit welchem Namen möchtest du dich registrieren?"));
		choiceBox.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue){
				if(choiceBox.getValue().equals("Realname")){
					hboxNickname.setVisible(false);
					hboxVorname.setVisible(true);
					hboxNachname.setVisible(true);
				} else{
					hboxVorname.setVisible(false);
					hboxNachname.setVisible(false);
					hboxNickname.setVisible(true);
				}
			}
		});
	}
	
	/**
	 * Schliesst die Registrierungsmaske und oeffnet die Loginmaske.
	 * IOException wird gefangen, falls login.fxml nicht geladen werden kann.
	 */
	@FXML
	private void abbrechenGeklickt(ActionEvent event){
		try{
			((Node) (event.getSource())).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/login.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("Login");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Schliesst die Registrierungsmaske und oeffnet das Hauptfenster der CodingSession.
	 */
	@FXML
	private void registrierenGeklickt(ActionEvent event){
		
	}
	
	// Pruefung findet in Proxy-Klasse statt
	private BenutzerkontoOriginal erstelleNick(String email, String pw,
			String name) {
		return bg.erstelleNickKonto(email, pw, name, this.benutzerliste);
	}

	// Pruefung findet in Proxy-Klasse statt
	private BenutzerkontoOriginal erstelleReal(String email, String pw,
			String vor, String nach) {
		return bg.erstelleRealKonto(email, pw, vor, nach, benutzerliste);
	}
}
