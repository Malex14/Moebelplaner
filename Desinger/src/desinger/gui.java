package desinger;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.omg.CORBA.portable.ValueOutputStream;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Group;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.GestureListener;
import org.eclipse.swt.events.GestureEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;

@SuppressWarnings("unused")
public class gui {

	protected Shell shlMbelplaner;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	int testObjektIndex = 0;
	static Canvas canvas;
	private Text text;
	private Text text_1;
	private Tree tree;
	private static TreeItem trtmMoebel;

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
		shlMbelplaner.open();
		shlMbelplaner.layout();
		
		while (!shlMbelplaner.isDisposed()) {
			desinger.main.guiloop();
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			
		}
		
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		ArrayList<TestObjekt> testObjektArray = new ArrayList<TestObjekt>();
		
		shlMbelplaner = new Shell();
		shlMbelplaner.setMinimumSize(new Point(500, 300));
		shlMbelplaner.setText("M\u00F6belplaner");
		shlMbelplaner.setSize(899, 578);
		shlMbelplaner.setLayout(new FormLayout());
		
		Menu menu = new Menu(shlMbelplaner, SWT.BAR);
		shlMbelplaner.setMenuBar(menu);
		
		MenuItem mntmDatei = new MenuItem(menu, SWT.CASCADE);
		mntmDatei.setText("Datei");
		
		Menu menu_1 = new Menu(mntmDatei);
		mntmDatei.setMenu(menu_1);
		
		MenuItem mntmNeu = new MenuItem(menu_1, SWT.NONE);
		mntmNeu.setText("Neu");
		
		MenuItem mntmOeffnen = new MenuItem(menu_1, SWT.NONE);
		mntmOeffnen.setText("\u00D6ffnen...");
		
		MenuItem mntmSpeichern = new MenuItem(menu_1, SWT.NONE);
		mntmSpeichern.setText("Speichern");
		
		MenuItem mntmSpeichernUnter = new MenuItem(menu_1, SWT.NONE);
		mntmSpeichernUnter.setText("Speichern unter...");
		
