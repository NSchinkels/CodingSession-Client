import java.io.IOException;
import java.net.*;
import Businesslogik.*;
public class Controller {
	//Socket um mit dem Server zu reden
	private Socket Sock;
	
	public Controller(){
		
		//ZUM TESTEN WIRD NOCH ENTFERNT
		CodingSession cs =new CodingSession("Test",false,Sock);
		cs.aktualsiereCode("Ein Satz");
		cs.codeVeroeffentlichen();
	}

	public Socket getSock() {
		return Sock;
	}

	public void setSock(Socket sock) {
		Sock = sock;
	}
	

}
