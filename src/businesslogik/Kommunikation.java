package businesslogik;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
//hier wird sobald jms läuft sachen passiern
//jetzt erstmal ziemlich theroitsche methoden
	
public class Kommunikation {
	public Object test; 
	public Kommunikation(){
		this.test=new Object();
		//Verbindung mit JMS
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			connectionFactory.setBrokerURL("tcp://localhost:61616");

			Connection connection = connectionFactory.createConnection();
			connection.start();

			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createTopic("TestTopic");

			String mySelector = "sender is not null AND JMSCorrelationID = 1234";

			MessageConsumer messageConsumer = session
					.createConsumer(destination);

			//Message message = messageConsumer.receive(20000);

	
				MessageListener listner = new MessageListener() {
					public void onMessage(Message message) {
						try {
							if (message instanceof TextMessage) {
								TextMessage textMessage = (TextMessage) message;
								System.out.println("Received message"
										+ textMessage.getText() + "'");
							}
						} catch (JMSException e) {
							System.out.println("Caught:" + e);
							e.printStackTrace();
						}
					}
				};
			
			messageConsumer.setMessageListener(listner);
			//messageConsumer.close();
			//session.close();
			//connection.close();

		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
	        System.in.read();
		} catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void veröffentlichCode(String code){
		//code an topic geschrieben
	}
	public void neuerCode(){
		//hier wartet später das JMS
		test.notify();
	}
	public String getNeuerCode(){
		return "TEST";
	}
	
}
