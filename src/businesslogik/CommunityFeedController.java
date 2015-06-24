package businesslogik;


import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.io.Serializable;

import javax.persistence.*;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;
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


@Entity
@Table(name="CommunityFeedController")
public class CommunityFeedController implements Initializable,Serializable{
	@OneToMany(targetEntity=Beitrag.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Beitrag> beitraege;
	//Da es nur einen CFController geben soll PK ID auf 1 gesetzt
	@Id
	private int ID = 1;
	private static int anzahl;
	
	@FXML
	@Transient
	private ListView<Beitrag> listCommunityFeed;

	public CommunityFeedController() {
		beitraege=new LinkedList<Beitrag>();
		beitraege.add(new Beitrag(new CodingSessionModell(3,"Test@web.de","Beispiel",true,"public test"), "Beispiel", "nur ein Beispiel", false));
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			beitraege=Datenhaltung.leseCF().getBeitrag();
		} catch (PersistenzException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObservableList<Beitrag> communityFeedItems = listCommunityFeed.getItems();
       for(Beitrag b:beitraege){
    	   communityFeedItems.add(b);
       }
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
		this.initialize(null,null);
	}
	
	@FXML
	public void txtSucheFreundeGeklickt(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			ControllerMediator.getInstance().neueFreundeSuche();	
		}	
	}

	public void addBeitrag(Beitrag beitrag) {
		beitraege.add(beitrag);
		try {
			Datenhaltung.schreibeCF(this);
		} catch (PersistenzException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Beitrag> getBeitrag(){
		return this.beitraege;
	}
}
