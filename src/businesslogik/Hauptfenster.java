package businesslogik;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;


public class Hauptfenster implements Initializable{
	
	Object lock;
	CodingSession codingSession;
	Profil profil;
	CommunityFeed communityFeed;
	KommunikationStart com;
	KommunikationIncoming comi;
	KommunikationOutgoing como;
	
	int benId;
	int csId;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Tab tabProfil;
	
	@FXML
	private Tab tabCommunityFeed;

	@Override
	public void initialize(URL url, ResourceBundle rb){
		 try{
         	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/profil.fxml"));
         	profil = new Profil();
 			loader.setController(profil);
 			Parent root = (Parent) loader.load();
            tabProfil.setContent(root);
         } catch (IOException e){
             e.printStackTrace();
         }
	
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
			@Override
			public void changed(ObservableValue<? extends Tab> ov, Tab alterTab, Tab neuerTab){
				if(neuerTab == tabCommunityFeed && neuerTab.getContent() == null){
		           try{
		        	   FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/community_feed.fxml"));
		               communityFeed = new CommunityFeed();
		               loader.setController(communityFeed);
		               Parent root = (Parent) loader.load();
		               neuerTab.setContent(root);
		           } catch(IOException e){
		        	   e.printStackTrace();
		           }
		        } else{
		        	 // Der Inhalt ist bereits vorhanden und wird falls notwendig neu geladen.
		            Parent root = (Parent) neuerTab.getContent();
		        }
			}
		});
	}
	
	@FXML
	public void testMethode(ActionEvent event){
		benId=(int)(Math.random()*123123)+1;
		com=new KommunikationStart(benId);
		lock=new Object();
		comi=new KommunikationIncoming(benId, com, lock, new Object());
		como=new KommunikationOutgoing(benId, com);
	
		try{
			((Node) (event.getSource())).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/codingsession.fxml"));
			codingSession =new CodingSession("huhu", false, comi, como, benId, 1);
			loader.setController(codingSession);
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("CodingSession");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
