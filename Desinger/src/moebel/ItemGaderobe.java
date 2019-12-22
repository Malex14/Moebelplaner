package moebel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

import designer.Gui;
import designer.Moebel;

public class ItemGaderobe extends Moebel {


	public ItemGaderobe(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		origWidth = width = 140;
		origHeight = height = 20;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Gaderobe.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}