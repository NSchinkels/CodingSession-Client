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
	
	//Wenn Zeit uebrig bleibt, mach ich das noch schoener.
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
					new CodingSessionDialog().erstelleVornameValidierungDialog();
				}
			}
			
			if(!istLeer(txtNachname.getText())) {
				if(txtNachname.getText().matches(nachnameRegex)) {
					benutzerkonto.setNachname(txtNachname.getText());
				} else {
					new CodingSessionDialog().erstelleNachnameValidierungDialog();
				}
			}
		} else {
			if(!istLeer(txtNickname.getText())) {
				if(txtNickname.getText().matches(nicknameRegex)) {
					benutzerkonto.setNickname(txtNickname.getText());
				} else {
					new CodingSessionDialog().erstelleNicknameValidierungDialog();
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
				new CodingSessionDialog().erstelleGeburtsdatumValidierungDialog();
			}	
		}
		
		if(!istLeer(txtGeburtsort.getText())) {
			if(txtGeburtsort.getText().matches(geburtsortRegex)) {
				profilModell.setGeburtsort(txtGeburtsort.getText());
			} else {
				new CodingSessionDialog().erstelleGeburtsortValidierungDialog();
			}
		}
		
		if(!istLeer(txtWohnort.getText())) {
			if(txtWohnort.getText().matches(wohnortRegex)) {
				profilModell.setWohnort(txtWohnort.getText());
			} else {
				new CodingSessionDialog().erstelleWohnortValidierungDialog();
			}
		}
		
		if(!istLeer(txtAktuellerJob.getText())) {
			if(txtAktuellerJob.getText().matches(aktuellerJobRegex)) {
				profilModell.setAktuellerJob(txtAktuellerJob.getText());
			} else {
				new CodingSessionDialog().erstelleAktuellerJobValidierungDialog();
			}		
		}

		if(!istLeer(txtProgrammierkenntnisse.getText())) {
			if(txtProgrammierkenntnisse.getText().matches(pkenntnisseRegex)) {
				profilModell.setProgrammierkenntnisse(txtProgrammierkenntnisse.getText());
			} else {
				new CodingSessionDialog().erstelleProgrammierkenntnisseValidierungDialog();
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
						new CodingSessionDialog().erstellePasswoerterWiderspruchDialog();
					}
				} else {
					new CodingSessionDialog().erstellePasswortValidierungDialog();
				}		
			} else {
				new CodingSessionDialog().erstelleAltesPasswortValidierungDialog();
			}
		}
		
		try {
			Datenhaltung.updateDB((BenutzerkontoOriginal) benutzerkonto);
			Datenhaltung.updateProfil(profilModell);
		} catch (PersistenzException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	public void zurueckZumProfilGeklickt(ActionEvent event) {
		ControllerMediator.getInstance().neuesProfil();
	}
	
	private boolean istLeer(String text){
		if(text.equals(null) || text.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
