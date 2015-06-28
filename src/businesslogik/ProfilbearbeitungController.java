
package businesslogik;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import Persistence.Datenhaltung;
import Persistence.PersistenzException;

public class ProfilbearbeitungController implements Initializable{

	//Regulaere Ausdruecke fuer die Eingabevalidierung
	private final String vornameRegex = "^[a-zA-Z]{3,20}$";
	private final String nachnameRegex = "^[a-zA-Z]{3,20}$";
	private final String nicknameRegex = "^[a-zA-Z][\\w_-]{3,25}$";
	private final String geburtsdatumRegex = "^([01][0-9]).([01][0-9]).([12][089][0-9][0-9])$";
	private final String geburtsortRegex= "^[a-zA-Z-\\s]{3,20}$";
	private final String wohnortRegex = "^[a-zA-Z-\\s]{3,20}$";
	private final String aktuellerJobRegex = "^[a-zA-Z\\s]{3,100}$";
	private final String pkenntnisseRegex = "^[a-zA-Z0-9#+-/.,!\\s]{3,100}$";
	private final String passwortRegex = "^[a-zA-Z0-9!§$%&/()=?@#^+-_*~'\"\\s]{8,25}$";
	
	ProfilModell profilModell = new ProfilModell();
	Benutzerkonto benutzerkonto = ControllerMediator.getInstance().getBenutzerkonto();
	
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
	private TextField txtGeburtsdatum;
	
	@FXML
	private TextField txtGeburtsort;
	
	@FXML
	private TextField txtWohnort;
	
	@FXML
	private TextField txtAktuellerJob;
	
	@FXML
	private TextField txtProgrammierkenntnisse;
	
	@FXML
	private PasswordField pwdAltesPasswort;
	
	@FXML
	private PasswordField pwdNeuesPasswort;
	
	@FXML
	private PasswordField pwdPasswortBestaetigung;
	
