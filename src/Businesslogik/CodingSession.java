package Businesslogik;

import java.io.*;
import java.net.*;

//wird noch durch JMS ersetzt!!!!
public class CodingSession {
	// nicht im Diagramm,aber bestimmt wichtig
	private int benutzerId;
	private int id;
	private String titel;
	private boolean speichern;
	// Im Moment noch als ein String,später was besseres
	private String code;
	// Cs nur mit titel und speichern erstellbar

	private Profil[] teilnehmer;
	private int anzahlTeilnehmer = 0;

	public CodingSession(String titel, boolean speichern, Kommunikation com) {
		this.titel = titel;
		this.speichern = speichern;
		// teilnehmer= new Profil[10];
	}

	// Methode die zeitlich aufgrufen wird um den alten code mit dem neuen zu
	// erstetzen
	public synchronized void aktualsiereCode(String text) {
		this.code = text;
		codeVeroeffentlichen();
	}

	public String codeVeroeffentlichen() {
		// Server Magic. Die anderne clienenten wird der neue code gegeben
		return null;
	}

	public boolean addTeilnehmer(Profil b) {
		if (anzahlTeilnehmer < 10) {
			teilnehmer[anzahlTeilnehmer++] = b;
			return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSpeichern() {
		return speichern;
	}

	public void setSpeichern(boolean speichern) {
		this.speichern = speichern;
	}
}