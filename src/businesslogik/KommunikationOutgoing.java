package businesslogik;

import java.io.Serializable;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

public class KommunikationOutgoing extends Kommunikation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MessageProducer producerCode;
	TextMessage textMessage;

	public KommunikationOutgoing(Object lockCode,Object lockEinladung) {
		super(lockCode,lockEinladung);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			//System.out.println("Verbunden");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void veröffentlicheCode(String nachricht, String topic, String sender) {
		// code an topic geschrieben
		try {
			sessionCode = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination topicCode = sessionCode.createTopic(topic);
			producerCode = sessionCode.createProducer(topicCode);
			producerCode.setDeliveryMode(DeliveryMode.PERSISTENT);
			textMessage = sessionCode.createTextMessage(nachricht);
			producerCode.send(textMessage);
			//System.out.println("Veröffentlicht");
			// sessionCode.close();
			// connection.close();
		} catch (Exception e2) {

		}

	}

	public void ladeEin(CodingSession cs,String freund) {
		try {
			sessionEinladung = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			topicEinladung = sessionEinladung.createTopic("Einladung");
			producerCode = sessionEinladung.createProducer(topicEinladung);
			producerCode.setDeliveryMode(DeliveryMode.PERSISTENT);
			ObjectMessage om=sessionEinladung.createObjectMessage(cs);
			System.out.println("om erstellt");
			producerCode.send(om);
			System.out.println("Veröffentlicht");
			// sessionCode.close();
			// connection.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}
}
