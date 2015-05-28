package businesslogik;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class KommunikationIncoming {

	public String neuerCode;
	ActiveMQConnectionFactory connectionFactory;
	Session session;
	Destination destination;
	MessageConsumer messageConsumer;
	MessageListener listner;
	Connection connection;

	public KommunikationIncoming() {
		neuerCode = new String();
		// Verbindung mit JMS
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			System.out.println("Verbunden");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
