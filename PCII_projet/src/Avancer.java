

public class Avancer extends Thread{
	private int time_to_sleep = 300 ;
	private Affichage panel ;
	
	public Avancer (Affichage p) {
		super("Avancer");
		panel = p ;
	}
	@Override
	public void run() {
		try {
			panel.road.setPos();
			panel.repaint();
			Thread.sleep(time_to_sleep);
		}catch (Exception e ) {
			e.printStackTrace();
		}
	}

}
