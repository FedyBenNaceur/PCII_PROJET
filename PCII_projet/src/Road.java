
import java.util.ArrayList;


/*Classe qui represente la piste */
public class Road {
	private Affichage panel;
	public final float middlePoint = 0.5f;/*le milieu de la piste*/
	public final float roadWidth = 0.8f;/*la largeur de la piste en pourcentage par rapport a la largeur de la fenetre*/
	public final float clipWidth = roadWidth * 0.15f;/*la taille des bordures*/
	public final int segLength = 10;//taille des segements de la piste
	public int sizeR;
	private ArrayList<Line> lines = new ArrayList<Line>();//stocker les valeurs des lignes qui representes la piste
	private int pos = 0;//la position sur la piste

	//Constructeur de la class road
	public Road() {
		sizeR = 0;
	}

	/**
	 * Procedure qui initialise la route
	 */
	private void initLines() {
		int i = 0;
		float y = 0;
		int cond = 0;
		while (cond < panel.HEIGHT / 2) {
			Line line = new Line();
			line.y = panel.HEIGHT - (i * segLength);
			cond = i * segLength;
			lines.add(line);
			y = line.y;
			i++;
		}
		sizeR = lines.size();
	}
	
	
    /**
     * getlines 
     * @return les lignes a dessiner sur l'ecran
     */
	public ArrayList<Line> getLines() {
	       ArrayList<Line>res =new ArrayList<Line>();
	       for (Line l : lines) {
	    	   if (l.y>=0) {
	    		   res.add(l);
	    	   }
	       }
	       return res ;
	}
	
	/**
	 * Fonction pour synchroniser la piste et la vue
	 * @param p l'affichage
	 */

	public void initialisePanel(Affichage p) {
		panel = p;
		initLines();
	}
	
	/*methode qui ajoute une ligne */

	public void addLigne() {
		Line last = lines.get(sizeR - 1);
		Line newLine = new Line();
		newLine.y = last.y;
		lines.add(newLine);
		for (Line l : lines) {
			l.y -= segLength;
		}
		// lines.remove(0);
		panel.repaint();

	}

	public void setPos() {
		pos += 200;
		// addLigne();
	}

}