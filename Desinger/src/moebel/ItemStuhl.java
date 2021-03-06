package moebel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

import designer.Gui;
import designer.Moebel;

public class ItemStuhl extends Moebel {


	public ItemStuhl(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		origWidth = width = 45;
		origHeight = height = 45;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Stuhl.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}