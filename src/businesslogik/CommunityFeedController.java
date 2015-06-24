package businesslogik;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CommunityFeedController implements Initializable{
	
	private Beitrag[] beitraege;
	private static int anzahl;
	
	@FXML
	private ListView<Beitrag> listCommunityFeed;

	public CommunityFeedController() {
		// hier werden noch die Sachen vom Server gezogen
		beitraege=new Beitrag[10];
		beitraege[0]=new Beitrag(new CodingSessionModell(5,"huhu@web.de","titel1",true,"public test"), "Fehler", "Hab hier krassen Fehler", false);
		beitraege[1]=new Beitrag(new CodingSessionModell(6,"huhuer@web.de","titel2",true,"public test"), "Fehler schon wieder", "Hab hier wieder krassen Fehler", false);
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Beitrag> communityFeedItems = listCommunityFeed.getItems();
        communityFeedItems.add(beitraege[0]);
        communityFeedItems.add(beitraege[1]);
        listCommunityFeed.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        ControllerMediator.getInstance().changeCodingSession(listCommunityFeed.getSelectionModel().getSelectedItem().getSession());
                    }
                }
            }
        });
	}
	
	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}
	
	@FXML
	public void codingSessionStartenGeklickt(ActionEvent event){
		ControllerMediator.getInstance().neueCodingSession();
	}
	
	@FXML
	public void communityFeedAktualisierenGeklickt(ActionEvent event){
		System.out.println("Test");
	}
	
	@FXML
	public void txtSucheFreundeGeklickt(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			ControllerMediator.getInstance().neueFreundeSuche();	
		}	
	}

	public void addBeitrag(Beitrag beitrag) {
		//aktuelle Anzahl vom Server ziehen
		//beitraege[anzahl++] = beitrag;
		//hier wird dann gespeichert
	}
	public static int getAnzahl(){
		//zuerst aktualisieren vom server
		return anzahl;
	}
	public Beitrag[] getBeitraege() {
		return beitraege;
	}

	
}
