package desinger;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

public class TestObjekt extends moebel{
int size_x;
int size_y;
int rotation;
	public TestObjekt() {
		Device device = Display.getCurrent();
		x = 20;
		y = 20;
		width = 50;
		height = 50;
		angle = 0;
		color = new Color(device, 0,0,0);
	}
	
	
}

