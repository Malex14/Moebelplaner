package desinger;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.image.GIFFileFormat;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.SWTResourceManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.GestureListener;
import org.eclipse.swt.events.GestureEvent;


@SuppressWarnings("unused")
public class Gui {

	protected static Shell shlMbelplaner;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private static Canvas canvas;
	private Text text;
	private Text text_1;
	private Tree tree;
	private static TreeItem trtmMoebel;
	private boolean drag = false;
	private boolean mouseDown = false;
	private Moebel dragMoebel;
	private String newItemDialog[] = {"Name","Bitte geben Sie einen Namen ein"};
	private Float start_angle;
	private Float start_mouse;
	private ArrayList<Moebel> moebel = new ArrayList<>();
	private String savepath;
	private static boolean hasChanged;
	private static boolean hasStar = false;
	private Device device = Display.getCurrent();
	private static String openPath;
	private boolean gesture = false;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			openPath = args.length > 0 ? args[0] : null;
		} catch (Exception e) {e.printStackTrace();}
		try {
			Gui window = new Gui();
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
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			
		}
		
	}
	
	

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		
		shlMbelplaner = new Shell();
		shlMbelplaner.setMinimumSize(new Point(500, 300));
		shlMbelplaner.setText("M\u00F6belplaner - Unbennant");
		shlMbelplaner.setSize(899, 576);
		shlMbelplaner.setLayout(new FormLayout());
		
		Menu menu = new Menu(shlMbelplaner, SWT.BAR);
		shlMbelplaner.setMenuBar(menu);
		shlMbelplaner.setImages(new Image[] {SWTResourceManager.getImage(Gui.class, "/icons/icon32.png"),SWTResourceManager.getImage(Gui.class, "/icons/icon512.png")});
		
		MenuItem mntmDatei = new MenuItem(menu, SWT.CASCADE);
		mntmDatei.setText("Datei");
		
		Menu menu_1 = new Menu(mntmDatei);
		mntmDatei.setMenu(menu_1);
		
		
		//NEU
		
		MenuItem mntmNeu = new MenuItem(menu_1, SWT.NONE);
		mntmNeu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox msg = new MessageBox(shlMbelplaner, SWT.YES | SWT.NO | SWT.ICON_WARNING);
				msg.setText("Neu");
				msg.setMessage("Die aktuelle Datei ist nicht leer. Soll sie verworfen werden?");
				if(moebel.isEmpty() || msg.open() == SWT.YES) {
				for (Moebel moebel : moebel) {
        			moebel.hide(Gui.getCanvas());
        		}
        		moebel = new ArrayList<Moebel>();
				}
            	hasStar = hasChanged = false;
				shlMbelplaner.setText("M\u00F6belplaner - Unbennant");
			}
		});
		mntmNeu.setText("Neu\tStrg+N");
		mntmNeu.setAccelerator(SWT.CONTROL+'n');
		
		
		//ÖFFNEN
		
		MenuItem mntmOeffnen = new MenuItem(menu_1, SWT.NONE);
		mntmOeffnen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					MessageBox msg = new MessageBox(shlMbelplaner, SWT.YES | SWT.NO | SWT.ICON_WARNING);
					msg.setText("Neu");
					msg.setMessage("Die aktuelle Datei ist nicht leer. Soll sie verworfen werden?");
					FileDialog fileDialog = new FileDialog(shlMbelplaner, SWT.OPEN);
					fileDialog.setFilterExtensions(new String[] {"*.mob"});
					fileDialog.setText("Öffnen");
					savepath = fileDialog.open();
					FileInputStream fis = new FileInputStream(savepath);
					ObjectInputStream ois = new ObjectInputStream(fis);
					String jsonIn = ois.readUTF();
					ois.close();
            		fis.close();
            		if(moebel.isEmpty() || msg.open() == SWT.YES) {
            		JSONArray ja = new JSONArray(jsonIn);
            		for (Moebel moebel : moebel) {
            			moebel.hide(Gui.getCanvas());
            		}
            		moebel = new ArrayList<Moebel>();
            		for (int i = 0; i < ja.length(); i++) {
            			JSONObject jo = new JSONObject(ja.get(i).toString());
            			moebel.add((Moebel)Class.forName(jo.getString("type")).getDeclaredConstructor(new Class[] {Canvas.class,String.class}).newInstance(new Object[] {Gui.getCanvas(),jo.getString("name")}));
            			Moebel tmp = moebel.get(moebel.size()-1);
            			tmp.setAll(jo.getInt("x"), jo.getInt("y"), jo.getInt("width"), jo.getInt("height"), jo.getInt("angle"), jo.getBoolean("hasPaintListener"), jo.getBoolean("highlight")); 
            		}
            		hasStar = hasChanged = false;
            		shlMbelplaner.setText("M\u00F6belplaner - " + savepath);
					}
				} catch(Exception e1) {e1.printStackTrace();};
			}
		});
		mntmOeffnen.setText("\u00D6ffnen...\tStrg+O");
		mntmOeffnen.setAccelerator(SWT.CONTROL+'o');
		
		
		//SPEICHERN
		
		MenuItem mntmSpeichern = new MenuItem(menu_1, SWT.NONE);
		mntmSpeichern.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});
		mntmSpeichern.setText("Speichern\tStrg+S");
		mntmSpeichern.setAccelerator(SWT.CONTROL+'s');
		
		//SPEICHERTN UNTER
		
		MenuItem mntmSpeichernUnter = new MenuItem(menu_1, SWT.NONE);
		mntmSpeichernUnter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					JSONArray ja = new JSONArray(); 
					for (Moebel moebel2 : moebel) {
						ja.put(moebel2.getJSON());
					}
					FileDialog fileDialog = new FileDialog(shlMbelplaner, SWT.SAVE);
					fileDialog.setFilterExtensions(new String[] {"*.mob"});
					fileDialog.setText("Speichern unter");
					fileDialog.setOverwrite(true);
					String tmp_savepath = fileDialog.open();
					if(tmp_savepath != null) {
						savepath = tmp_savepath;
						FileOutputStream fos = new FileOutputStream(savepath);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeUTF(ja.toString());
						oos.close();
	            		fos.close();
	            		hasStar = hasChanged = false;
	            		shlMbelplaner.setText("M\u00F6belplaner - " + savepath);
					}
				}catch(Exception e1) {}
				
			}
		});
		mntmSpeichernUnter.setText("Speichern unter...\tStrg+Umschalt+S");
		mntmSpeichernUnter.setAccelerator(SWT.CONTROL+SWT.SHIFT+'s');
		
		//EXPORTIEREN
		
		MenuItem mntmExportieren = new MenuItem(menu_1, SWT.NONE);
		mntmExportieren.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Export export = new Export(shlMbelplaner);
				if (export.open()==0) {
					if (export.getBtnAbsolut()==true) {
						try {
							FileDialog fileDialog = new FileDialog(shlMbelplaner, SWT.SAVE);
							fileDialog.setFilterExtensions(new String[] {"*.jpeg","*.png","*.bmp"});
							fileDialog.setText("Exportieren");
							fileDialog.setOverwrite(true);
							Image image = new Image(Display.getCurrent(), canvas.getBounds());
							String path = fileDialog.open();
							int fileType = -1;
							switch (fileDialog.getFilterIndex()) {
							case 0:
								fileType = SWT.IMAGE_JPEG;
								break;
							case 1:
								fileType = SWT.IMAGE_PNG;
								break;
							case 2:
								fileType = SWT.IMAGE_BMP;
								break;
							default:
								break;
							}
							GC gc = new GC(image);
							canvas.print(gc);
							ImageLoader loader = new ImageLoader();
							loader.data = new ImageData[] {image.getImageData().scaledTo(export.getWidth(), export.getHeight())};
							if(fileType == -1) throw new Exception();
							loader.save(path, fileType);
							gc.dispose();
							image.dispose();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}else {
						try {
							FileDialog fileDialog = new FileDialog(shlMbelplaner, SWT.SAVE);
							fileDialog.setFilterExtensions(new String[] {"*.jpeg","*.png","*.bmp"});
							fileDialog.setText("Exportieren");
							fileDialog.setOverwrite(true);
							String path = fileDialog.open();
							int fileType = -1;
							switch (fileDialog.getFilterIndex()) {
							case 0:
								fileType = SWT.IMAGE_JPEG;
								break;
							case 1:
								fileType = SWT.IMAGE_PNG;
								break;
							case 2:
								fileType = SWT.IMAGE_BMP;
								break;
							default:
								break;
							}
							Image image = new Image(Display.getCurrent(), canvas.getBounds());
							GC gc = new GC(image);
							canvas.print(gc);
							ImageLoader loader = new ImageLoader();
							loader.data = new ImageData[] {image.getImageData().scaledTo((int)(canvas.getBounds().width * (float)(export.getPercent()/100)), (int)(canvas.getBounds().height * (float)(export.getPercent()/100)))};
							if(fileType == -1) throw new Exception();
							loader.save(path, fileType);
							gc.dispose();
							image.dispose();
						} catch (Exception e2) {
							System.out.println("a");
						}
					}
				}
			}
		});
		mntmExportieren.setText("Exportieren...\tStrg+E");
		mntmExportieren.setAccelerator(SWT.CONTROL+'e');
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmBeenden = new MenuItem(menu_1, SWT.NONE);
		mntmBeenden.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlMbelplaner.close();
			}
		});
		mntmBeenden.setText("Beenden\tStrg+Q");
		mntmBeenden.setAccelerator(SWT.CONTROL+'q');
		
		MenuItem mntmWerkzeuge = new MenuItem(menu, SWT.CASCADE);
		mntmWerkzeuge.setText("Werkzeuge");
		
		Menu menu_2 = new Menu(mntmWerkzeuge);
		mntmWerkzeuge.setMenu(menu_2);
		
		MenuItem mntmUniversalwerkzeug = new MenuItem(menu_2, SWT.RADIO);
		mntmUniversalwerkzeug.setSelection(true);
		mntmUniversalwerkzeug.setText("Universalwerkzeug");
		
		MenuItem mntmEinfgenWerkzeug = new MenuItem(menu_2, SWT.RADIO);
		mntmEinfgenWerkzeug.setText("Einf\u00FCgen Werkzeug");
		
		MenuItem mntmEntfernenWerkzeug = new MenuItem(menu_2, SWT.RADIO);
		mntmEntfernenWerkzeug.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
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
					moebel.get(moebel.size()-1).hide(getCanvas());
					moebel.remove(moebel.size()-1);
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
		fd_grpMbel.right = new FormAttachment(100);
		fd_grpMbel.bottom = new FormAttachment(100);
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
				moebel.get(moebel.size()-1).hide(getCanvas());
				moebel.remove(moebel.size()-1);
				}catch(Exception e1) {}
			}
		});
		formToolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("Letztes Objekt l\u00F6schen");
		
		
		Button btnCreateSchrank = formToolkit.createButton(grpMbel, "Schrank", SWT.NONE);
		
		
		Button btnCreateTisch = formToolkit.createButton(grpMbel, "Tisch", SWT.NONE);
		btnCreateTisch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog dialog = new InputDialog(shlMbelplaner, newItemDialog[0], newItemDialog[1], "", new IInputValidator() {
					
					@Override
					public String isValid(String arg0) {
						if(arg0 != "")return null;
						else return "";
					}
				});
				dialog.open();
				if(dialog.getReturnCode() == 0) {
					moebel.add(new ItemTisch(Gui.getCanvas(),dialog.getValue()));
					moebel.get(moebel.size()-1).testMethode();
				}
			}
		});
		
		
		FormData fd_scale = new FormData();
		fd_scale.top = new FormAttachment(grpMbel, 118);
		
		Button btnCreateRunderTisch = new Button(grpMbel, SWT.NONE);
		btnCreateRunderTisch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog dialog = new InputDialog(shlMbelplaner, newItemDialog[0], newItemDialog[1], "", new IInputValidator() {
					
					@Override
					public String isValid(String arg0) {
						if(arg0 != "")return null;
						else return "";
					}
				});
				dialog.open();
				if(dialog.getReturnCode() == 0) {
					moebel.add(new ItemrunderTisch(Gui.getCanvas(),dialog.getValue()));
					moebel.get(moebel.size()-1).testMethode();
				}
			}
		});
		formToolkit.adapt(btnCreateRunderTisch, true, true);
		btnCreateRunderTisch.setText("runder Tisch");
		
		Button btnCreateWaschmaschine = new Button(grpMbel, SWT.NONE);
		btnCreateWaschmaschine.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog dialog = new InputDialog(shlMbelplaner, newItemDialog[0], newItemDialog[1], "", new IInputValidator() {
					
					@Override
					public String isValid(String arg0) {
						if(arg0 != "")return null;
						else return "";
					}
				});
				dialog.open();
				if(dialog.getReturnCode() == 0) {
					moebel.add(new ItemWaschmaschine(Gui.getCanvas(),dialog.getValue()));
					moebel.get(moebel.size()-1).testMethode();
				}
			}
		});
		formToolkit.adapt(btnCreateWaschmaschine, true, true);
		btnCreateWaschmaschine.setText("Waschmaschine");
		fd_scale.left = new FormAttachment(0, 32);
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("Objekte");
		
		tree = new Tree(tabFolder, SWT.BORDER);
		tbtmNewItem.setControl(tree);
		formToolkit.paintBordersFor(tree);
		
		trtmMoebel = new TreeItem(tree, SWT.NONE);
		trtmMoebel.setText("M\u00F6bel");
		trtmMoebel.setExpanded(true);
		
		TabItem tbtmEigenschaften = new TabItem(tabFolder, SWT.NONE);
		tbtmEigenschaften.setText("Eigenschaften");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmEigenschaften.setControl(composite_1);
		formToolkit.paintBordersFor(composite_1);
		composite_1.setLayout(new FormLayout());
		
		Group grpObjektobjektname = new Group(composite_1, SWT.NONE);
		grpObjektobjektname.setLayout(new FormLayout());
		FormData fd_grpObjektobjektname = new FormData();
		fd_grpObjektobjektname.right = new FormAttachment(100);
		fd_grpObjektobjektname.top = new FormAttachment(0);
		fd_grpObjektobjektname.bottom = new FormAttachment(100);
		fd_grpObjektobjektname.left = new FormAttachment(0);
		grpObjektobjektname.setLayoutData(fd_grpObjektobjektname);
		grpObjektobjektname.setText("Objekt: Kein Objekt ausgewählt");
		formToolkit.adapt(grpObjektobjektname);
		formToolkit.paintBordersFor(grpObjektobjektname);
		
		Group grpPosition = new Group(grpObjektobjektname, SWT.NONE);
		FormData fd_grpPosition = new FormData();
		fd_grpPosition.bottom = new FormAttachment(0, 79);
		fd_grpPosition.right = new FormAttachment(0, 229);
		fd_grpPosition.top = new FormAttachment(0, 5);
		fd_grpPosition.left = new FormAttachment(0, 5);
		grpPosition.setLayoutData(fd_grpPosition);
		grpPosition.setText("Position");
		formToolkit.adapt(grpPosition);
		formToolkit.paintBordersFor(grpPosition);
		
		Label lblX = new Label(grpPosition, SWT.NONE);
		lblX.setBounds(10, 21, 10, 15);
		formToolkit.adapt(lblX, true, true);
		lblX.setText("X:");
		
		text = new Text(grpPosition, SWT.BORDER);
		text.setBounds(26, 18, 188, 21);
		formToolkit.adapt(text, true, true);
		
		text_1 = new Text(grpPosition, SWT.BORDER);
		text_1.setBounds(26, 42, 188, 21);
		formToolkit.adapt(text_1, true, true);
		
		Label lblY = new Label(grpPosition, SWT.NONE);
		lblY.setText("Y:");
		lblY.setBounds(10, 45, 10, 15);
		formToolkit.adapt(lblY, true, true);
		
		Button btnbernehmen = new Button(grpObjektobjektname, SWT.NONE);
		FormData fd_btnbernehmen = new FormData();
		fd_btnbernehmen.right = new FormAttachment(100, -6);
		fd_btnbernehmen.bottom = new FormAttachment(100, -6);
		btnbernehmen.setLayoutData(fd_btnbernehmen);
		btnbernehmen.setEnabled(false);
		formToolkit.adapt(btnbernehmen, true, true);
		btnbernehmen.setText("\u00DCbernehmen");
		
		Button btnAutomatischbernehmen = new Button(grpObjektobjektname, SWT.CHECK);
		fd_btnbernehmen.left = new FormAttachment(0, 133);
		FormData fd_btnAutomatischbernehmen = new FormData();
		fd_btnAutomatischbernehmen.bottom = new FormAttachment(100, -10);
		fd_btnAutomatischbernehmen.left = new FormAttachment(0, 6);
		btnAutomatischbernehmen.setLayoutData(fd_btnAutomatischbernehmen);
		btnAutomatischbernehmen.setSelection(true);
		btnAutomatischbernehmen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnAutomatischbernehmen.getSelection()) btnbernehmen.setEnabled(false);
				else btnbernehmen.setEnabled(true);
			}
		});
		formToolkit.adapt(btnAutomatischbernehmen, true, true);
		btnAutomatischbernehmen.setText("auto \u00DCbernehmen");
		
		
		
		canvas = new Canvas(shlMbelplaner, SWT.BORDER | SWT.DOUBLE_BUFFERED);
		canvas.addGestureListener(new GestureListener() {
			public void gesture(GestureEvent arg0) {
				if(arg0.detail == SWT.GESTURE_BEGIN)gesture = true;
				if(arg0.detail == SWT.GESTURE_END)gesture = false;
				if(gesture == true && arg0.detail == SWT.GESTURE_MAGNIFY) {
					double mag = arg0.magnification;
				}
			}
		});
		canvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent arg0) {
				try {
					if(drag) dragMoebel.setPosition(arg0.x, arg0.y);
					if(mouseDown) {
						Moebel tmp_moebel = null;
						for (Moebel moebel : moebel) {
							if(moebel.isHighlighted()) tmp_moebel = moebel;
						}
						int ank = 0;
						int geg = 0;
						float angle = 0;
						if (arg0.x < tmp_moebel.getX()) {
							if(arg0.y <= tmp_moebel.getY()) {
								//links oben
								ank = Math.max(tmp_moebel.getX(), arg0.x)-Math.min(tmp_moebel.getX(), arg0.x);
								geg = Math.max(tmp_moebel.getY(), arg0.y)-Math.min(tmp_moebel.getY(), arg0.y);
								angle = (float) Math.toDegrees((float)Math.atan((float)geg/(float)ank))+270;
							}
							else if(arg0.y > tmp_moebel.getY()) {
								//links unten
								ank = Math.max(tmp_moebel.getX(), arg0.x)-Math.min(tmp_moebel.getX(), arg0.x);
								geg = Math.max(tmp_moebel.getY(), arg0.y)-Math.min(tmp_moebel.getY(), arg0.y);
								angle = (float) (Math.toDegrees((float)Math.atan((float)-geg/(float)ank))+270);
							}
						}
						else if (arg0.x >= tmp_moebel.getX()) {
							if(arg0.y <= tmp_moebel.getY()) {
								//rechts oben
								ank = Math.max(tmp_moebel.getX(), arg0.x)-Math.min(tmp_moebel.getX(), arg0.x);
								geg = Math.max(tmp_moebel.getY(), arg0.y)-Math.min(tmp_moebel.getY(), arg0.y);
								angle = (float) Math.abs(Math.toDegrees((float)Math.atan((float)geg/(float)ank))-90);
							}
							else if(arg0.y > tmp_moebel.getY()) {
								//rechts unten
								ank = Math.max(tmp_moebel.getX(), arg0.x)-Math.min(tmp_moebel.getX(), arg0.x);
								geg = Math.max(tmp_moebel.getY(), arg0.y)-Math.min(tmp_moebel.getY(), arg0.y);
								angle = (float) Math.toDegrees((float)Math.atan((float)geg/(float)ank))+90;
							}
						}
						if(start_angle == null) start_angle = tmp_moebel.getAngle();
						if(start_mouse == null) start_mouse = angle;
						angle = (start_angle +(angle - start_mouse)) % 360;
						/*if(angle < 5 && angle > 355) {
							angle = 0;
						}
						if(angle < 185 && angle > 175) {
							angle = 180;
						}*/
						tmp_moebel.setAngle(angle);
					}
				} catch (Exception e) {}
			}
		});
		canvas.addDragDetectListener(new DragDetectListener() {
			public void dragDetected(DragDetectEvent arg0) {
				
				try {
				for (Moebel moebel : moebel) {
					if(moebel.isHighlighted()) dragMoebel = moebel;
				}
				if (dragMoebel.contains(new java.awt.Point(arg0.x,arg0.y))) drag = true;
				else mouseDown = true;
				} catch(Exception e) {}
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Moebel tmp_moebel = null;
				for (Moebel moebel : moebel) {
					if(moebel.hasPaintListener() && moebel.contains(new java.awt.Point(e.x,e.y))) tmp_moebel = moebel;
					moebel.setHighlight(false);
				}
				if(tmp_moebel != null) {
					tmp_moebel.setHighlight(true);
					grpObjektobjektname.setText("Objekt: " + tmp_moebel.getName());
				} else grpObjektobjektname.setText("Objekt: Kein Objekt ausgewählt");
			}
			
			@Override
			public void mouseUp(MouseEvent e) {
				drag = false;
				mouseDown = false;
				dragMoebel = null;
				start_angle = null;
				start_mouse = null;
			}
			@Override
			public void mouseDown(MouseEvent e) {
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
		
		
		btntestObjekt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog dialog = new InputDialog(shlMbelplaner, newItemDialog[0], newItemDialog[1], "", new IInputValidator() {
					
					@Override
					public String isValid(String arg0) {
						if(arg0 != "")return null;
						else return "";
					}
				});
				dialog.open();
				if(dialog.getReturnCode() == 0) {
					moebel.add(new TestObjekt(Gui.getCanvas(),dialog.getValue()));
					moebel.get(moebel.size()-1).testMethode();
					
				}
			}
		});
		
		shlMbelplaner.addListener(SWT.Close, new Listener() {
		      public void handleEvent(Event event) {
		    	  if(hasChanged) {
						MessageBox msg = new MessageBox(shlMbelplaner,SWT.YES|SWT.NO|SWT.CANCEL);
						msg.setText("Möbelplaner");
						msg.setMessage("Möchten sie die Änderungen speichern?");
						switch(msg.open()) {
						case 64:
							if(!save()) {
								event.doit = false;
								break;
							}
						case 128:
							event.doit = true;
							break;
						case 256:
							event.doit = false;
							break;
						default:
							event.doit = false;
							break;
						}	
					}else event.doit = true;
		      }
		});
		
		if(openPath != null) {
			try {
				savepath = openPath;
				FileInputStream fis = new FileInputStream(savepath);
				ObjectInputStream ois = new ObjectInputStream(fis);
				String jsonIn = ois.readUTF();
				ois.close();
        		fis.close();
        		
        		JSONArray ja = new JSONArray(jsonIn);
        		for (Moebel moebel : moebel) {
        			moebel.hide(Gui.getCanvas());
        		}
        		moebel = new ArrayList<Moebel>();
        		for (int i = 0; i < ja.length(); i++) {
        			JSONObject jo = new JSONObject(ja.get(i).toString());
        			moebel.add((Moebel)Class.forName(jo.getString("type")).getDeclaredConstructor(new Class[] {Canvas.class,String.class}).newInstance(new Object[] {Gui.getCanvas(),jo.getString("name")}));
        			Moebel tmp = moebel.get(moebel.size()-1);
        			tmp.setAll(jo.getInt("x"), jo.getInt("y"), jo.getInt("width"), jo.getInt("height"), jo.getInt("angle"), jo.getBoolean("hasPaintListener"), jo.getBoolean("highlight"));
        		}
        		hasStar = hasChanged = false;
        		shlMbelplaner.setText("M\u00F6belplaner - " + savepath);
				
			} catch(Exception e1) {};
		}
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
	public Shell getShell() {
		return shlMbelplaner;
	}
	public static void sethasChanged(boolean changed) {
		hasChanged = changed;
		if(changed && !hasStar ) {
			shlMbelplaner.setText(shlMbelplaner.getText()+"*");
			hasStar = true;
		}
	}
	private boolean save() {
		try {
			JSONArray ja = new JSONArray(); 
			for (Moebel moebel2 : moebel) {
				ja.put(moebel2.getJSON());
			}
			if(savepath == null) {
			FileDialog fileDialog = new FileDialog(shlMbelplaner, SWT.SAVE);
			fileDialog.setFilterExtensions(new String[] {"*.mob"});
			fileDialog.setText("Speichern");
			fileDialog.setOverwrite(true);
			savepath = fileDialog.open();	
			}
			FileOutputStream fos = new FileOutputStream(savepath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeUTF(ja.toString());
			oos.close();
        	fos.close();
        	hasStar = hasChanged = false;
        	shlMbelplaner.setText("M\u00F6belplaner - " + savepath);
        	return true;
		}catch(Exception e1) {return false;}
	}
}
