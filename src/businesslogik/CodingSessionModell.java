package businesslogik;

import java.io.Serializable;

public class CodingSessionModell implements Serializable{

	
		private static final long serialVersionUID = 1L;
		// IDs
		private int benutzerId;
		private int id;

		// Daten der CS
		private String titel;
		private boolean speichern;
		
		// Teilnehmer
		private String[] teilnehmer;
		private int anzahlTeilnehmer = 0;
		
		//Geschriebener Code
		private String code;

		
		public CodingSessionModell(){
			teilnehmer = new String[10];
		}
		
		public CodingSessionModell(int benutzerId, int id, String titel,
				boolean speichern,String code) {
			this.benutzerId = benutzerId;
			this.id = id;
			this.titel = titel;
			this.speichern = speichern;
			this.teilnehmer = new String[10];
			this.anzahlTeilnehmer = 1;
			this.code = code;
		}

		public int getBenutzerId() {
			return benutzerId;
		}

		public void setBenutzerId(int benutzerId) {
			this.benutzerId = benutzerId;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitel() {
			return titel;
		}

		public void setTitel(String titel) {
			this.titel = titel;
		}

		public boolean isSpeichern() {
			return speichern;
		}

		public void setSpeichern(boolean speichern) {
			this.speichern = speichern;
		}

		public String[] getTeilnehmer() {
			return teilnehmer;
		}

		public void setTeilnehmer(String[] teilnehmer) {
			this.teilnehmer = teilnehmer;
		}
		public String getTeilnehmer(int i) {
			return teilnehmer[i];
		}

		public void addTeilnehmer(String teilnehmer) {
			this.teilnehmer[anzahlTeilnehmer] = teilnehmer;
		}
		
		public int getAnzahlTeilnehmer() {
			return anzahlTeilnehmer;
		}

		public void setAnzahlTeilnehmer(int anzahlTeilnehmer) {
			this.anzahlTeilnehmer = anzahlTeilnehmer;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
		
		
		
}
