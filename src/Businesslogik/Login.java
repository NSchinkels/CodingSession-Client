package Businesslogik;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login{
	
	private String emailAdresse;
	private String passwort;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private PasswordField pwdPasswort;
	
	/**
	 * Falls die eingegeben Daten valide sind (Methode 'ueberpruefeDaten(String email, String pw)') wird 
	 * die Loginmaske geschlossen und das Hauptfenster der Anwendung geladen. 
	 * IOException wird gefangen, falls main.fxml nicht geladen werden kann.
	 */
	@FXML
	private void anmeldenGeklickt(ActionEvent event){
		if(txtEmail.getText().equals("test") && pwdPasswort.getText().equals("test")){
			try{
				((Node) (event.getSource())).getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/hauptfenster.fxml"));
				Parent root = (Parent) loader.load();
				Stage stage = new Stage();
				Scene scene = new Scene(root);		
				stage.setScene(scene);
				stage.show();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
		
	/**
	 * Schliesst die Loginmaske und laedt die Registrierungsmaske (registrierung.fxml).
	 * IOException wird gefangen, falls registrierung.fxml nicht geladen werden kann.
	 */
	@FXML
	private void oeffneRegistrierungGeklickt(ActionEvent event){
		try{
			((Node) (event.getSource())).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/registrierung.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);		
			stage.setTitle("Registrierung");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private boolean ueberpruefeDaten(String email, String pw){
		return true;
	}
}
