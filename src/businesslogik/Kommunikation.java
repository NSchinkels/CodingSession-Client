package businesslogik;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

//KOMENTARE SPÄTER,ALLES NOCH WIP
public abstract class Kommunikation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ActiveMQConnectionFactory connectionFactory;
	Connection connection;
	Session sessionCode;
	Session sessionEinladung;
	Topic topicCode;
	Topic topicEinladung;

	public Kommunikation(Object lockCode,Object lockEinladung) {
		connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
	}

}