package desinger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

public class ItemSchreibtisch extends Moebel {

	public ItemSchreibtisch(Canvas c,String objName) {
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		width = 180;
		height = 90;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Schreibtisch.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}
