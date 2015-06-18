package businesslogik;

import java.util.LinkedList;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicSubscriber;

public class KommunikationIncoming {

	public String neuerCode = "";
	KommunikationStart komser;
	MessageListener listnerFuerCode;
	MessageListener listnerFuerEinladung;
	MessageListener listnerFuerChat;
	TopicSubscriber topsubCode;
	TopicSubscriber topsubEinladung;
	TopicSubscriber tobsubChat;
	CodingSessionModell csEinladung;
	Session session;
	Object lockEinladung;
	String benutzerId;
	boolean change;

	public KommunikationIncoming(String benutzerId, KommunikationStart komser,
			Object lockEinladung) {

		this.session = komser.getSession();
		this.lockEinladung = lockEinladung;
		this.benutzerId = benutzerId;
		this.komser = komser;
	}

	public void bekommeCode(String topic, String benutzer) {
		// hier wartet später das JMS aud Code von Csen
		try {
			topsubCode = session.createDurableSubscriber(komser.getTopicCode(),
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
					if (message instanceof ObjectMessage) {
						synchronized (lockEinladung) {
							try {
								csEinladung = ((CodingSessionModell) ((ObjectMessage) message)
										.getObject());
								lockEinladung.notifyAll();
								message.acknowledge();
							} catch (Exception e) {
								// throw new
								// Exception("Konnte nicht eingeladen werden");
							}
						}
					}
				}
			};
			komser.getTopsubEinladung()
					.setMessageListener(listnerFuerEinladung);
		} catch (Exception e1) {
			// throw new Exception("Konnte den Einlader nicht starten");
		}
	}

	public void bekommeChat(int chatId, LinkedList<String> chatLog) {
		try {
			tobsubChat = session.createDurableSubscriber(komser.getTopicChat(),
					"Chatter" + benutzerId);
			listnerFuerChat = new MessageListener() {
				public void onMessage(Message message) {
					if (message instanceof TextMessage) {
						try {
							chatLog.addLast(message.getStringProperty("sender")
									+ ": " + ((TextMessage) message).getText());
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

	public CodingSessionModell getEinladung() {
		if (csEinladung != null){
			return this.csEinladung;
		}
		else{
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
}