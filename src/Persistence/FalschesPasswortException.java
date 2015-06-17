package Persistence;

public class FalschesPasswortException extends Exception{
	FalschesPasswortException(){
		super();
	}
	
	FalschesPasswortException(String message){
		super(message);
	}                                 
}
