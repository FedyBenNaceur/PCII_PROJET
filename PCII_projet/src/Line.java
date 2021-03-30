import java.awt.Graphics;

public class Line {
	    double camD = 1;
		double x, y, z;
		double X, Y, W;
		double scale;
		Graphics graphic;
		int width = 1600;
		int height = 768;
		int roadW = 2000;
		public Line() {
			
		}
		void project(int camX, int camY, int camZ) {
			scale = camD / (z - camZ);
			X = (1 + scale * (x - camX)) * width / 2;
			Y = (1 - scale * (y - camY)) * height / 2;
			W = scale * roadW * width / 2;
		}
	
	  /*  double cameraX,cameraY,cameraZ = 0 ;
	    double screenScale = 0 ;
	    double worldX ,worldY,worldZ = 0; 
	    double screenX ,screenY,screenW = 0;
	    
        void project (double camX ,double camY ,double camZ,double cameraDepth,double width,double height,double roadWidth) {
	    	cameraX =  camX ; 
	    	cameraY =  camY ; 
	    	cameraY = worldZ - camZ ;
	    	screenScale = cameraDepth/cameraZ ;
	    	screenX = width/2 + (screenScale * cameraX*width/2) ;
	    	screenY = height/2 + (screenScale * cameraY *height/2) ; 
	    	screenW = screenScale *roadWidth * width/2 ;
	    	
	    	
	    }
	    */

	}
	 

