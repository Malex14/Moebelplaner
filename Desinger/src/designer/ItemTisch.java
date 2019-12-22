package designer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

public class ItemTisch extends Moebel{

	public ItemTisch(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 80;
		y = 60;
		origWidth = width = 120;
		origHeight = height = 80;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Tisch.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}
}