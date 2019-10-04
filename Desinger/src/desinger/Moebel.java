package desinger;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;

public class Moebel {

protected int x;
protected int y;
protected int width;
protected int height;
protected float angle = 0;
protected Device device = Display.getCurrent();
protected PaintListener paintListener;
protected Canvas canvas;
protected Image image;
protected TreeItem trtm;
protected String name;
protected boolean hasPaintListener = false;
protected boolean highlight = false;

	void setPosition(int new_x, int new_y) {
		x = new_x;
		y = new_y;
		canvas.redraw();
	}
	
	void setAngle(float new_angle) {
		angle = new_angle;
		canvas.redraw();
	}
	
	void setDimensions(int new_width, int new_height) {
		height = new_height;
		width = new_width;
		canvas.redraw();
	}
	
	
	void setX(int new_x) {
		x = new_x;
		canvas.redraw();
	}
	
	void setY(int new_y) {
		y = new_y;
		canvas.redraw();
	}
	
	void setWidth(int new_width) {
		width = new_width;
		canvas.redraw();
	}

	void setHeight(int new_height) {
		height = new_height;
		canvas.redraw();
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	float getAngle() {
		return angle;
	}
	
	int getHeight() {
		return height;
	}
	
	int getWidth() {
		return width;
	}
	
	String getName() {
		return name;
	}
	
	void testMethode() {
		System.out.println(this.toString());
	}
	
	boolean hasPaintListener() {
		return hasPaintListener;
	}
	
	void draw(boolean... highlighted) {
		highlight = highlighted.length > 0 ? highlighted[0] : false; 
		try {
		canvas.removePaintListener(paintListener);
		hasPaintListener = false;
		}catch(Exception e) {}
		canvas.addPaintListener(paintListener = new PaintListener() {
			 public void paintControl(PaintEvent e) {
				 Transform transform = new Transform(device);
				 transform.translate(x, y);
				 transform.rotate((float)angle);
				 transform.translate(-(width/2), -(height/2));
				 e.gc.setTransform(transform);
				 e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
				 if (highlight) {
						e.gc.setForeground(new Color(device,255,0,0));
						e.gc.setLineWidth(2);
						e.gc.drawLine(0, 0, width, height);
						e.gc.drawLine(0, height, width, 0);
						e.gc.drawRectangle(0, 0, width, height);
				}
				 transform.dispose();
				 hasPaintListener = true;
			 }
		});
		canvas.redraw();
	}
	
	void setHighlight(boolean highlighted) {
		highlight = highlighted;
		draw(highlight);
		if(highlight) trtm.setBackground(new Color(device, /*205,232,255*/ 220,220,220)); else trtm.setBackground(new Color(device, 255,255,255));
	}
	
	boolean contains(Point2D point) {
		Rectangle rect = new Rectangle(x, y, width, height);
		AffineTransform t = new AffineTransform();
		t.translate(rect.x, rect.y);
        t.rotate(Math.toRadians(angle));
        t.translate(-(rect.width/2)-x, -(rect.height/2)-y);
		try {
			Point2D tp = t.inverseTransform(point,null);
			if(rect.contains(tp.getX(),tp.getY())) return true;
		} catch (NoninvertibleTransformException e) {}
		return false;
	}
	
	void hide(Canvas canvas) {
		try {
			canvas.removePaintListener(paintListener);
			hasPaintListener = false;
			highlight = false;
		}catch(Exception e) {}
		canvas.redraw();
	}
	
	void addToTree(TreeItem treeItem, int style) {
		trtm = new TreeItem(treeItem,style);
		if (name != null) { 
		trtm.setText(name);
		} else {
			trtm.setText(this.toString());
		}
		
	}
	
	void removeFromTree() {
		trtm = null;
	}
	
	
	boolean isHighlighted() {
		return highlight;
	}
}