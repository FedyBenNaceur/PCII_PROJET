
import java.util.ArrayList;
import java.util.Random;



/*Classe qui represente la piste */
public class Road {
	private Affichage panel;
	public float middlePoint = 0.5f;/*le milieu de la piste*/
	public final float roadWidth = 0.5f;/*la largeur de la piste en pourcentage par rapport a la largeur de la fenetre*/
	public final float clipWidth = roadWidth * 0.15f;/*la taille des bordures*/
	public final int segLength = 1;//taille des segements de la piste
	public int sizeR;
	private ArrayList<Line> lines = new ArrayList<Line>();//stocker les valeurs des lignes qui representes la piste
	public ArrayList<Pair> curvs = new ArrayList<Pair>()  ;
	private float offset = 0 ; 
	public int tracksection = 0 ; 
	private final int trackLength = 20 ;
	public float curvature ;

	//Constructeur de la class road
	public Road() {
		sizeR = 0;
		curvature = 0 ;
	}

	/**
	 * Procedure qui initialise la route
	 */
	private void initLines() {
		int i = 0;
		initialiseCurvs();

		int cond = 0;
		while (cond < panel.HEIGHT - panel.HEIGHT/5) {
			Line line = new Line();
			line.y = panel.HEIGHT - (i * segLength);
			cond = i * segLength;
			lines.add(line);
	
			i++;
		}
		sizeR = lines.size();
	}
	
	public void findPos() {
		while(tracksection<curvs.size() && offset<=panel.vehicule.distance ) {
			offset += curvs.get(tracksection).getScd() ; 
			tracksection ++ ;			
		}
	}
	
	
	private void initialiseCurvs() {
	    int i = 0 ; 
	    while (i<trackLength) {
	    	Random m = new Random();
	    	float c = (float) m.nextInt(1+1+1)-1 ;
	    	Pair p = new Pair(c,(i+1)*10);
	    	curvs.add(p);
	    	i++ ;
	    }

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
	    		   System.out.print("out");
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
	
	public void updateCurvature(float diff) {
	    this.curvature += diff ;  
	    middlePoint =  0.5f + this.curvature;
	    panel.repaint();
		
	}


}