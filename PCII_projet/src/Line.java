
public class Line {
	float x,y,z;
	float X, Y, W;
	float scale;
	float camD = 0.84f ;
	int width = 1280 ;
	int height = 1024;


	public Line() {
		 x=y=z=0 ; 
	}

	public float project() {
		return y /(height/0.2f);
	}
}