		MenuItem mntmExportieren = new MenuItem(menu_1, SWT.NONE);
		mntmExportieren.setText("Exportieren");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmBeenden = new MenuItem(menu_1, SWT.NONE);
		mntmBeenden.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlMbelplaner.close();
			}
		});
		mntmBeenden.setText("Beenden");
		
		MenuItem mntmWerkzeuge = new MenuItem(menu, SWT.CASCADE);
		mntmWerkzeuge.setText("Werkzeuge");
		
		Menu menu_2 = new Menu(mntmWerkzeuge);
		mntmWerkzeuge.setMenu(menu_2);
		
		MenuItem mntmVerschiebewerkzeug = new MenuItem(menu_2, SWT.RADIO);
		mntmVerschiebewerkzeug.setSelection(true);
		mntmVerschiebewerkzeug.setText("Verschiebewerkzeug");
		
		MenuItem mntmEinfgenWerkzeug = new MenuItem(menu_2, SWT.RADIO);
		mntmEinfgenWerkzeug.setText("Einf\u00FCgen Werkzeug");
		
		MenuItem mntmEntfernenWerkzeug = new MenuItem(menu_2, SWT.RADIO);
		mntmEntfernenWerkzeug.setText("Entfernen Werkzeug");
		
		MenuItem mntmGrenWerkzeug = new MenuItem(menu_2, SWT.RADIO);
		mntmGrenWerkzeug.setText("Gr\u00F6\u00DFen Werkzeug");
		
		MenuItem mntmMbel = new MenuItem(menu, SWT.CASCADE);
		mntmMbel.setText("M\u00F6bel");
		
		Menu menu_3 = new Menu(mntmMbel);
		mntmMbel.setMenu(menu_3);
		
		MenuItem mntmLetztesMbelLoschen = new MenuItem(menu_3, SWT.NONE);
		mntmLetztesMbelLoschen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					testObjektArray.get(testObjektArray.size()-1).hide(getCanvas());
					testObjektArray.remove(testObjektArray.size()-1);
					}catch(Exception e1) {}
			}
		});
		mntmLetztesMbelLoschen.setText("letztes M\u00F6bel loschen");
		
		TabFolder tabFolder = new TabFolder(shlMbelplaner, SWT.NONE);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.bottom = new FormAttachment(100);
		fd_tabFolder.top = new FormAttachment(0);
		fd_tabFolder.left = new FormAttachment(0);
		tabFolder.setLayoutData(fd_tabFolder);
		formToolkit.adapt(tabFolder);
		formToolkit.paintBordersFor(tabFolder);
		
		TabItem tbtmEinfgen = new TabItem(tabFolder, SWT.NONE);
		tbtmEinfgen.setText("Einf\u00FCgen");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmEinfgen.setControl(composite);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new FormLayout());
		
		Group grpMbel = new Group(composite, SWT.NONE);
		FormData fd_grpMbel = new FormData();
		fd_grpMbel.right = new FormAttachment(0, 242);
		fd_grpMbel.bottom = new FormAttachment(0, 148);
		fd_grpMbel.top = new FormAttachment(0);
		fd_grpMbel.left = new FormAttachment(0);
		grpMbel.setLayoutData(fd_grpMbel);
		grpMbel.setText("M\u00F6bel");
		formToolkit.adapt(grpMbel);
		formToolkit.paintBordersFor(grpMbel);
		grpMbel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Button btntestObjekt = formToolkit.createButton(grpMbel, "testObjekt", SWT.NONE);
		
		Button btnNewButton = new Button(grpMbel, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
				testObjektArray.get(testObjektArray.size()-1).hide(getCanvas());
				testObjektArray.remove(testObjektArray.size()-1);
				}catch(Exception e1) {}
			}
		});
		formToolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("Letztes Objekt l\u00F6schen");
		
		
		Button btnCreateSchrank = formToolkit.createButton(grpMbel, "Schrank", SWT.NONE);
		
		
		Button btnCreateStuhl = formToolkit.createButton(grpMbel, "New Button", SWT.NONE);
		
		
		FormData fd_scale = new FormData();
		fd_scale.top = new FormAttachment(grpMbel, 118);
		fd_scale.left = new FormAttachment(0, 32);
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("Objekte");
		
		tree = new Tree(tabFolder, SWT.BORDER);
		tbtmNewItem.setControl(tree);
		formToolkit.paintBordersFor(tree);
		
		trtmMoebel = new TreeItem(tree, SWT.NONE);
		trtmMoebel.setText("M\u00F6bel");
		
		
		TreeItem trtmTestTisch = new TreeItem(trtmMoebel, SWT.NONE);
		trtmTestTisch.setText("Test Tisch");
		trtmMoebel.setExpanded(true);
		
		TreeItem trtmFenster = new TreeItem(tree, SWT.NONE);
		trtmFenster.setText("Fenster");
		
		TreeItem trtmTestFenster = new TreeItem(trtmFenster, SWT.NONE);
		trtmTestFenster.setText("Test Fenster");
		trtmFenster.setExpanded(true);
		
		TabItem tbtmEigenschaften = new TabItem(tabFolder, SWT.NONE);
		tbtmEigenschaften.setText("Eigenschaften");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmEigenschaften.setControl(composite_1);
		formToolkit.paintBordersFor(composite_1);
		
		Group grpObjektobjektname = new Group(composite_1, SWT.NONE);
		grpObjektobjektname.setText("Objekt: [OBJEKTNAME]");
		grpObjektobjektname.setBounds(0, 0, 242, 491);
		formToolkit.adapt(grpObjektobjektname);
		formToolkit.paintBordersFor(grpObjektobjektname);
		
		Group grpPosition = new Group(grpObjektobjektname, SWT.NONE);
		grpPosition.setBounds(10, 15, 222, 71);
		grpPosition.setText("Position");
		formToolkit.adapt(grpPosition);
		formToolkit.paintBordersFor(grpPosition);
		
		Label lblX = new Label(grpPosition, SWT.NONE);
		lblX.setBounds(10, 21, 10, 15);
		formToolkit.adapt(lblX, true, true);
		lblX.setText("X:");
		
		text = new Text(grpPosition, SWT.BORDER);
		text.setBounds(26, 18, 186, 21);
		formToolkit.adapt(text, true, true);
		
		text_1 = new Text(grpPosition, SWT.BORDER);
		text_1.setBounds(26, 42, 186, 21);
		formToolkit.adapt(text_1, true, true);
		
		Label lblY = new Label(grpPosition, SWT.NONE);
		lblY.setText("Y:");
		lblY.setBounds(10, 45, 10, 15);
		formToolkit.adapt(lblY, true, true);
		
		Button btnbernehmen = new Button(grpObjektobjektname, SWT.NONE);
		btnbernehmen.setBounds(157, 456, 75, 25);
		formToolkit.adapt(btnbernehmen, true, true);
		btnbernehmen.setText("\u00DCbernehmen");
		
		canvas = new Canvas(shlMbelplaner, SWT.BORDER);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				moebel moebel = null;
				for (TestObjekt testObjekt : testObjektArray) {
					if(testObjekt.hasPaintListener() && testObjekt.contains(new java.awt.Point(e.x,e.y))) moebel = testObjekt;
						testObjekt.draw();
					}
				if(moebel != null) moebel.draw(true);
			}
		});
		
		
		fd_tabFolder.right = new FormAttachment(canvas, -6);
		
		ComboBoxCellEditor tableColumn = new ComboBoxCellEditor();

		FormData fd_canvas = new FormData();
		fd_canvas.bottom = new FormAttachment(100);
		fd_canvas.right = new FormAttachment(100);
		fd_canvas.top = new FormAttachment(0);
		fd_canvas.left = new FormAttachment(0, 256);
		canvas.setLayoutData(fd_canvas);
		formToolkit.adapt(canvas);
		formToolkit.paintBordersFor(canvas);
		
		
		
		//END OF AUTOGENERATION
		
		btntestObjekt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				newItem dialog = new newItem(shlMbelplaner,SWT.NONE);
				testObjektArray.add(new TestObjekt(getCanvas()));
				testObjektArray.get(testObjektArray.size()-1).testMethode();
			}
		});
	}
	
	public static Canvas getCanvas() {
		return canvas;
	}
	public Tree getTree() {
		return tree;
	}
	public static TreeItem gettrtmMoebel() {
		return trtmMoebel;
	}
}
