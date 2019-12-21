package designer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

public class ItemNachttisch extends Moebel {


	public ItemNachttisch(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		origWidth = width = 40;
		origHeight = height = 40;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Nachttisch.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}