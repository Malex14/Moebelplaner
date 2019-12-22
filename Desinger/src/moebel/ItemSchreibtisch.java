package moebel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

import designer.Gui;
import designer.Moebel;

public class ItemSchreibtisch extends Moebel {

	public ItemSchreibtisch(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 100;
		y = 70;
		origWidth = width = 180;
		origHeight = height = 90;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Schreibtisch.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}
