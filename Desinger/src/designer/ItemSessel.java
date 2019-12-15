package designer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

public class ItemSessel extends Moebel {

	public ItemSessel(Canvas c,String objName) {
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		origWidth = width = 60;
		origHeight = height = 60;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Sessel.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}