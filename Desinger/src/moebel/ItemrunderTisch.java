package moebel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

import designer.Gui;
import designer.Moebel;

public class ItemrunderTisch extends Moebel{

	public ItemrunderTisch(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 80;
		y = 80;
		origWidth =width = 100;
		origHeight = height = 100;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Tisch_rund.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}
}