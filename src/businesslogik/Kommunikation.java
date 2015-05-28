package businesslogik;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

//KOMENTARE SPÄTER,ALLES NOCH WIP
public class Kommunikation {
	public String neuerCode;
	ActiveMQConnectionFactory connectionFactory;
	Session session;
	Destination destination;
	MessageConsumer messageConsumer;
	MessageListener listner;
	Connection connection;

	public Kommunikation() {
		neuerCode = new String();
		// Verbindung mit JMS
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			System.out.println("Verbunden");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized void veröffentliche(String nachricht, String topic, String sender) {
		// code an topic geschrieben
		try {
			Destination destination = session.createTopic(topic);
			javax.jms.MessageProducer producer = session
					.createProducer(destination);
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

	public synchronized void bekomme(String topic, String selector) {
		// hier wartet später das JMS
		try {
			destination = session.createTopic(topic);
			String mySelector = selector;
			messageConsumer = session.createConsumer(destination, mySelector);
			System.out.println("Empfänger gestartet");
			// Message message = messageConsumer.receive(20000);
			listner = new MessageListener() {
				public void onMessage(Message message) {
					try {
						if (message instanceof TextMessage) {
							TextMessage textMessage = (TextMessage) message;
							neuerCode=textMessage.getText();
							neuerCode.notifyAll();
							System.out.println("Empfangen");
						}
					} catch (JMSException e) {
						System.out.println("Caught:" + e);
						e.printStackTrace();
					}
				}
			};

			messageConsumer.setMessageListener(listner);
			while(true){}
			// messageConsumer.close();
			// session.close();
			// connection.close();
			// test.notify();

		} catch (Exception e1) {

		}
	}

	public String getNeuerCode() {
		return "TEST";
	}

}