	/**
	 * Die Profilbearbeitung zeigt je nach Art des Benutzerskontos 
	 * ein Vor- und Nachname Textfeld oder ein Nickname Textfeld an.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		hboxVorname.managedProperty().bind(hboxVorname.visibleProperty());
		hboxNachname.managedProperty().bind(hboxNachname.visibleProperty());
		hboxNickname.managedProperty().bind(hboxNickname.visibleProperty());
		choiceBox.setItems(FXCollections.observableArrayList("Weiblich", "Männlich"));
		choiceBox.setTooltip(new Tooltip("Wähle dein Geschlecht"));
		
		if(benutzerkonto instanceof BenutzerkontoRealname) {
			hboxNickname.setVisible(false);
			hboxVorname.setVisible(true);
			hboxNachname.setVisible(true);
		} else {
			hboxNickname.setVisible(true);
			hboxVorname.setVisible(false);
			hboxNachname.setVisible(false);
		}
	}
	
	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}

	/**
	 * Speichert alle Daten, die in den ausgefuellten Textfeldern stehen, in dem jeweiligen
	 * Profil des Benutzers. Bei einem Fehlschlag werden entsprechende Fehlermeldungen
	 * angezeigt.
	 */
	@FXML
	public void aenderungenSpeichernGeklickt(ActionEvent event){
		try {
			profilModell = Datenhaltung.leseProfil(benutzerkonto.getEmail());
		} catch (PersistenzException e) {
			e.printStackTrace();
		}
		
		if(benutzerkonto instanceof BenutzerkontoRealname) {
			if(!istLeer(txtVorname.getText())) {
				if(txtVorname.getText().matches(vornameRegex)) {
					benutzerkonto.setVorname(txtVorname.getText());
				} else {
					new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiger Vorname", 
							"Bitte gebe einen gültigen Vornamen ein!");
				}
			}
			
			if(!istLeer(txtNachname.getText())) {
				if(txtNachname.getText().matches(nachnameRegex)) {
					benutzerkonto.setNachname(txtNachname.getText());
				} else {
					new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiger Nachname", 
							"Bitte gebe einen gültigen Nachnamen ein!");
				}
			}
		} else {
			if(!istLeer(txtNickname.getText())) {
				if(txtNickname.getText().matches(nicknameRegex)) {
					benutzerkonto.setNickname(txtNickname.getText());
				} else {
					new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiger Nickname", 
							"Bitte gebe einen gültigen Nicknamen ein!");
				}
			}
		}
		
		if(choiceBox.getValue() != null){
			profilModell.setGeschlecht(choiceBox.getValue());
		}

		if(!istLeer(txtGeburtsdatum.getText())) {
			if(txtGeburtsdatum.getText().matches(geburtsdatumRegex)) {
				profilModell.setGeburtsdatum(txtGeburtsdatum.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiges Geburtsdatum", 
						"Bitte gebe ein gültiges Geburtsdatum ein!");
			}	
		}
		
		if(!istLeer(txtGeburtsort.getText())) {
			if(txtGeburtsort.getText().matches(geburtsortRegex)) {
				profilModell.setGeburtsort(txtGeburtsort.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiger Geburtsort",
						"Bitte gebe einen gültigen Geburtsort ein!");
			}
		}
		
		if(!istLeer(txtWohnort.getText())) {
			if(txtWohnort.getText().matches(wohnortRegex)) {
				profilModell.setWohnort(txtWohnort.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiger Wohnort", 
						"Bitte gebe einen gültigen Wohnort ein!");
			}
		}
		
		if(!istLeer(txtAktuellerJob.getText())) {
			if(txtAktuellerJob.getText().matches(aktuellerJobRegex)) {
				profilModell.setAktuellerJob(txtAktuellerJob.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiger aktueller Job",
						"Bitte gebe einen gültigen aktuellen Job ein!");
			}		
		}

		if(!istLeer(txtProgrammierkenntnisse.getText())) {
			if(txtProgrammierkenntnisse.getText().matches(pkenntnisseRegex)) {
				profilModell.setProgrammierkenntnisse(txtProgrammierkenntnisse.getText());
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültige Programmierkenntnisse", 
						"Bitte gebe nur gültige Programmiersprachen ein!");
			}
			
		}
		
		if(!istLeer(pwdAltesPasswort.getText()) && !istLeer(pwdNeuesPasswort.getText()) && 
		   !istLeer(pwdPasswortBestaetigung.getText())) {
			if(benutzerkonto.getPasswort().equals(pwdAltesPasswort.getText())) {
				if(pwdNeuesPasswort.getText().matches(passwortRegex) && 
				   pwdPasswortBestaetigung.getText().matches(passwortRegex)) {
					if(pwdNeuesPasswort.getText().equals(pwdPasswortBestaetigung.getText())) {
						benutzerkonto.setPasswort(pwdNeuesPasswort.getText());
					} else {
						new CodingSessionDialog().erstelleFehlermeldungDialog("Passwort Widerspruch", 
								"Die eingegebenen neuen Passwörter stimmen nicht überein!");
					}
				} else {
					new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiges Passwort", 
							"Bitte gebe ein gültiges Passwort ein!");
				}		
			} else {
				new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiges Passwort", 
						"Das alte Passwort stimmt nicht mit dem des Benutzers überein!");
			}
		}
		
		try {
			Datenhaltung.updateDB((BenutzerkontoOriginal) benutzerkonto);
			Datenhaltung.updateProfil(profilModell);
		} catch (PersistenzException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Fuehrt den Benutzer zurueck zu seinem Profil.
	 */
	@FXML
	public void zurueckZumProfilGeklickt(ActionEvent event) {
		ControllerMediator.getInstance().neuesProfil();
	}
	
	/**
	 * Prueft ob der uebergebene String leer ist.
	 * @param text - String, der ueberprueft werden soll.
	 */
	private boolean istLeer(String text){
		if(text.equals(null) || text.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
