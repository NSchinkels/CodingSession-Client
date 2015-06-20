package businesslogik;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class HauptfensterController implements Initializable{
	
	ProfilController profilController;
	CommunityFeedController communityFeedController;
	ProfilbearbeitungController profilbearbeitungController;
	FreundeSucheController freundeSucheController;
	KommunikationStart com;
	KommunikationIncoming comi;
	KommunikationOutgoing como;
	CodingSessionModell csmod;
	String benId="Wär cool wenn hier mal ein wert stehen würde";

	private Tab tabCodingSession;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Tab tabProfil;
	
	@FXML
	private Tab tabCommunityFeed;

	@Override
	public void initialize(URL url, ResourceBundle rb){
		//wird durch richtige ersetzt
		benId=String.valueOf((int)(Math.random()*123123)+1);
		com=new KommunikationStart(benId);
		comi=new KommunikationIncoming(benId, com);
		como=new KommunikationOutgoing(benId, com);
		ControllerMediator.getInstance().setHauptfenster(this);
		try{
         	FXMLLoader loaderProfil = new FXMLLoader(getClass().getResource("/view/fxml/profil.fxml"));
         	profilController = new ProfilController();
         	ControllerMediator.getInstance().setProfil(profilController);
 			loaderProfil.setController(profilController);
 			Parent rootProfil = (Parent) loaderProfil.load();
            tabProfil.setContent(rootProfil);
            
            FXMLLoader loaderCF = new FXMLLoader(getClass().getResource("/view/fxml/community_feed.fxml"));
            communityFeedController = new CommunityFeedController();
            ControllerMediator.getInstance().setCommunityfeed(communityFeedController);
            loaderCF.setController(communityFeedController);
            Parent rootCF = (Parent) loaderCF.load();
            tabCommunityFeed.setContent(rootCF);
         } catch (IOException e){
             e.printStackTrace();
         }
	}
	
	public void neueCodingSession(){
		csmod = new CodingSessionDialog().erstelleStartDialog();
		CodingSessionController codingSessionController = null;
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/codingsession.fxml"));
			codingSessionController = new CodingSessionController(csmod,comi,como);
			ControllerMediator.getInstance().setCodingSession(codingSessionController);
			loader.setController(codingSessionController);
			Parent root = (Parent) loader.load();
			tabCodingSession = new Tab("CodingSession");
			tabPane.getTabs().add(tabCodingSession);
			tabPane.getSelectionModel().selectLast();
			tabCodingSession.setContent(root);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void neueFreundeSuche(){
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/freunde_suche.fxml"));
			freundeSucheController = new FreundeSucheController();
			ControllerMediator.getInstance().setFreundeSuche(freundeSucheController);
			loader.setController(freundeSucheController);
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);	
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void neueProfilBearbeitung(){
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/profilbearbeitung.fxml"));
			profilbearbeitungController = new ProfilbearbeitungController();
			ControllerMediator.getInstance().setProfilbearbeitung(profilbearbeitungController);
			loader.setController(profilbearbeitungController);
			Parent root = (Parent) loader.load();
			tabProfil.setText("Profil: Profilbearbeitung");
			tabProfil.setContent(root);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void zurueckZumProfil(){
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/profil.fxml"));
         	profilController = new ProfilController();
         	ControllerMediator.getInstance().setProfil(profilController);
 			loader.setController(profilController);
 			Parent root = (Parent) loader.load();
 			tabProfil.setText("Profil");
            tabProfil.setContent(root);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
