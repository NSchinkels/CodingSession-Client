package businesslogik;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

//KOMENTARE SPÄTER,ALLES NOCH WIP
public abstract class Kommunikation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ActiveMQConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Topic topicCode;
	Topic topicEinladung;

	public Kommunikation(Object lockCode, Object lockEinladung) {
		try {
			connectionFactory = new ActiveMQConnectionFactory();
			connectionFactory.setBrokerURL("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			// Kreigt natülich noch ne vernünftige id soblad benutzer am laufen
			// sind
			connection.setClientID(String.valueOf(Math.random()));
			connection.start();
			session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void beenden() {
		try {
			session.close();
			connection.close();
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
	}

}