package Persistence;

/**
 * Exception die geworfen werden soll wenn eine E-Mail schon vorhanden ist
 *
 */
public class EmailVorhandenException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	EmailVorhandenException(){
		super();
	}
	
	EmailVorhandenException(String message){
		super(message);
	}                                  
}
