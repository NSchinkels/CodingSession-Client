package businesslogik;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

public class KommunikationOutgoing {
	/**
	 * Klasse die fuer das setztender Topics und der ausgängliche Kommunikation
	 * zuständig ist Fuer Code und Chat gibt es jewals einen Producer der
	 * Nachrichten an das JMS Topic sendet
	 */

	KommunikationStart kommunikationStart;
	MessageProducer producerCode;
	MessageProducer producerChat;
	TextMessage textMessage;
	Session session;
	String benutzerId;

	public KommunikationOutgoing(String benutzerId, KommunikationStart kommunikationStart) {
		this.session = kommunikationStart.getSession();
		this.benutzerId = benutzerId;
		this.kommunikationStart = kommunikationStart;

	}

	/**
	 * Das Topic wird fuer die aktuelle CodingSession gesetz z.B die
	 * CodingSession mit der id 132 hat das Topic CodingSession134
	 * 
	 * @param topic
	 *            id der CodingSession
	 */
	public void starteCs(String topic) {
		try {
			kommunikationStart.setTopicCode(session.createTopic(topic));
			producerCode = session.createProducer(kommunikationStart.getTopicCode());
		} catch (JMSException e) {
			new CodingSessionDialog().erstelleFehlerMeldung("Konnte CodingSession nicht starten");
		}

	}

	/**
	 * Das Topic wird fuer den aktuellen Chat gesetz z.B der Chat mit der id 132
	 * hat das Topic Chat134
	 * 
	 * @param topic
	 *            id des Chats
	 */

	public void starteChat(String topic) {
		try {
			kommunikationStart.setTopicChat(session.createTopic(topic));
			producerChat = session.createProducer(kommunikationStart.getTopicChat());
		} catch (JMSException e) {
			new CodingSessionDialog().erstelleFehlerMeldung("Konnte Chat nicht starten");
		}

	}

	/**
	 * Eine TextMessage mit dem Code wird an das Topic geschrieben
	 * 
	 * @param nachricht
	 *            Code der veroeffentlicht werden soll
	 */

	public void veroeffentlicheCode(String nachricht) {
		try {
			textMessage = session.createTextMessage(nachricht);
			textMessage.setStringProperty("sender", benutzerId);
			producerCode.send(textMessage);
		} catch (JMSException e) {
			new CodingSessionDialog().erstelleFehlerMeldung("Ihr Code konnte nicht veroeffentlicht werden");
		}

	}

	/**
	 * Eine TextMessage mit der Nachricht und dem Sender wird an das Topic
	 * geschrieben
	 * 
	 * @param nachricht
	 *            Nachricht die veroeffentlicht werden soll
	 * @param sender
	 *            Sender der Nachricht
	 * 
	 */

	public void veroeffentlicheChat(String nachricht, String sender) {
		try {
			textMessage = session.createTextMessage(nachricht);
			textMessage.setStringProperty("sender", sender);
			producerChat.send(textMessage);
		} catch (JMSException e) {
			new CodingSessionDialog().erstelleFehlerMeldung("Konnte ihre Nachricht nicht senden");
		}

	}

	/**
	 * Eine ObjectMessage mit dem CodingSessionModell wird an das Topic
	 * geschrieben
	 * 
	 * @param cs
	 *            Das CodingSessionModell was an den andern Benutzer gesendet
	 *            werden soll
	 * @param freundEmail
	 *            Email des Empfängers
	 */

	public void ladeEin(CodingSessionModell cs, String freundEmail) {
		try {
			ObjectMessage om = session.createObjectMessage(cs);
			om.setStringProperty("id", freundEmail);
			kommunikationStart.getProducerEinladung().send(om);
		} catch (Exception e2) {
			new CodingSessionDialog().erstelleFehlerMeldung("Konnte nicht einladen");
		}

	}

	public void beenden() {
		try {
			producerCode.close();
			producerChat.close();
		} catch (Exception e) {
		}
	}

}
