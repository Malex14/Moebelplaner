package desinger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;

public class ItemTisch extends Moebel{

	public ItemTisch(Canvas c,String objName) {
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		width = 120;
		height = 80;
		angle = 0;
		image = new Image(device, width+1, height+1);
		GC gc = new GC(image);
		gc.drawRectangle(0, 0, width, height);
		gc.dispose();
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}
	
	
}
