package designer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

public class ItemBadewanne extends Moebel {


	public ItemBadewanne(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		origWidth = width = 180;
		origHeight = height = 80;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Badewanne.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}