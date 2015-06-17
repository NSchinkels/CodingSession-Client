package Persistence;

/**
 * Exception die geworfen werden soll wenn es DB Probleme gibt 
 *
 */
public class PersistenzException extends Exception{
	PersistenzException(){
		super();
	}
	
	PersistenzException(String message){
		super(message);
	}                                  
}
