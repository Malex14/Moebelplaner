package designer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

public class ItemrunderTisch extends Moebel{

	public ItemrunderTisch(Canvas c,String objName) {
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		width = 100;
		height = 100;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Tisch_rund.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}
