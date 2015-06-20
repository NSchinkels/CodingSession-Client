package businesslogik;

public class ControllerMediator {
	
	private CodingSessionController codingsession;
	private CommunityFeedController communityfeed;
	private FreundeSucheController suche;
	private HauptfensterController hauptfenster;
	private Profilbearbeitung bearbeitung;
	private ProfilController profil;
	
	private ControllerMediator() {
		
	}
	
	public static ControllerMediator getInstance() {
        return ControllerMediatorHolder.INSTANCE;
    }
		
    public void setHauptfenster(HauptfensterController hauptfenster) {
		this.hauptfenster = hauptfenster;
	}

	public void setCodingSession(CodingSessionController codingsession) {
		this.codingsession = codingsession;
	}

	public void setCommunityfeed(CommunityFeedController communityfeed) {
		this.communityfeed = communityfeed;
	}
	
	public void setFreundeSuche(FreundeSucheController suche){
		this.suche = suche;
	}

	public void setProfil(ProfilController profil) {
		this.profil = profil;
	}
	
	public void setProfilbearbeitung(Profilbearbeitung bearbeitung) {
		this.bearbeitung = bearbeitung;
	}
	
	public void addCommunityFeed(CodingSessionModell csmod){
		communityfeed.addBeitrag(csmod);
	}
	
	public void beenden(){
		if(codingsession !=null)
			codingsession.beenden();
	}
	
	public void changeCodingSession(){
		codingsession.changeModell(KommunikationIncoming.getEinladung());
	}
	
	public void neueCodingSession(){
		hauptfenster.neueCodingSession();
	}
	
	public void neueFreundeSuche(){
		hauptfenster.neueFreundeSuche();
	}
	
	public void neueProfilbearbeitung(){
		hauptfenster.neueProfilBearbeitung();
	}
	
	public void zurueckZumProfil(){
		hauptfenster.zurueckZumProfil();
	}

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }

}
