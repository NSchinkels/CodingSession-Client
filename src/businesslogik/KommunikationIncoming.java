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
	Object lock;

	public KommunikationIncoming(Object lock) {
		neuerCode = new String();
		this.lock=lock;
	}

	public void bekomme(String topic, String selector) {
		// hier wartet später das JMS
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.setClientID(String.valueOf(Math.random()));
			connection.start();
			session = connection.createSession(false,
					Session.CLIENT_ACKNOWLEDGE);
			System.out.println("Verbunden");
			topic1 = session.createTopic(topic);
			String mySelector = selector;
			topsub = session.createDurableSubscriber(topic1,
					String.valueOf(connection.hashCode()));
			System.out.println("Empfänger gestartet");
			listner = new MessageListener() {
				public void onMessage(Message message) {
					try {
						synchronized (lock) {
							System.out.println(((TextMessage) message)
									.getText());
							neuerCode = ((TextMessage) message).getText();
							lock.notify();
						}
					} catch (JMSException e1) {
						// TODO Auto-generated catch block
						lock.notifyAll();
						e1.printStackTrace();
					}
					lock.notifyAll();
				}
			};
			topsub.setMessageListener(listner);
			// messageConsumer.close();
			// session.close();
			// connection.close();
		} catch (Exception e1) {
		}
	}

}
