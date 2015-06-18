package businesslogik;

import javax.persistence.*;

import java.io.*;


@Entity
public class BenutzerkontoNickname extends BenutzerkontoOriginal {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nickname;

	public BenutzerkontoNickname(String email, String pw, String name,int id) {
		super(email, pw,id);
		this.nickname = name;
	}
	//default Konstruktor hinzugefügt,da für JPA notwendig
	public BenutzerkontoNickname(){}
	
	public String getName() {
		return nickname;
	}
	
	public void setName(String nick) {
		this.nickname = nick;
	}
}