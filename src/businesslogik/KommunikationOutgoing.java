package businesslogik;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class KommunikationOutgoing {
	ActiveMQConnectionFactory connectionFactory;
	Session session;
	Destination destination;
	MessageConsumer messageConsumer;
	MessageListener listner;
	Connection connection;

	public KommunikationOutgoing() {
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

	public synchronized void veröffentliche(String nachricht, String topic,String sender) {
		// code an topic geschrieben
		try {
			Destination destination = session.createTopic(topic);
			javax.jms.MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage textMessage = session.createTextMessage("Hallo");
			textMessage.setStringProperty("sender", sender);
			textMessage.setJMSCorrelationID("1234");
			producer.send(textMessage);
			System.out.println("Veröffentlicht");
			// session.close();
			// connection.close();
		} catch (Exception e2) {

		}

	}
}
