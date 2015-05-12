
package Businesslogik;

public class CodingSession {
//nicht im Diagramm,aber bestimmt wichtig 
private int benutzerId;

private String titel;

private boolean speichern;
//Im Moment noch als ein String,später was besseres
private String code;
//Cs nur mit titel und speichern erstellbar
private Thread warteAufCode;
public CodingSession(String titel,boolean speichern){
	this.titel=titel;
	this.speichern=speichern;
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
	return code;
}


}