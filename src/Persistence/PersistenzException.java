package Persistence;

/**
 * Exception die geworfen werden soll wenn es DB Probleme gibt 
 *
 */
public class PersistenzException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PersistenzException(){
		super();
	}
	
	PersistenzException(String message){
		super(message);
	}                                  
}
