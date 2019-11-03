package desinger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;

public class ItemWaschmaschine extends Moebel{

	public ItemWaschmaschine(Canvas c,String objName) {
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		width = 100;
		height = 100;
		angle = 0;
		image = new Image(device, width+1, height+1);
		GC gc = new GC(image);
		
		gc.drawRectangle(0, 0, width, height);
		gc.drawOval(10, 10, width-20, height-20);
		gc.setBackground(new Color(device, 0,0,0));
		gc.fillOval((width/2)-2, (height/2)-2, 4, 4);
		gc.dispose();
		
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}
