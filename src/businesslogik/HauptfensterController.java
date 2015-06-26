package businesslogik;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;
import javafx.application.Platform;
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

public class HauptfensterController implements Initializable {

	ProfilController profilController;
	CommunityFeedController communityFeedController;
	ProfilbearbeitungController profilbearbeitungController;
	FreundeSucheController freundeSucheController;
	CodingSessionController codingSessionController;
	KommunikationStart kommunikationStart;
	KommunikationIncoming kommunikationIn;
	KommunikationOutgoing kommunikationOut;
	CodingSessionModell codingSessionModell;
	Benutzerkonto benutzerkonto;

	private Tab tabCodingSession;

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab tabProfil;

	@FXML
	private Tab tabCommunityFeed;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		benutzerkonto = ControllerMediator.getInstance().getBenutzerkonto();
		kommunikationStart = new KommunikationStart(benutzerkonto.getEmail());
		kommunikationIn = new KommunikationIncoming(benutzerkonto.getEmail(), kommunikationStart);
		kommunikationOut = new KommunikationOutgoing(benutzerkonto.getEmail(), kommunikationStart);

		ControllerMediator.getInstance().setHauptfenster(this);
		kommunikationIn.bekommeEinladung();
			this.neuesProfil();
			this.neuerCf();
	}

	public void neueCodingSession(boolean dialog, CodingSessionModell codingSessionModell) {
		if (codingSessionController != null) {
			schliesseCodingSession();
		}
		if (dialog) {
			codingSessionModell = new CodingSessionDialog().erstelleStartDialog();
			if (codingSessionModell.isSpeichern()) {
				try {
					Datenhaltung.schreibeCS(codingSessionModell);
				} catch (PersistenzException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		codingSessionController = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/codingsession.fxml"));
			codingSessionController = new CodingSessionController(codingSessionModell, kommunikationIn, kommunikationOut);
			ControllerMediator.getInstance().setCodingSession(codingSessionController);
			loader.setController(codingSessionController);
			Parent root = (Parent) loader.load();
			tabCodingSession = new Tab("CodingSession");
			tabCodingSession.setClosable(true);
			tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
			tabPane.getTabs().add(tabCodingSession);
			tabPane.getSelectionModel().selectLast();

			// Das kann man bestimmt schoener machen..
			tabCodingSession.setOnCloseRequest(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					event.consume();
					new CodingSessionDialog().erstelleEndDialog();
				}
			});

			tabCodingSession.setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void neueFreundeSuche() {
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void neueProfilBearbeitung() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/profilbearbeitung.fxml"));
			profilbearbeitungController = new ProfilbearbeitungController();
			ControllerMediator.getInstance().setProfilbearbeitung(profilbearbeitungController);
			loader.setController(profilbearbeitungController);
			Parent root = (Parent) loader.load();
			tabProfil.setText("Profil: Profilbearbeitung");
			tabProfil.setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void neuesProfil() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/profil.fxml"));
			profilController = new ProfilController();
			ControllerMediator.getInstance().setProfil(profilController);
			loader.setController(profilController);
			Parent root = (Parent) loader.load();
			tabProfil.setText("Profil");
			tabProfil.setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void neuerCf(){
		FXMLLoader loaderCF = new FXMLLoader(getClass().getResource("/view/fxml/community_feed.fxml"));
		try {
			communityFeedController = Datenhaltung.leseCF();
		} catch (PersistenzException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ControllerMediator.getInstance().setCommunityfeed(communityFeedController);
		loaderCF.setController(communityFeedController);
		Parent rootCF = null;
		try {
			rootCF = (Parent) loaderCF.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabCommunityFeed.setContent(rootCF);

	}

	public void schliesseCodingSession() {
		tabPane.getTabs().remove(tabPane.getTabs().remove(tabCodingSession));
		tabPane.getSelectionModel().selectFirst();
		codingSessionController.killThread();
	}
}
