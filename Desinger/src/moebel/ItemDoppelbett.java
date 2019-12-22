package moebel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

import designer.Gui;
import designer.Moebel;

public class ItemDoppelbett extends Moebel {


	public ItemDoppelbett(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 130;
		y = 130;
		origWidth = width = 210;
		origHeight = height = 210;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Doppelbett.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}