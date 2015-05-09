package Businesslogik;

import java.util.HashMap;

public class PackageExplorer {
	//CodingSessions werden über den Titel der ihnen im PE gegeben wurde sortiert
	private HashMap<String,CodingSession> inhalt;
	//Imo billig 0 wert jewals ordner name, alles dahinter inhalt des ordners,auf null prüfen
	//wird vll noch besser
	private String[][] ordnerStrucktur;
	int ordnerEnde=0;
	
	public CodingSession csErstellen(String titel,String ordner){
		if(ordner==null){
			int i=0;
			for(String t:ordnerStrucktur[ordnerEnde]){
				if(t==null){
					ordnerStrucktur[ordnerEnde][i]=titel;
					inhalt.put(titel, null);
				}
			}
		}
	}
}
