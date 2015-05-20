import java.io.IOException;
import java.net.*;
public class Controller {
	//Socket um mit dem Server zu reden
	private Socket Sock;
	
	public Controller(){
		try {
			//Mein lokaler Server. Wird durch unsern ersetzt
			Sock = new Socket("192.168.2.103",6000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Socket getSock() {
		return Sock;
	}

	public void setSock(Socket sock) {
		Sock = sock;
	}
	

}
