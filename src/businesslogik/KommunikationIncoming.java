package businesslogik;

import java.util.LinkedList;
import java.util.List;

import javafx.application.Platform;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicSubscriber;

public class KommunikationIncoming {
	/**
	 * Diese Klasse ist fuer die einkommende Kommunikation mit dem JMS. Sie
	 * verwaltet die Einladungen, den Code und den Chat Diese drei Komponeten
	 * haben jewals einen TopicSubscriber und MessageListner
	 */

	public String neuerCode = "";
	KommunikationStart kommunikationStart;
	MessageListener listnerFuerCode;
	MessageListener listnerFuerEinladung;
	MessageListener listnerFuerChat;
	TopicSubscriber topsubCode;
	TopicSubscriber topsubEinladung;
	TopicSubscriber tobsubChat;
	static CodingSessionModell csEinladung;
	Session session;
	String benutzerId;
	boolean change;

	public KommunikationIncoming(String benutzerId, KommunikationStart kommunikationStart) {

		this.session = kommunikationStart.getSession();
		this.benutzerId = benutzerId;
		this.kommunikationStart = kommunikationStart;
	}

	/**
	 * Der Subscriber fuer den neuen Code
	 * 
	 * @param topic
	 *            Das Topic in der der Code fuer die CodingSession gepostet wird
	 * @param benutzer
	 *            Email des Benutzers
	 */
	public void bekommeCode(String topic, String benutzer) {
		// wenn eine Cs schon gestartet wurde wird diese zuerst geloescht
		if (topsubCode != null) {
			try {
				topsubCode.close();
			} catch (JMSException e) {
				new CodingSessionDialog().erstelleFehlerMeldung("Sie konnten sich nicht an der CodingSession anmdelden");
			}
		}
		try {
			// Der Subscriber wird von der Session erstellt
			topsubCode = session.createDurableSubscriber(kommunikationStart.getTopicCode(), "Benutzer" + benutzer);
			listnerFuerCode = new MessageListener() {
				public void onMessage(Message message) {
					try {
						// Wenn neuer Code von einemandernbenutzer kommt wird
						// dieser gespeichert
						if (!neuerCode.equals(((TextMessage) message).getText()) && message.getStringProperty("sender") != benutzer) {
							neuerCode = ((TextMessage) message).getText();
							change = true;
						}
					} catch (JMSException e) {
						new CodingSessionDialog().erstelleFehlerMeldung("Sie konnten sich nicht an der CodingSession anmdelden");
					}
				}
			};
			topsubCode.setMessageListener(listnerFuerCode);
		} catch (Exception e1) {
		}
	}

	/**
	 * Sobald der benutzer eine Einladung bekommt wird ein Dialog aufgerufen und
	 * das gesendete CodingSessionModell als aktive CodingSession gesetzt wenn
	 * der Benutzer uebereinstimmt und er den Dialog posetiv bentwortet Der
	 * Thread im Hauptfenster wird informiert
	 */
	public void bekommeEinladung() {
		try {
			listnerFuerEinladung = new MessageListener() {
				public void onMessage(Message message) {
					try {
						if (message.getStringProperty("id").equals(ControllerMediator.getInstance().getBenutzerkonto().getEmail())) {
							csEinladung = ((CodingSessionModell) ((ObjectMessage) message).getObject());
							//Dialoge muessenueber den JavaFX Thread gestartet werden
							Platform.runLater(new Runnable() {
								public void run() {
									System.out.println("einladung run");
									new CodingSessionDialog().erstelleEinladungDialog(csEinladung.getBenutzerMail());
								}
							});
						}
					} catch (Exception e) {
						new CodingSessionDialog().erstelleFehlerMeldung("Sie koennen nicht eingeladen werden");
					}
				}
			};
			kommunikationStart.getTopsubEinladung().setMessageListener(listnerFuerEinladung);
		} catch (Exception e1) {
			new CodingSessionDialog().erstelleFehlerMeldung("Sie koennen nicht eingeladen werden");
			e1.printStackTrace();
		}
	}

	/**
	 * Aehnlich bekommeCode, nur werden alle Nachrichten empfangen und an eine
	 * Liste gehangen
	 * 
	 * @param chatId
	 *            die id des Chats
	 * @param chatLog
	 *            eine liste an die neue nachrichten angehangen werden
	 */
	public void bekommeChat(int chatId, List<String> chatLog) {
		if (tobsubChat != null) {
			try {
				tobsubChat.close();
			} catch (JMSException e) {
				new CodingSessionDialog().erstelleFehlerMeldung("Sie konnten sich nicht am Chat anmdelden");
			}
		}
		try {
			tobsubChat = session.createDurableSubscriber(kommunikationStart.getTopicChat(), "Chatter" + benutzerId);
			listnerFuerChat = new MessageListener() {
				public void onMessage(Message message) {
					if (message instanceof TextMessage) {
						try {
							((LinkedList<String>) chatLog).addLast(message.getStringProperty("sender") + ": " + ((TextMessage) message).getText());
						} catch (JMSException e) {
							new CodingSessionDialog().erstelleFehlerMeldung("Sie konnten sich nicht am Chat anmdelden");
						}
					}
				}

			};
			tobsubChat.setMessageListener(listnerFuerChat);
		} catch (Exception e1) {
			new CodingSessionDialog().erstelleFehlerMeldung("Sie konnten sich nicht am Chat anmdelden");
		}

	}

	public static CodingSessionModell getEinladung() {
		if (csEinladung != null) {
			return csEinladung;
		} else {
			// throw new Exception("Konnte den Einlader nicht starten");
			return null;
		}
	}

	public String getCode() {
		change = false;
		return this.neuerCode;
	}

	public boolean hasChanged() {
		return change;
	}

	public void beenden() {
		try {
			topsubEinladung.close();
			topsubCode.close();
			tobsubChat.close();
			kommunikationStart.beenden();
		} catch (Exception e) {
		}

	}
}