package businesslogik;

import java.net.URL;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

@Entity
@Table(name = "CommunityFeedController")
public class CommunityFeedController implements Initializable, Serializable {
	/**
	 * Der CFController beinhaltet die Funktion seinen Quellcode mit der
	 * Community zu teilen. Es können Fragen und Probleme zusammen bearbeitet
	 * werden
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	@OneToMany(targetEntity = Beitrag.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	
	// Inhalt des CommunityFeeds
	private List<Beitrag> beitraege;
	
	// Da es nur einen CommunityFeedController geben soll PK ID auf 1 gesetzt
	@Id
	private int ID = 1;

	@FXML
	@Transient
	private ListView<Beitrag> listCommunityFeed;

	public CommunityFeedController() {
		// beitraege=new LinkedList<Beitrag>();
	}

	/**
	 * Methode fuerr FXML, die beim start ausgeführt wird
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// aus den Beitreagen wird eine Liste gemacht
		listCommunityFeed.getItems().clear();
		ObservableList<Beitrag> communityFeedItems = listCommunityFeed.getItems();
		for (Beitrag b : beitraege) {
			communityFeedItems.add(b);
		}
		listCommunityFeed.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
//						CodingSessionModell codingSession = listCommunityFeed.getSelectionModel().getSelectedItem().getSession();
//						if (codingSession.isSpeichern()) {
//							codingSession.setBenutzerId(ControllerMediator.getInstance().getBenutzerkonto().getEmail());
//						}
						ControllerMediator.getInstance().changeCodingSession(listCommunityFeed.getSelectionModel().getSelectedItem().getSession());
					}
				}
			}
		});
	}

	/**
	 * Der Benutzer kann sich hiermit abmelden
	 * 
	 * @param event
	 */
	@FXML
	public void abmeldenGeklickt(ActionEvent event) {
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}

	/**
	 * Der Benutzer kann eine neue CodingSession starten
	 * 
	 * @param event
	 */
	@FXML
	public void codingSessionStartenGeklickt(ActionEvent event) {
		ControllerMediator.getInstance().neueCodingSession();
	}

	/**
	 * Der CommunityFeed wird aktualisert
	 * 
	 * @param event
	 */
	@FXML
	public void communityFeedAktualisierenGeklickt(ActionEvent event) {
		ControllerMediator.getInstance().neuerCommunityFeed();
	}

	/**
	 * Ein neuer Beitrag wird zum CommunityFeed hinzugefuegt
	 * 
	 * @param beitrag
	 *            Der neue Beitrag
	 */

	public void addBeitrag(Beitrag beitrag) {
		beitraege.add(beitrag);
		try {
			Datenhaltung.schreibeCF(this);
		} catch (PersistenzException e) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Community", "Konnte den Beitrag nich posten. \nBist du nicht der Eigentümer?");
		}
	}

	public List<Beitrag> getBeitrag() {
		return this.beitraege;
	}

	public void setBeitrag(List<Beitrag> beitrag) {
		this.beitraege = beitrag;
	}
}
