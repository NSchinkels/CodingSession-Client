package businesslogik;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicSubscriber;

public class KommunikationIncoming {

	public String neuerCode = "";
	KommunikationStart kommunikationStart;
	MessageListener listnerFuerCode;
	MessageListener listnerFuerEinladung;
	MessageListener listnerFuerChat;
	TopicSubscriber topsubCode;
	TopicSubscriber topsubEinladung;
	TopicSubscriber tobsubChat;
	static CodingSessionModell csEinladung;
	Session session;
	String benutzerId;
	boolean change;
	Object lock;

	public KommunikationIncoming(String benutzerId, KommunikationStart kommunikationStart,Object lock) {

		this.session = kommunikationStart.getSession();
		this.benutzerId = benutzerId;
		this.kommunikationStart = kommunikationStart;
		this.lock=lock;
	}

	public void bekommeCode(String topic, String benutzer) {
		// hier wartet später das JMS aud Code von Csen
		if (topsubCode != null) {
			try {
				topsubCode.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			topsubCode = session.createDurableSubscriber(kommunikationStart.getTopicCode(),
					"Benutzer" + benutzer);
			listnerFuerCode = new MessageListener() {
				public void onMessage(Message message) {
					try {
						if (!neuerCode
								.equals(((TextMessage) message).getText())
								&& message.getStringProperty("sender") != benutzer) {
							neuerCode = ((TextMessage) message).getText();
							change = true;
						}
					} catch (JMSException e) {
						// throw new Exception("Konnte nicht den Code teilen");
					}
				}
			};
			topsubCode.setMessageListener(listnerFuerCode);
		} catch (Exception e1) {
		}
	}

	public void bekommeEinladung() {
		try {
			listnerFuerEinladung = new MessageListener() {
				public void onMessage(Message message) {
					System.out.println("NACHRICHT");
					try {
						synchronized(lock){
						if (message.getStringProperty("id").equals(ControllerMediator.getInstance().getBenutzerkonto().getEmail())) {
							System.out.println("neue om erhalten");
							csEinladung = ((CodingSessionModell) ((ObjectMessage) message).getObject());
							ControllerMediator.getInstance().einladen("huhu");
							lock.notifyAll();
						}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			kommunikationStart.getTopsubEinladung()
					.setMessageListener(listnerFuerEinladung);
		} catch (Exception e1) {
			e1.printStackTrace();
			;
		}
	}

	public void bekommeChat(int chatId, List<String> chatLog) {
		if (tobsubChat != null) {
			try {
				tobsubChat.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			tobsubChat = session.createDurableSubscriber(kommunikationStart.getTopicChat(),
					"Chatter" + benutzerId);
			listnerFuerChat = new MessageListener() {
				public void onMessage(Message message) {
					if (message instanceof TextMessage) {
						try {
							((LinkedList<String>) chatLog).addLast(message
									.getStringProperty("sender")
									+ ": "
									+ ((TextMessage) message).getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}

			};
			tobsubChat.setMessageListener(listnerFuerChat);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public static CodingSessionModell getEinladung() {
		if (csEinladung != null) {
			return csEinladung;
		} else {
			// throw new Exception("Konnte den Einlader nicht starten");
			return null;
		}
	}

	public String getCode() {
		change = false;
		return this.neuerCode;
	}

	public boolean hasChanged() {
		return change;
	}

	public void beenden() {
		kommunikationStart.beenden();
	}
}