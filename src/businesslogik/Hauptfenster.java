package businesslogik;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class Hauptfenster {
	KommunikationStart com;
	KommunikationIncoming comi;
	KommunikationOutgoing como;
	int benId;
	int csId;
	Object lock;
	CodingSession cs;
	
	private Button btnTest;
	
	public void testMethode(ActionEvent event){
		benId=(int)(Math.random()*123123)+1;
		com=new KommunikationStart(benId);
		comi=new KommunikationIncoming(benId, com, lock, new Object());
		como=new KommunikationOutgoing(benId, com);
		lock=new Object();
		
		try{
			((Node) (event.getSource())).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/codingsession.fxml"));
			cs =new CodingSession("huhu", false, comi, como, benId, 1, lock);
			loader.setController(cs);
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("Login");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
