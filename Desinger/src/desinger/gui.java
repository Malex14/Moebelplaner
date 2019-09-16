package desinger;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Composite;
import java.awt.Frame;
import org.eclipse.swt.awt.SWT_AWT;
import java.awt.Panel;
import java.awt.BorderLayout;
import javax.swing.JRootPane;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class gui {

	protected Shell shlDesinger;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			gui window = new gui();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlDesinger.open();
		shlDesinger.layout();
		while (!shlDesinger.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlDesinger = new Shell();
		shlDesinger.setSize(916, 857);
		shlDesinger.setText("Desinger");
		
		Menu menu = new Menu(shlDesinger, SWT.BAR);
		shlDesinger.setMenuBar(menu);
		
		MenuItem mntmDatei_1 = new MenuItem(menu, SWT.CASCADE);
		mntmDatei_1.setText("Datei");
		
		Menu menu_1 = new Menu(mntmDatei_1);
		mntmDatei_1.setMenu(menu_1);
		
		MenuItem mntmBeenden = new MenuItem(menu_1, SWT.NONE);
		mntmBeenden.setText("Beenden");
		
		MenuItem mntmBearbeiten = new MenuItem(menu, SWT.NONE);
		mntmBearbeiten.setText("Bearbeiten");
		
		Canvas canvas = new Canvas(shlDesinger, SWT.NONE);
		canvas.setBounds(439, 160, 370, 412);
		
		Button btnNewButton = new Button(shlDesinger, SWT.NONE);
		btnNewButton.setBounds(77, 172, 75, 25);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(shlDesinger, SWT.NONE);
		
		btnNewButton_1.setBounds(77, 294, 75, 25);
		btnNewButton_1.setText("New Button");
		
		Scale scale = new Scale(shlDesinger, SWT.NONE);
		scale.setBounds(93, 415, 170, 42);
		
		ProgressBar progressBar = new ProgressBar(shlDesinger, SWT.NONE);
		progressBar.setMaximum(100000);
		progressBar.setBounds(190, 627, 413, 127);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				progressBar.setSelection(0);
				for(int i = 0; i< progressBar.getMaximum();i++) {progressBar.setSelection(i);}
			}
		});
	}
}
