package Businesslogik;

import java.util.ArrayList;

public class Registrierung {
	
	private ArrayList<Benutzerkonto> benutzerliste= new ArrayList<Benutzerkonto>();
	
	//Eigentlich mehrere eigene Exceptions benutzen! 
	
	//Erstellt einen neuen Benutzer mit einem Nicknamen
	//Prüfung, ob E-Mail oder Benutzername bereits vergeben sind, und ob die Daten valide sind.
	private Benutzerkonto erstelleNick(String email, String pw, String name){
		try{
			for(Benutzerkonto b: benutzerliste){
				if((b.getEmail()).equals(email) || (b.getName()).equals(name)){
					throw new Exception();
				}
			}
			if(ueberpruefeNick(email, pw, name) == true){
				Benutzerkonto neuerBenutzer = new BenutzerkontoNickname(email, pw, name);
				benutzerliste.add(neuerBenutzer);
				return neuerBenutzer;
			} else{
				throw new Exception();
			}
		}catch(Exception e){
			System.out.println("Benutzername/E-Mail bereits vergeben oder eingegebene Daten nicht valide!");
			return null;
		}
	}
	
	//Erstellt einen neuen Benutzer mit Vornamen und Nachnamen
	//Prüfung, ob E-Mail oder Benutzername bereits vergeben sind, und ob die Daten valide sind.
	//Keine Prüfung des Namens erforderlich, da der gleiche Name mehrmals vorkommen kann.
	private Benutzerkonto erstelleReal(String email, String pw, String vor, String nach){
		try{
			for(Benutzerkonto b : benutzerliste){
				if((b.getEmail()).equals(email)){
					throw new Exception();
				}
			}
			if(ueberpruefeReal(email, pw, vor, nach) == true){
				Benutzerkonto neuerBenutzer = new BenutzerkontoRealname(email, pw, vor, nach);
				benutzerliste.add(neuerBenutzer);
				return neuerBenutzer;
			} else{
				throw new Exception();
			}
		}catch(Exception e){
			System.out.println("E-Mail bereits vergeben oder eingegebene Daten nicht valide!");
			return null;
		}
	}
	
	//Überprüfung auf Korrektheit der Daten? Welche Einschränkungen soll es z.b. beim Passwort / Namen geben?
	private boolean ueberpruefeNick(String email, String pw, String nick){
		return true;
	}
	
	private boolean ueberpruefeReal(String email, String pw, String vor, String nach){
		return true;
	}
}
