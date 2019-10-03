package desinger;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;

public class TestObjekt extends Moebel{
	
	public TestObjekt(Canvas c, String... objName) {
		name = objName[0]; 
		canvas = c;
		x = (int)(Math.random()* Gui.getCanvas().getBounds().width);
		y = (int)(Math.random()* Gui.getCanvas().getBounds().height);
		width = 50;
		height = 100;
		angle = (float)(Math.random()* 360);
		image = new Image(device, "./assets/moebel/testObjekt.png");
		draw();
		addToTree(Gui.gettrtmMoebel());
	}	
}