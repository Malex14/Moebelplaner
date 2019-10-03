package desinger;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;

public class ItemrunderTisch extends Moebel{

	public ItemrunderTisch(Canvas c, String... objName) {
		name = objName[0]; 
		canvas = c;
		x = 50;
		y = 50;
		width = 100;
		height = 100;
		angle = 0;
		image = new Image(device, width+1, height+1);
		GC gc = new GC(image);
		gc.drawOval(0, 0, width, height);
		gc.dispose();
		draw();
		addToTree(Gui.gettrtmMoebel());
	}

}
