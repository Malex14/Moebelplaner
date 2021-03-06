/*
 *	Möbelplaner - Malte Behrmann 2019
 *	S1 Informatik Fr. Marinescu
 */

package designer;

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
import org.json.JSONObject;


public class Moebel{

protected int x;
protected int y;
protected int origWidth;
protected int origHeight;
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
protected float xScale = 1;
protected float yScale = 1;
protected Gui Gui;

	

	public int getOrigWidth() {
		return origWidth;
	}

	public void setOrigWidth(int origWidth) {
		this.origWidth = origWidth;
	}

	public int getOrigHeight() {
		return origHeight;
	}

	public void setOrigHeight(int origHeight) {
		this.origHeight = origHeight;
	}

	public void setPosition(int new_x, int new_y) {
		setX(new_x);
		setY(new_y);
		Gui.sethasChanged(true);
		canvas.redraw();
	}
	
	public void setAngle(float new_angle) {
		angle = new_angle;
		Gui.sethasChanged(true);
		canvas.redraw();
	}
	
	public void setDimensions(int new_width, int new_height) {
		setHeight(new_height);
		setWidth(new_width);
		Gui.sethasChanged(true);
		canvas.redraw();
	}
	
	
	public void setX(int new_x) {
		x = new_x;
		Gui.sethasChanged(true);
		
		canvas.redraw();
	}
	
	public void setY(int new_y) {
		y = new_y;
		Gui.sethasChanged(true);
		canvas.redraw();
	}
	
	public void setWidth(int new_width) {
		width = new_width;
		Gui.sethasChanged(true);
		canvas.redraw();
	}

	public void setHeight(int new_height) {
		height = new_height;
		Gui.sethasChanged(true);
		canvas.redraw();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public String getName() {
		return name;
	}

	public float getxScale() {
		return xScale;
	}

	public void setxScale(float xScale) {
		this.xScale = xScale;
		width = (int)(origWidth * this.xScale);
		
		canvas.redraw();
	}

	public float getyScale() {
		return yScale;
	}

	public void setyScale(float yScale) {
		this.yScale = yScale;
		height = (int)(origHeight * this.yScale);
		
		canvas.redraw();
	}

	public void testMethode() {
		//System.out.println(this.toString());
	}
	
	public JSONObject getJSON() {
		JSONObject jo = new JSONObject();
		jo.put("name", name);
		jo.put("x", x);
		jo.put("y", y);
		jo.put("width", width);
		jo.put("height", height);
		jo.put("origWidth", origWidth);
		jo.put("origHeight", origHeight);
		jo.put("angle", angle);
		jo.put("hasPaintListener", hasPaintListener);
		jo.put("highlight", highlight);
		jo.put("xScale", xScale);
		jo.put("yScale", yScale);
		jo.put("type", getType());
		return jo;
	}
	
	public String getType() {
		String cl = this.getClass().toString();
		String type[] = cl.split(" ");
		return type[1];
	}
	
	public void setAll(int new_x,int new_y,int new_width,int new_height,int new_origWidth,int new_origHeight,float new_angle,boolean new_hasPaintListener,boolean new_highlight,float new_xScale, float new_yScale) {
		x = new_x;
		y = new_y;
		width = new_width;
		height = new_height;
		origWidth = new_origWidth;
		origHeight = new_origHeight;
		angle = new_angle;
		hasPaintListener = new_hasPaintListener;
		highlight = new_highlight;
		xScale=new_xScale;
		yScale=new_yScale;
		canvas.redraw();
	}
	
	public void setName(String name) {
		this.name = name;
		removeFromTree();
		addToTree(Gui.gettrtmMoebel(), SWT.NONE);
		Gui.getGrpObjektobjektname().setText("Objekt: " + name);
	}

	public boolean hasPaintListener() {
		return hasPaintListener;
	}
	
	public void draw(boolean... highlighted) {
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
						
						info();
						
				}
				 transform.dispose();
				 hasPaintListener = true;
			 }
		});
		canvas.redraw();
	}
	
	public void setHighlight(boolean highlighted) {

		Gui.sethasChanged(true);
		highlight = highlighted;
		draw(highlight);
		if(highlight) trtm.setBackground(new Color(device, /*205,232,255*/ 220,220,220)); else trtm.setBackground(new Color(device, 255,255,255));
	}
	
	public boolean contains(Point2D point) {
		if(hasPaintListener) {
		Rectangle rect = new Rectangle(x, y, width, height);
		AffineTransform t = new AffineTransform();
		t.translate(rect.x, rect.y);
        t.rotate(Math.toRadians(angle));
        t.translate(-(rect.width/2)-x, -(rect.height/2)-y);
		try {
			Point2D tp = t.inverseTransform(point,null);
			if(rect.contains(tp.getX(),tp.getY())) return true;
		} catch (NoninvertibleTransformException e) {}
		}
		return false;
	}
	
	public void hide(Canvas canvas) {
		try {
			canvas.removePaintListener(paintListener);
			removeFromTree();
			hasPaintListener = false;
			highlight = false;
		}catch(Exception e) {}
		canvas.redraw();
	}
	
	public void addToTree(TreeItem treeItem, int style) {
		trtm = new TreeItem(treeItem,style);
		if (name != null) { 
		trtm.setText(name);
		} else {
			trtm.setText(this.toString());
		}
	}
	
	public void removeFromTree() {
		trtm.dispose();
		trtm = null;
	}
	
	public boolean isHighlighted() {
		return highlight;
	}
	

	private void info() {
		Gui.getNew_x().setText(String.valueOf(x));
		Gui.getNew_y().setText(String.valueOf(y));
		Gui.getNew_height().setText(String.valueOf(height));
		Gui.getNew_width().setText(String.valueOf(width));
		Gui.getNew_angle().setText(String.valueOf(angle));
		Gui.getNew_name().setText(name);
	}
}