package businesslogik;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

public class KommunikationIncoming {

	public String neuerCode;
	ActiveMQConnectionFactory connectionFactory;
	Session session;
	Destination destination;
	MessageConsumer messageConsumer;
	MessageListener listner;
	Connection connection;
	Topic topic1;
	TopicSubscriber topsub;

	public KommunikationIncoming() {
		neuerCode = new String();
	}

	public synchronized void bekomme(String topic, String selector) {
		// hier wartet später das JMS
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.setClientID(String.valueOf(connection.hashCode()));
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			System.out.println("Verbunden");
			topic1 = session.createTopic(topic);
			String mySelector = selector;
			topsub = session.createDurableSubscriber(topic1, String.valueOf(connection.hashCode()));
			System.out.println("Empfänger gestartet");
			// Message message = messageConsumer.receive(20000);
			listner = new MessageListener() {
				public void onMessage(Message message) {
					try {
						System.out.println(((TextMessage)message).getText());
					} catch (JMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						if (message instanceof TextMessage) {
							TextMessage textMessage = (TextMessage) message;
							message.acknowledge();
							neuerCode = textMessage.getText();
							neuerCode.notifyAll();
							System.out.println("Empfangen");
						}
					} catch (JMSException e) {
						System.out.println("Caught:" + e);
						e.printStackTrace();
					}
				}
			};
			topsub.setMessageListener(listner);
			//System.in.read();
			// messageConsumer.close();
			// session.close();
			// connection.close();
			// test.notify();
		} catch (Exception e1) {

		}
	}

}
