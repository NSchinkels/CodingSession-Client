package businesslogik;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicSubscriber;

public class KommunikationIncoming extends Kommunikation{

	public String neuerCode;
	MessageListener listnerFuerCode;
	MessageListener listnerFuerEinladung;
	TopicSubscriber topsubCode;
	TopicSubscriber topsubEinladung;
	CodingSession csEinladung;
	Object lockCode;
	Object lockEinladung;


	public KommunikationIncoming(Object lockCode, Object lockEinladung) {
		super(lockCode,lockEinladung);
		this.lockCode=lockCode;
		this.lockEinladung=lockEinladung;
		try {
			connection = connectionFactory.createConnection();
			// Kreigt natülich noch ne vernünftige id soblad benutzer am laufen
			// sind
			connection.setClientID(String.valueOf(Math.random()));
			connection.start();
		} catch (Exception e2) {

		}
	}

	public void bekommeCode(String topic, String selector) {
		// hier wartet später das JMS aud Code von Csen
		try {
			sessionCode = connection.createSession(false,
					Session.CLIENT_ACKNOWLEDGE);
			//System.out.println("Verbunden");
			topicCode = sessionCode.createTopic(topic);
			topsubCode = sessionCode.createDurableSubscriber(topicCode,
					String.valueOf(connection.hashCode()));
			System.out.println("Empfänger gestartet");
			listnerFuerCode = new MessageListener() {
				public void onMessage(Message message) {
					if (message instanceof TextMessage) {
						try {
							synchronized (lockCode) {
								neuerCode = ((TextMessage) message).getText();
								lockCode.notify();
							}
						} catch (JMSException e1) {
							// TODO Auto-generated catch blockCode
							lockCode.notifyAll();
							e1.printStackTrace();
						}
					}
				}
			};
			topsubCode.setMessageListener(listnerFuerCode);
			// messageConsumer.close();
			// session.close();
			// connection.close();
		} catch (Exception e1) {
		}
	}

	public void bekommeEinladung() {
		try {
			sessionEinladung = connection.createSession(false,
					Session.CLIENT_ACKNOWLEDGE);
			System.out.println("Verbunden");
			topicEinladung = sessionEinladung.createTopic("Einladungen");
			// topsubEinladung =
			// sessionEinladung.createDurableSubscriber(topicEinladung,"Subname","selector",true);
			topsubEinladung = sessionEinladung.createDurableSubscriber(
					topicEinladung, "einlader");
			System.out.println("Empfänger gestartet");
			listnerFuerEinladung = new MessageListener() {
				public void onMessage(Message message) {
					if (message instanceof ObjectMessage) {
						System.out.println("OM bekommen");
						synchronized (lockEinladung) {
							csEinladung = (CodingSession) message;
							lockEinladung.notifyAll();
							System.out.println(csEinladung.getCode());
						}
					}
				}
			};
			topsubCode.setMessageListener(listnerFuerCode);
		} catch (Exception e1) {

		}
	}
	public CodingSession getEinladung(){
		return this.csEinladung;
	}
	public String getCode(){
		return this.neuerCode;
	}

}