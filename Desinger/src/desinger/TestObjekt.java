package desinger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;

public class TestObjekt extends Moebel{
	
	public TestObjekt(Canvas c,String objName) {
		Gui.sethasChanged(true);
		name = objName; 
		canvas = c;
		x = (int)(Math.random()* Gui.getCanvas().getBounds().width);
		y = (int)(Math.random()* Gui.getCanvas().getBounds().height);
		width = 50;
		height = 100;
		angle = (float)(Math.random()* 360);
		 
		image = SWTResourceManager.getImage(Gui.class, "/moebel/testObjekt.png");
		draw();
		addToTree(Gui.gettrtmMoebel(),SWT.NONE);
	}	
}