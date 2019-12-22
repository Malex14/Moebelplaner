package moebel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

import designer.Gui;
import designer.Moebel;

public class ItemSchrankelement extends Moebel {


	public ItemSchrankelement(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 70;
		y = 50;
		origWidth = width = 110;
		origHeight = height = 60;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Schrankelement.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}