package businesslogik;

import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.TopicSubscriber;

public class KommunikationIncoming extends Kommunikation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String neuerCode="";
	MessageListener listnerFuerCode;
	MessageListener listnerFuerEinladung;
	TopicSubscriber topsubCode;
	TopicSubscriber topsubEinladung;
	HashMap<String, String> csEinladung;
	Object lockCode;
	Object lockEinladung;

	public KommunikationIncoming(Object lockCode, Object lockEinladung) {
		super(lockCode, lockEinladung);
		this.lockCode = lockCode;
		this.lockEinladung = lockEinladung;
		try {

		} catch (Exception e2) {

		}
	}

	public void bekommeCode(String topic, int benutzer) {
		// hier wartet später das JMS aud Code von Csen
		try {
			topicCode = session.createTopic(topic);
			topsubCode = session.createDurableSubscriber(topicCode,"Benutzer"+benutzer);
			System.out.println("Empfänger "+ benutzer+" gestartet");
			listnerFuerCode = new MessageListener() {
				public void onMessage(Message message) {
					if (message instanceof TextMessage) {
						try {
							synchronized (lockCode) {
								if (!neuerCode.equals(((TextMessage) message).getText())&&message.getIntProperty("sender")!=benutzer) {
									neuerCode = ((TextMessage) message).getText();
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

	public void bekommeEinladung(int id) {
		try {
			topicEinladung = session.createTopic("Einladung");
			topsubEinladung = session.createDurableSubscriber(topicEinladung,
					"einlader", "id = " + id, false);
			listnerFuerEinladung = new MessageListener() {
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
			topsubEinladung.setMessageListener(listnerFuerEinladung);
			System.out.println("Empfänger für om gestartet");
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