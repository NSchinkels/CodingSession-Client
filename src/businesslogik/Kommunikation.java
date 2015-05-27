package businesslogik;
//hier wird sobald jms läuft sachen passiern
//jetzt erstmal ziemlich theroitsche methoden
	
public class Kommunikation {
	public Object test; 
	public Kommunikation(){
		this.test=new Object();
		//Verbindung mit JMS
	}
	public void veröffentlichCode(String code){
		//code an topic geschrieben
	}
	public void neuerCode(){
		//hier wartet später das JMS
		test.notify();
	}
	public String getNeuerCode(){
		return "TEST";
	}
	
}
