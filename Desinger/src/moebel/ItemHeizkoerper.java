package moebel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

import designer.Gui;
import designer.Moebel;

public class ItemHeizkoerper extends Moebel {


	public ItemHeizkoerper(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		origWidth = width = 100;
		origHeight = height = 10;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Heizkoerper.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}