import businesslogik.*;

public class NIKOSTESTKLASSEDONTTOUCH {
	CodingSession cs1;
	CodingSession cs2;
	KommunikationIncoming komi1;
	KommunikationIncoming komi2;
	KommunikationOutgoing komo1;
	KommunikationOutgoing komo2;
	KommunikationStart ko1;
	KommunikationStart ko2;
	Object lock;
	Object lock2;

	public static void main(String args[]) {
		new NIKOSTESTKLASSEDONTTOUCH();
	}

	public NIKOSTESTKLASSEDONTTOUCH() {
		lock = new Object();
		lock2 = new Object();
		int ben1=(int)(Math.random()*123+342);
		int ben2=(int)(Math.random()*1233242+343453);
		ko1=new KommunikationStart(ben1);
		ko2 = new KommunikationStart(ben2);
		komi1 = new KommunikationIncoming(ben1,ko1,lock, new Object());
		komi2 = new KommunikationIncoming(ben2,ko2,lock2, new Object());
		komo1 = new KommunikationOutgoing(ben1,ko1);
		komo2 = new KommunikationOutgoing(ben2,ko2);
		cs1 = new CodingSession("Wayne", false, komi1, komo1, ben1, 1, lock);
		cs2 = new CodingSession("Wayne", false, komi2, komo2, ben2, 1, lock2);
		new Thread() {
			public void run() {
				new testgui(cs1);
			}
		}.start();

		new testgui(cs2);
		
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		cs2.sendeEinladung(12);
		komi1.bekommeEinladung();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cs1.aktualisiereCode("Benutzer1 schreibt was", true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cs2.getCode());
		cs2.aktualisiereCode("Ebn2", true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cs1.getCode());
		
		
		
	}

}
