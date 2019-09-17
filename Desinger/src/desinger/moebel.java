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

	void setPosition(int new_x, int new_y) {
		x = new_x;
		y = new_y;
	}
	
	void setAngle(int new_angle) {
		angle = new_angle;
	}
	
	void setDimensions(int new_width, int new_height) {
		height = new_height;
		width = new_width;
	}
	
	
	void setX(int new_x) {
		x = new_x;
	}
	
	void setY(int new_y) {
		y = new_y;
	}
	
	void setWidth(int new_width) {
		width = new_width;
	}

	void setHeight(int new_height) {
		height = new_height;
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
		
	}
	
	void draw(Image image, Canvas canvas) {
		canvas.addPaintListener(new PaintListener() {
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
	
}
