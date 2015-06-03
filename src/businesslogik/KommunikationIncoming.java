package businesslogik;

import java.util.HashMap;
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
	HashMap<String, String> csEinladung;
	Session session;
	Object lockCode;
	Object lockEinladung;
	int benutzerId;

	public KommunikationIncoming(int benutzerId,KommunikationStart komser, Object lockCode,
			Object lockEinladung) {

		this.session=komser.getSession();
		this.lockCode = lockCode;
		this.lockEinladung = lockEinladung;
		this.benutzerId=benutzerId;
		this.komser = komser;
	}

	public void bekommeCode(String topic, int benutzer) {
		// hier wartet später das JMS aud Code von Csen
		try {
			topsubCode = session.createDurableSubscriber(komser.getTopicCode(), "Benutzer"+ benutzer);
			System.out.println("Empfänger " + benutzer + " gestartet");
			listnerFuerCode = new MessageListener() {
				public void onMessage(Message message) {
					if (message instanceof TextMessage) {
						try {
							synchronized (lockCode) {
								if (!neuerCode.equals(((TextMessage) message)
										.getText())
										&& message.getIntProperty("sender") != benutzer) {
									neuerCode = ((TextMessage) message)
											.getText();
									lockCode.notify();
								}
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch blockCode
							lockCode.notifyAll();
							e1.printStackTrace();
						}
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
				@SuppressWarnings("unchecked")
				public void onMessage(Message message) {
					System.out.println("OM bekommen");
					if (message instanceof ObjectMessage) {
						// System.out.println("OM bekommen");
						synchronized (lockEinladung) {
							lockEinladung.notifyAll();
							try {
								csEinladung = ((HashMap<String, String>) ((ObjectMessage) message)
										.getObject());
								System.out.println(csEinladung.get("id"));
								message.acknowledge();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			};
			komser.getTopsubEinladung().setMessageListener(listnerFuerEinladung);
			System.out.println("Empfänger für om gestartet");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void bekommeChat(int chatId, LinkedList<String> chatLog) {
		try {
			tobsubChat = session.createDurableSubscriber(komser.getTopicChat(), "Chatter"+ benutzerId);
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

	public HashMap<String, String> getEinladung() {
		return this.csEinladung;
	}

	public String getCode() {
		return this.neuerCode;
	}
}