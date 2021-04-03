
public class Avancer extends Thread {
	private int time_to_sleep = 50;
	private Affichage panel;

	public Avancer(Affichage p) {
		super("Avancer");
		panel = p;
	}

	@Override
	public void run() {
		try {
			while (true) {
				/*if (panel.vehicule.speed - panel.vehicule.acceleration < 0) {
					panel.vehicule.speed = 0;
				} else {
					panel.vehicule.speed -= panel.vehicule.acceleration;
				}*/
				if (panel.vehicule.speed + panel.vehicule.acceleration<= panel.vehicule.maxSpeed) {
					panel.vehicule.speed +=panel.vehicule.acceleration;
				}else { 
					panel.vehicule.speed =  panel.vehicule.maxSpeed ;
				}
				panel.vehicule.distance += panel.vehicule.speed;
				panel.repaint();
				Thread.sleep(time_to_sleep);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
