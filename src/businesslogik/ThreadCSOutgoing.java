package businesslogik;

import javafx.scene.control.TextArea;

public class ThreadCSOutgoing implements Runnable {

	TextArea csGUI;
	CodingSession cs;
	TextArea chatGUI;
	Chat chat;

	public ThreadCSOutgoing(TextArea csGUI, CodingSession cs, TextArea chatGUI,
			Chat chat) {
		this.csGUI = csGUI;
		this.cs = cs;
		this.chatGUI = chatGUI;
		this.chat = chat;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (cs.hasChanged()) {
					csGUI.setText(cs.getCode());
				} else {
					cs.neuerCodeGUI(csGUI.getText());
				}
				Thread.sleep(2000);
				chatGUI.setText("");
				for(String text:chat.empfangen()){
					chatGUI.appendText(text);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
