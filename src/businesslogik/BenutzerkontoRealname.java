package businesslogik;

public class BenutzerkontoRealname extends BenutzerkontoOriginal {

	private String vorname;
	private String nachname;
	
	public BenutzerkontoRealname(String email, String pw, String vor, String nach) {
		super(email,pw);
		this.vorname = vor;
		this.nachname = nach;
	}
	
	public String getName() {
		return nachname + ", " + vorname;
	}
	
	public void setName(String vor, String nach) {
		this.vorname = vor;
		this.nachname = nach;
	}
	
}
