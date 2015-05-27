package businesslogik;

public class BenutzerkontoNickname extends BenutzerkontoOriginal {
	
	private String nickname;

	public BenutzerkontoNickname(String email, String pw, String name) {
		super(email, pw);
		this.nickname = name;
	}
	
	public String getName() {
		return nickname;
	}
	
	public void setName(String nick) {
		this.nickname = nick;
	}
}