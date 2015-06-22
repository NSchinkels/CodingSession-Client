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

public class LoginController{
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private PasswordField pwdPasswort;
	
	/**
	 * Wenn der Button 'Anmelden' geklickt wird und die Daten valide
	 * sind wird die Loginmaske geschlossen und das Hauptfenster 
	 * der Anwendung geladen. 
	 * 
	 * @throws IOException Falls main.fxml nicht geladen werden kann.
	 */
	@FXML
	private void anmeldenGeklickt(ActionEvent event){
		if(txtEmail.getText().equals("test") && pwdPasswort.getText().equals("test")){
		try{
//			Wird ausgeklammert, sobald wir mit Accounts arbeiten
			//if(Datenhaltung.passwortRichtig(txtEmail.getText(), pwdPasswort.getText())){
				((Node) (event.getSource())).getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/hauptfenster.fxml"));
				Parent root = (Parent) loader.load();
				Stage stage = new Stage();
				Scene scene = new Scene(root);		
				stage.setScene(scene);
			    stage.setMaximized(true); 
				stage.show();
			}catch(Exception e){
				
			}
		}
	}
		
	/**
	 * Wenn der Hyperlink geklickt wird, schliesst die Loginmaske 
	 * und die Registrierungsmaske wird geladen.
	 * 
	 * @throws IOException Falls registrierung.fxml nicht geladen werden kann.
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
}
