package desinger;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class moebel {

protected int x;
protected int y;
protected int width;
protected int height;
protected float angle;
protected Device device = Display.getCurrent();
PaintListener paintListener;
Canvas canvas;
Image image;

	void setPosition(int new_x, int new_y) {
		x = new_x;
		y = new_y;
		canvas.redraw();
	}
	
	void setAngle(int new_angle) {
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
	
	void testMethode() {
		System.out.println(this.toString());
	}
	
	void draw() {
		try {
		canvas.removePaintListener(paintListener);
		}catch(Exception e) {}
		canvas.addPaintListener(paintListener = new PaintListener() {
			 public void paintControl(PaintEvent e) {
				 Transform transform = new Transform(device);
				 transform.translate(x, y);
				 transform.rotate((float)angle);
				 transform.translate(-(width/2), -(height/2));
				 e.gc.setTransform(transform);
				 e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
				 transform.dispose();
			 }
		});
		canvas.redraw();
	}
	
	void hide(Canvas canvas) {
		try {
			canvas.removePaintListener(paintListener);
		}catch(Exception e) {}
		canvas.redraw();
	}
}
