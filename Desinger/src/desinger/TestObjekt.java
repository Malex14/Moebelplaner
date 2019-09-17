package desinger;

import org.eclipse.swt.graphics.Image;

public class TestObjekt extends moebel{
int size_x;
int size_y;
int rotation;

	public TestObjekt() {
		x = (int)(Math.random()* gui.getCanvas().getBounds().width);
		y = (int)(Math.random()* gui.getCanvas().getBounds().height);
		width = 50;
		height = 50;
		angle = (float)(Math.random()* 360);
		draw(new Image(device, "./assets/moebel/testObjekt.png"), gui.getCanvas());
	}
	
	
}

