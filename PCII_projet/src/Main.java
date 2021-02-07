import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		Road r = new Road();
		Car c = new Car();
		Affichage panel = new Affichage(r,c);
		r.initialisePanel(panel);
		c.initPanel(panel);
		//(new Avancer(panel)).start();
		JFrame fenetre = new JFrame("jeu de course");
		fenetre.addKeyListener(c);
		fenetre.add(panel);
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
