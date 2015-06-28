package businesslogik;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Persistence.Datenhaltung;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class HauptfensterController implements Initializable {
	/**
	 * Das Hauotfenster der Anwendung besitzt die Controller der Untefenster Es
	 * erstellt Oberfläche der Fenster und verbindet die Controller mit dieser
	 * Desweitern werden hier die Kommunikations Objekte erstellt und das
	 * Benutzerkonto gespeichert
	 */
	ProfilController profilController;
	CommunityFeedController communityFeedController;
	ProfilbearbeitungController profilbearbeitungController;
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

	/**
	 * Methode die von JavaFx ausgerufen wird um den Controller zu
	 * initialisieren Kommunikation wird gestartet und das Profil und der Cf
	 * geladen
	 */
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

	/**
	 * Methode die ein neues CodingSession Fenster entwirft
	 * 
	 * @param dialog
	 *            Wenn der CodingSession starten Button gedrueckt wurde und eine
	 *            CodingSession mit einem Dialog erstellt werden soll
	 * @param codingSessionModell
	 *            falls eine CodingSession aus dem PackageExplorer oder CF kommt
	 *            wird hier das Modell uebergeben
	 */
	public void neueCodingSession(boolean dialog, CodingSessionModell codingSessionModell) {
		if (codingSessionController != null) {
			schliesseCodingSession();
		}
		if (dialog) {
			codingSessionModell = new CodingSessionDialog().erstelleCsStartenDialog();
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

			tabCodingSession.setOnCloseRequest(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					event.consume();
					new CodingSessionDialog().erstelleCsSchliessenDialog();
				}
			});

			tabCodingSession.setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void neueProfilBearbeitung() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/profilbearbeitung.fxml"));
			profilbearbeitungController = new ProfilbearbeitungController();
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
			loader.setController(profilController);
			Parent root = (Parent) loader.load();
			tabProfil.setText("Profil");
			tabProfil.setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Der Cf wird von der Datenbank gelesen Diese Methode wird auch aufgerufen
	 * falls der CF neu geladen werden soll
	 */

	public void neuerCf() {

		try {
			FXMLLoader loaderCF = new FXMLLoader(getClass().getResource("/view/fxml/community_feed.fxml"));
			communityFeedController = Datenhaltung.leseCF();
			ControllerMediator.getInstance().setCommunityfeed(communityFeedController);
			loaderCF.setController(communityFeedController);
			Parent rootCF = (Parent) loaderCF.load();
			tabCommunityFeed.setContent(rootCF);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Der Tab fuer die CodingSession wird geschlossen
	 */
	public void schliesseCodingSession() {
		tabPane.getTabs().remove(tabPane.getTabs().remove(tabCodingSession));
		tabPane.getSelectionModel().selectFirst();
		codingSessionController.killThread();
	}
}
