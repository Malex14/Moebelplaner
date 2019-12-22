package moebel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

import designer.Gui;
import designer.Moebel;

public class ItemFluegel extends Moebel {


	public ItemFluegel(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 130;
		y = 100;
		origWidth = width = 200;
		origHeight = height = 150;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Fluegel.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}