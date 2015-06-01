import javax.swing.JFrame;
import javax.swing.JTextArea;

import businesslogik.CodingSession;
import businesslogik.KommunikationIncoming;
import businesslogik.KommunikationOutgoing;

public class testgui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea ar = new JTextArea();
	CodingSession cs;
	Object lock1 = new Object();
	Object lock2 = new Object();
	int id1 =(int) (Math.random() * 123123 + 1);

	public testgui() {
		super();
		setSize(200, 200);
		add(ar);
		KommunikationIncoming ki= new KommunikationIncoming(lock1,lock2);
		cs = new CodingSession("Title", true, ki, new KommunikationOutgoing(lock1, lock2),
				id1, 12, lock1);
		new Thread() {
			public void run() {
				while (true) {
					try{
						if(cs.hasChanged()){
							System.out.println("Change Textarea "+id1);
							ar.setText(cs.getCode());
						}else{
							cs.neuerCodeGUI(ar.getText());
							System.out.println("Change Textarea NOT ");
						}
						Thread.sleep((int)(Math.random()*2000+1000));
						
						//Thread.sleep((int)(Math.random()*2000+1000));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}.start();
		this.setVisible(true);
	}

}
