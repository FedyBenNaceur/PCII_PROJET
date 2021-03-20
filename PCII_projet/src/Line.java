
public class Line {
      public int  y = 0 ;
      public int height = 768 ;
      public int width = 1600 ;

	
	  public Line() { y = 0; }
	 
	  public float project() { return (float) (y / (height / 0.2f)); }
	 


}
