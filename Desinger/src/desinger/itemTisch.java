package desinger;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class itemTisch extends moebel{

	public itemTisch() {
		Device device = Display.getCurrent();
		x = 20;
		y = 20;
		width = 50;
		height = 50;
		angle = 0;

		
	}
	
	
}
