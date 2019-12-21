package designer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

public class ItemBett extends Moebel {

	public ItemBett(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		origWidth = width = 220;
		origHeight = height = 160;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Bett.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}