package moebel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

import designer.Gui;

public class ItemWaschbecken extends designer.Moebel {

	public ItemWaschbecken(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		origWidth = width = 60;
		origHeight = height = 50;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Waschbecken.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}