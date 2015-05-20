
package Businesslogik;

import java.io.*;
import java.net.*;

public class CodingSession {
//nicht im Diagramm,aber bestimmt wichtig 
private int benutzerId;

private String titel;

private boolean speichern;
//Im Moment noch als ein String,später was besseres
private String code;
//Cs nur mit titel und speichern erstellbar
private Thread warteAufCode;

private Socket sock;
private ObjectOutputStream obj=null;
public CodingSession(String titel,boolean speichern,Socket sock){
	this.titel=titel;
	this.speichern=speichern;
	this.sock=sock;
	//Hier noch tricky:Der Client wartet auf Code der von andern Personen geschrieben wird
	warteAufCode =new Thread(){
		public void run(){
			while(true){
				try {
					//hier wird später auf neuen code gewartet, der von anderen geschickt wird
					warteAufCode.wait();
					//aktualsiereCode(Code der von den anderen kommt)
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	};
	
}
//Methode die zeitlich aufgrufen wird um den alten code mit dem neuen zu erstetzen 
public synchronized void aktualsiereCode(String text){
	this.code=text;
	codeVeroeffentlichen();
}
public String codeVeroeffentlichen(){
	//Server Magic. Die anderne clienenten wird der neue code gegeben
	try {
		sock = new Socket("192.168.2.104",6000);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		obj=new ObjectOutputStream(sock.getOutputStream());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		obj.writeObject(this);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return code;
}


}