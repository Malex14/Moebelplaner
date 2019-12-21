package designer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

public class ItemSofa extends Moebel {


	public ItemSofa(Canvas c,String objName, Gui Gui) {
		this.Gui = Gui;
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = 50;
		y = 50;
		origWidth = width = 150;
		origHeight = height = 80;
		image = SWTResourceManager.getImage(Gui.class, "/moebel/Sofa.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}

}