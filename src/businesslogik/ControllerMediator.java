package businesslogik;
public class ControllerMediator {
	
	private CodingSessionController codingsession;
	private CommunityFeedController communityfeed;
	private ProfilController profil;
	private HauptfensterController hauptfenster;
	
	
	
	private ControllerMediator() {
		
	}
	
	
    public void setHauptfenster(HauptfensterController hauptfenster) {
		this.hauptfenster = hauptfenster;
	}


	public void setCodingsession(CodingSessionController codingsession) {
		this.codingsession = codingsession;
	}


	public void setCommunityfeed(CommunityFeedController communityfeed) {
		this.communityfeed = communityfeed;
	}


	public void setProfil(ProfilController profil) {
		this.profil = profil;
	}
	public void neueCodingSession(){
		hauptfenster.neueCodingSession();
	}
	public void beenden(){
		if(codingsession !=null)
			codingsession.beenden();
	}
	public static ControllerMediator getInstance() {
        return ControllerMediatorHolder.INSTANCE;
    }

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }

}
