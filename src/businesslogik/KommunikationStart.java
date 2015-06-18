package businesslogik;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

//Diese Klasse verwaltet die Connection und Session zum Server, sowie die Topics und erstellt die Producer und Subsciber für
//Einladungen, da das Topic sich da nich ändert

public class KommunikationStart {

	ActiveMQConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Topic topicCode;
	Topic topicEinladung;
	Topic topicChat;
	MessageProducer producerEinladung;
	TopicSubscriber topsubEinladung;
	String benutzerId;

	
	public KommunikationStart(String benutzerId) {
		this.benutzerId=benutzerId;
		try {
			connectionFactory = new ActiveMQConnectionFactory();
			connectionFactory.setBrokerURL("tcp://84.200.247.147:61616");
			connection = connectionFactory.createConnection();
			connection.setClientID("Benutzer: "+benutzerId);
			connection.start();
			session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			topicEinladung = session.createTopic("Einladung");
			producerEinladung = session.createProducer(topicEinladung);
			producerEinladung.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			topsubEinladung = session.createDurableSubscriber(topicEinladung,"einlader: "+benutzerId, "id = " + benutzerId, false);
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
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	public MessageProducer getProducerEinladung() {
		return producerEinladung;
	}

	public Topic getTopicCode() {
		return topicCode;
	}

	public void setTopicCode(Topic topicCode) {
		this.topicCode = topicCode;
	}

	public Topic getTopicChat() {
		return topicChat;
	}

	public void setTopicChat(Topic topicChat) {
		this.topicChat = topicChat;
	}

	public TopicSubscriber getTopsubEinladung() {
		return topsubEinladung;
	}
}