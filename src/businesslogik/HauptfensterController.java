package businesslogik;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class HauptfensterController implements Initializable{
	
	ProfilController profilController;
	CommunityFeedController communityFeedController;
	ProfilbearbeitungController profilbearbeitungController;
	FreundeSucheController freundeSucheController;
	CodingSessionController codingSessionController;
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
         	this.neuesProfil();
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
	
	public void neueCodingSession(boolean dialog,CodingSessionModell csmod){
		if(codingSessionController!=null){
			codingSessionController.killThread();
			schliesseCodingSession();
		}
		if(dialog){
			csmod =new CodingSessionDialog().erstelleStartDialog();
		}
		codingSessionController = null;
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/codingsession.fxml"));
			codingSessionController = new CodingSessionController(csmod,comi,como);
			ControllerMediator.getInstance().setCodingSession(codingSessionController);
			loader.setController(codingSessionController);
			Parent root = (Parent) loader.load();
			tabCodingSession = new Tab("CodingSession");
			tabCodingSession.setClosable(true);
			tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
			tabPane.getTabs().add(tabCodingSession);
			tabPane.getSelectionModel().selectLast();
			
			//Das kann man bestimmt schoener machen..
			tabCodingSession.setOnCloseRequest(new EventHandler<Event>() {
				   @Override
				   public void handle(Event event) {
					   event.consume();
					   new CodingSessionDialog().erstelleEndDialog();
				   }
			});
			
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
	
	public void neuesProfil(){
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
	
	
	public void schliesseCodingSession(){
		tabPane.getTabs().remove(tabPane.getTabs().remove(tabCodingSession));
		tabPane.getSelectionModel().selectFirst();
	}
}
