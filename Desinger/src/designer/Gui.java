/*
 *	Möbelplaner - Malte Behrmann 2019
 *	S1 Informatik Fr. Marinescu
 */

package designer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;


public class Gui {
	
	private static Gui window;
	protected static Shell shlMbelplaner;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Canvas canvas;
	private Text new_x;
	private Text new_y;
	private Tree tree;
	private TreeItem trtmMoebel;
	private boolean drag = false;
	private boolean mouseDown = false;
	private Moebel dragMoebel;
	private Moebel gestureMoebel;
	private String newItemDialog[] = {"Name","Bitte geben Sie einen Namen ein"};
	private Float start_angle;
	private Float start_mouse;
	private ArrayList<Moebel> moebel = new ArrayList<>();
	private String savepath;
	private boolean hasChanged;
	private boolean hasStar = false;
	private static String openPath;
	private int magHeight;
	private int magWidth;
	private double rotAngle;
	private String currentTool = "universal";
	private Text new_angle;
	private Text new_name;
	private Text new_height;
	private Text new_width;
	private Group grpObjektobjektname;
	private Button btnAutomatischbernehmen;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			openPath = args.length > 0 ? args[0] : null;
		} catch (Exception e) {e.printStackTrace();}
		try {
			window = new Gui();
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
		
		//SHELL ERZEUGEN
		
		shlMbelplaner = new Shell();
		shlMbelplaner.setMinimumSize(new Point(500, 300));
		shlMbelplaner.setText("M\u00F6belplaner - Unbennant");
		shlMbelplaner.setSize(899, 576);
		shlMbelplaner.setLayout(new FormLayout());
		
		
		
		
		//MENÜLEISTE
		
		Menu menu = new Menu(shlMbelplaner, SWT.BAR);
		shlMbelplaner.setMenuBar(menu);
		shlMbelplaner.setImages(new Image[] {SWTResourceManager.getImage(Gui.class, "/icons/icon32.png"),SWTResourceManager.getImage(Gui.class, "/icons/icon512.png")});
		
		
		
		//DATEI-MENÜ
		
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
        			moebel.hide(getCanvas());
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
            			moebel.hide(getCanvas());
            		}
            		moebel = new ArrayList<Moebel>();
            		for (int i = 0; i < ja.length(); i++) {
            			JSONObject jo = new JSONObject(ja.get(i).toString());
            			moebel.add((Moebel)Class.forName(jo.getString("type")).getDeclaredConstructor(new Class[] {Canvas.class,String.class}).newInstance(new Object[] {getCanvas(),jo.getString("name")}));
            			Moebel tmp = moebel.get(moebel.size()-1);
            			tmp.setAll(jo.getInt("x"), jo.getInt("y"), jo.getInt("width"), jo.getInt("height"), jo.getInt("origWidth"), jo.getInt("origHeight") , jo.getInt("angle"), jo.getBoolean("hasPaintListener"), jo.getBoolean("highlight"), jo.getFloat("xScale"), jo.getFloat("yScale")); 
            		}
            		hasStar = hasChanged = false;
            		shlMbelplaner.setText("M\u00F6belplaner - " + savepath);
					}
				}catch(Exception e1) {MessageBox err = new MessageBox(shlMbelplaner, SWT.OK | SWT.ICON_ERROR);
					err.setText("Error");
					err.setMessage("Dateilesefehler: \n" + e1.toString());
					err.open();
				};
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
				Export export = new Export(shlMbelplaner,window);
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
		
		
		//BEENDEN
		
		MenuItem mntmBeenden = new MenuItem(menu_1, SWT.NONE);
		mntmBeenden.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlMbelplaner.close();
			}
		});
		mntmBeenden.setText("Beenden\tStrg+Q");
		mntmBeenden.setAccelerator(SWT.CONTROL+'q');
		
	
		
		//WERKZEUGE-MENÜ
		
		MenuItem mntmWerkzeuge = new MenuItem(menu, SWT.CASCADE);
		mntmWerkzeuge.setText("Werkzeuge");
		
		Menu menu_2 = new Menu(mntmWerkzeuge);
		mntmWerkzeuge.setMenu(menu_2);
		
		
		//UNIVERSALWERKZEUG
		
		MenuItem mntmUniversalwerkzeug = new MenuItem(menu_2, SWT.RADIO);
		mntmUniversalwerkzeug.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentTool = "universal";
			}
		});
		mntmUniversalwerkzeug.setSelection(true);
		mntmUniversalwerkzeug.setText("Universalwerkzeug");
		
		
		//ENTFERNENWERKZEUG
		
		MenuItem mntmEntfernenWerkzeug = new MenuItem(menu_2, SWT.RADIO);
		mntmEntfernenWerkzeug.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentTool = "remove";
			}
		});
		mntmEntfernenWerkzeug.setText("Entfernen Werkzeug");
		
		
		
		//MÖEBEL-MENÜ
		
		MenuItem mntmMbel = new MenuItem(menu, SWT.CASCADE);
		mntmMbel.setText("M\u00F6bel");
		
		Menu menu_3 = new Menu(mntmMbel);
		mntmMbel.setMenu(menu_3);
		
		
		//VERGRÖSSERN
		
		MenuItem mntmVergoessern = new MenuItem(menu_3, SWT.NONE);
		mntmVergoessern.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Moebel tmp = null;
				for (Moebel moebel2 : moebel) if (moebel2.isHighlighted()) tmp = moebel2;
				if (tmp != null) {
					float ratio = tmp.getyScale()/tmp.getxScale();
					tmp.setxScale(tmp.getxScale()+0.05F);
					tmp.setyScale(tmp.getxScale()*ratio);
				}
			}
		});
		mntmVergoessern.setText("Vergr\u00F6\u00DFern\tStrg++");
		mntmVergoessern.setAccelerator(SWT.CONTROL+'+');
		
		
		//VERKLEINERN
		
		MenuItem mntmVerkleinern = new MenuItem(menu_3, SWT.NONE);
		mntmVerkleinern.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Moebel tmp = null;
				for (Moebel moebel2 : moebel) if (moebel2.isHighlighted()) tmp = moebel2;
				if (tmp != null && ((tmp.getWidth() > 5) || (tmp.getHeight() > 5))) {
					float ratio = tmp.getyScale()/tmp.getxScale();
					tmp.setxScale(tmp.getxScale()-0.05F);
					tmp.setyScale(tmp.getxScale()*ratio);
				}
			}
		});
		mntmVerkleinern.setText("Verkleinern\tStrg+-");
		mntmVerkleinern.setAccelerator(SWT.CONTROL+'-');
		
		new MenuItem(menu_3, SWT.SEPARATOR);
		
		
		//ENTFERNEN
		
		MenuItem mntmEntfernen = new MenuItem(menu_3, SWT.NONE);
		mntmEntfernen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Iterator<Moebel> itr = moebel.iterator();
				while(itr.hasNext()) {
					Moebel itrMoebel = itr.next();
					if(itrMoebel.isHighlighted()) {
						itrMoebel.hide(getCanvas());
						itr.remove();
						new_angle.setText("");
						new_x.setText("");
						new_y.setText("");
						new_width.setText("");
						new_height.setText("");
						new_name.setText("");
						grpObjektobjektname.setText("Objekt: Kein Objekt ausgewählt");
					}
				}
			}
		});
		mntmEntfernen.setText("Entfernen\tEntf");
		mntmEntfernen.setAccelerator(SWT.DEL);
		
		//ENDE MENÜLEISTE
		
		
		
		
		//SEITENLEISTE
		
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
					moebel.add(new ItemTisch(getCanvas(),dialog.getValue(),window));
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
					moebel.add(new ItemrunderTisch(getCanvas(),dialog.getValue(),window));
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
					moebel.add(new ItemWaschmaschine(getCanvas(),dialog.getValue(), window));
					moebel.get(moebel.size()-1).testMethode();
				}
			}
		});
		formToolkit.adapt(btnCreateWaschmaschine, true, true);
		btnCreateWaschmaschine.setText("Waschmaschine");
		
		Button btnSchreibtisch = new Button(grpMbel, SWT.NONE);
		btnSchreibtisch.addSelectionListener(new SelectionAdapter() {
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
					moebel.add(new ItemSchreibtisch(getCanvas(),dialog.getValue(), window));
					moebel.get(moebel.size()-1).testMethode();
				}
			}
		});
		formToolkit.adapt(btnSchreibtisch, true, true);
		btnSchreibtisch.setText("Schreibtisch");
		
		Button btnHocker = new Button(grpMbel, SWT.NONE);
		btnHocker.addSelectionListener(new SelectionAdapter() {
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
					moebel.add(new ItemHocker(getCanvas(),dialog.getValue(), window));
					moebel.get(moebel.size()-1).testMethode();
				}
			}
		});
		formToolkit.adapt(btnHocker, true, true);
		btnHocker.setText("Hocker");
		
		Button btnSessel = new Button(grpMbel, SWT.NONE);
		btnSessel.addSelectionListener(new SelectionAdapter() {
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
					moebel.add(new ItemSessel(getCanvas(),dialog.getValue(), window));
					moebel.get(moebel.size()-1).testMethode();
				}
			}
		});
		formToolkit.adapt(btnSessel, true, true);
		btnSessel.setText("Sessel");
		
		Button btnSofa = new Button(grpMbel, SWT.NONE);
		btnSofa.addSelectionListener(new SelectionAdapter() {
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
					moebel.add(new ItemSofa(getCanvas(),dialog.getValue(), window));
					moebel.get(moebel.size()-1).testMethode();
				}
			}
		});
		formToolkit.adapt(btnSofa, true, true);
		btnSofa.setText("Sofa");
		
		Button btnSideboard = new Button(grpMbel, SWT.NONE);
		btnSideboard.addSelectionListener(new SelectionAdapter() {
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
					moebel.add(new ItemSideboard(getCanvas(),dialog.getValue(), window));
					moebel.get(moebel.size()-1).testMethode();
				}
			}
		});
		formToolkit.adapt(btnSideboard, true, true);
		btnSideboard.setText("Sideboard");
		
		Button btnBett = new Button(grpMbel, SWT.NONE);
		formToolkit.adapt(btnBett, true, true);
		btnBett.setText("Bett");
		
		Button btnSchrankelement = new Button(grpMbel, SWT.NONE);
		formToolkit.adapt(btnSchrankelement, true, true);
		btnSchrankelement.setText("Schrankelement");
		
		Button btnNachttisch = new Button(grpMbel, SWT.NONE);
		formToolkit.adapt(btnNachttisch, true, true);
		btnNachttisch.setText("Nachttisch");
		
		Button btnDoppelbett = new Button(grpMbel, SWT.NONE);
		formToolkit.adapt(btnDoppelbett, true, true);
		btnDoppelbett.setText("Doppelbett");
		
		Button btnBadewanne = new Button(grpMbel, SWT.NONE);
		formToolkit.adapt(btnBadewanne, true, true);
		btnBadewanne.setText("Badewanne");
		fd_scale.left = new FormAttachment(0, 32);
		
		TabItem tbtmObjekte = new TabItem(tabFolder, SWT.NONE);
		tbtmObjekte.setText("Objekte");
		
		tree = new Tree(tabFolder, SWT.BORDER);
		tbtmObjekte.setControl(tree);
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
		
		grpObjektobjektname = new Group(composite_1, SWT.NONE);
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
		fd_grpPosition.top = new FormAttachment(0);
		fd_grpPosition.left = new FormAttachment(0);
		fd_grpPosition.bottom = new FormAttachment(0, 185);
		grpPosition.setLayoutData(fd_grpPosition);
		grpPosition.setText("Position");
		formToolkit.adapt(grpPosition);
		formToolkit.paintBordersFor(grpPosition);
		
		Label lblX = new Label(grpPosition, SWT.NONE);
		lblX.setBounds(10, 51, 10, 15);
		formToolkit.adapt(lblX, true, true);
		lblX.setText("X:");
		
		new_x = new Text(grpPosition, SWT.BORDER);
		new_x.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(btnAutomatischbernehmen.getSelection()) {
					for (Moebel moebel2 : moebel) {
						if(moebel2.isHighlighted()) {
							try {
							moebel2.setX(Integer.parseInt(new_x.getText()));
							}catch (NumberFormatException e) {}
						}
					}
				}
			}
		});
		new_x.setBounds(26, 48, 194, 21);
		formToolkit.adapt(new_x, true, true);
		
		new_y = new Text(grpPosition, SWT.BORDER);
		new_y.setBounds(26, 75, 194, 21);
		formToolkit.adapt(new_y, true, true);
		
		Label lblY = new Label(grpPosition, SWT.NONE);
		lblY.setText("Y:");
		lblY.setBounds(10, 78, 10, 15);
		formToolkit.adapt(lblY, true, true);
		
		new_angle = new Text(grpPosition, SWT.BORDER);
		new_angle.setBounds(55, 156, 165, 21);
		formToolkit.adapt(new_angle, true, true);
		
		Label lblWinkel = new Label(grpPosition, SWT.NONE);
		lblWinkel.setText("Winkel:");
		lblWinkel.setBounds(10, 159, 39, 15);
		formToolkit.adapt(lblWinkel, true, true);
		
		new_name = new Text(grpPosition, SWT.BORDER);
		new_name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(btnAutomatischbernehmen.getSelection()) {
					for (Moebel moebel2 : moebel) {
						if(moebel2.isHighlighted()) {
							grpObjektobjektname.setText("Objekt: " + new_name.getText());
							moebel2.setName(new_name.getText());
						}
						
					}
				}
			}
		});
		new_name.setBounds(48, 21, 172, 21);
		formToolkit.adapt(new_name, true, true);
		
		Label lblName = new Label(grpPosition, SWT.NONE);
		lblName.setText("Name:");
		lblName.setBounds(10, 24, 35, 15);
		formToolkit.adapt(lblName, true, true);
		
		new_height = new Text(grpPosition, SWT.BORDER);
		new_height.setBounds(48, 102, 172, 21);
		formToolkit.adapt(new_height, true, true);
		
		Label lblHhe = new Label(grpPosition, SWT.NONE);
		lblHhe.setText("H\u00F6he:");
		lblHhe.setBounds(10, 105, 35, 15);
		formToolkit.adapt(lblHhe, true, true);
		
		new_width = new Text(grpPosition, SWT.BORDER);
		new_width.setBounds(48, 129, 172, 21);
		formToolkit.adapt(new_width, true, true);
		
		Label lblBreite = new Label(grpPosition, SWT.NONE);
		lblBreite.setText("Breite:");
		lblBreite.setBounds(10, 132, 35, 15);
		formToolkit.adapt(lblBreite, true, true);
		
		Button btnbernehmen = new Button(grpObjektobjektname, SWT.NONE);
		fd_grpPosition.right = new FormAttachment(btnbernehmen, 0, SWT.RIGHT);
		btnbernehmen.setEnabled(true);
		btnbernehmen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			Moebel edit = null;
			for (Moebel moebel2 : moebel) {
				if(moebel2.isHighlighted()) edit = moebel2;
			}
			if(edit != null) {
				try {
				edit.setAll(Integer.parseInt(new_x.getText()), Integer.parseInt(new_y.getText()), Integer.parseInt(new_width.getText()), Integer.parseInt(new_height.getText()), edit.getOrigWidth(), edit.getOrigHeight(), Float.parseFloat(new_angle.getText()), edit.hasPaintListener(), edit.isHighlighted(),(Integer.parseInt(new_width.getText())/edit.origWidth),(Integer.parseInt(new_height.getText())/edit.origHeight));
				edit.setName(new_name.getText());
				}catch (NumberFormatException e1) {
					MessageBox err = new MessageBox(shlMbelplaner, SWT.OK | SWT.ICON_ERROR);
					err.setText("Error");
					err.setMessage("Zahl hat falsches format: \n" + e1.toString());
					err.open();
				}
			}
			}
		});
		
		
		FormData fd_btnbernehmen = new FormData();
		fd_btnbernehmen.right = new FormAttachment(100, -6);
		fd_btnbernehmen.bottom = new FormAttachment(100, -6);
		btnbernehmen.setLayoutData(fd_btnbernehmen);
		formToolkit.adapt(btnbernehmen, true, true);
		btnbernehmen.setText("\u00DCbernehmen");
		fd_btnbernehmen.left = new FormAttachment(0, 133);
		
		
		
		canvas = new Canvas(shlMbelplaner, SWT.BORDER | SWT.DOUBLE_BUFFERED);
		canvas.addGestureListener(new GestureListener() {
			public void gesture(GestureEvent arg0) {
				try {
					if(arg0.detail == SWT.GESTURE_BEGIN) {
						for (Moebel moebel2 : moebel) if(moebel2.contains(new java.awt.Point(arg0.x, arg0.y)) && moebel2.isHighlighted()) gestureMoebel = moebel2;
						magHeight = gestureMoebel.getHeight();
						magWidth = gestureMoebel.getWidth();
						rotAngle = gestureMoebel.getAngle();
					}
					if(gestureMoebel != null && arg0.detail == SWT.GESTURE_ROTATE) {
						gestureMoebel.setAngle((float)(rotAngle - arg0.rotation));
					}
					if(arg0.detail == SWT.GESTURE_END) {
						gestureMoebel = null;
					}
					if(gestureMoebel != null && arg0.detail == SWT.GESTURE_MAGNIFY) {
						gestureMoebel.setxScale((float)((magWidth*arg0.magnification)/gestureMoebel.getOrigWidth()));
						gestureMoebel.setyScale((float)((magHeight*arg0.magnification)/gestureMoebel.getOrigHeight()));
					}
				} catch(Exception e){}
			}
		});
		canvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent arg0) {
				switch (currentTool) {
				case "universal":
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
					break;
					
				default:
					break;
				}
				
				
			}
		});
		canvas.addDragDetectListener(new DragDetectListener() {
			public void dragDetected(DragDetectEvent arg0) {
				switch (currentTool) {
				case "universal":
					try {
						for (Moebel moebel : moebel) {
							if(moebel.isHighlighted()) dragMoebel = moebel;
						}
						if (dragMoebel.contains(new java.awt.Point(arg0.x,arg0.y))) drag = true; //Drag innerhalb von Moebel
						else mouseDown = true; //Drag außerhab von Moebel
						} catch(Exception e) {}
					break;
				
				default:
					break;
				}
				
				
				
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				switch (currentTool) {
				case "universal":
					Moebel tmp_moebel = null;
					for (Moebel moebel : moebel) {
						if(moebel.hasPaintListener() && moebel.contains(new java.awt.Point(e.x,e.y))) tmp_moebel = moebel;
						moebel.setHighlight(false);
					}
					if(tmp_moebel != null) {
						moebel.remove(tmp_moebel); moebel.add(tmp_moebel);
						tmp_moebel.setHighlight(true);
						grpObjektobjektname.setText("Objekt: " + tmp_moebel.getName());
					} else {
						new_angle.setText("");
						new_x.setText("");
						new_y.setText("");
						new_width.setText("");
						new_height.setText("");
						new_name.setText("");
						grpObjektobjektname.setText("Objekt: Kein Objekt ausgewählt");
					}
					break;

				default:
					break;
				}
				
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
				switch (currentTool) {
				case "remove":
					Iterator<Moebel> itr = moebel.iterator();
					while(itr.hasNext()) {
						Moebel itrMoebel = itr.next();
						if(itrMoebel.hasPaintListener() && itrMoebel.contains(new java.awt.Point(e.x,e.y))) {
							itrMoebel.hide(getCanvas());
							itr.remove();
						}
					}
					break;

				default:
					break;
				}
			}
		});
		
		
		fd_tabFolder.right = new FormAttachment(canvas, -6);

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
					moebel.add(new TestObjekt(getCanvas(),dialog.getValue(), window));
					moebel.get(moebel.size()-1).testMethode();
					
				}
			}
		});
		
		shlMbelplaner.addListener(SWT.Close, new Listener() {
		      public void handleEvent(Event event) {
		    	  if(hasChanged) {
						MessageBox msg = new MessageBox(shlMbelplaner,SWT.YES|SWT.NO|SWT.CANCEL);
						msg.setText("Möbelplaner");
						msg.setMessage("Möchten Sie die Änderungen speichern?");
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
		
		
		//öffenen einer Datei, die als 1. Parameter übergeben wurde
		
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
        			moebel.hide(getCanvas());
        		}
        		moebel = new ArrayList<Moebel>();
        		for (int i = 0; i < ja.length(); i++) {
        			JSONObject jo = new JSONObject(ja.get(i).toString());
        			moebel.add((Moebel)Class.forName(jo.getString("type")).getDeclaredConstructor(new Class[] {Canvas.class,String.class}).newInstance(new Object[] {getCanvas(),jo.getString("name")}));
        			Moebel tmp = moebel.get(moebel.size()-1);
        			tmp.setAll(jo.getInt("x"), jo.getInt("y"), jo.getInt("width"), jo.getInt("height"), jo.getInt("origWidth"), jo.getInt("origHeight") , jo.getInt("angle"), jo.getBoolean("hasPaintListener"), jo.getBoolean("highlight"), jo.getFloat("scale"), jo.getFloat("yScale"));
        		}
        		hasStar = hasChanged = false;
        		shlMbelplaner.setText("M\u00F6belplaner - " + savepath);
				
			} catch(Exception e1) {};
		}
	}
	
	
	//Getter & Setter
	
	public Canvas getCanvas() {
		return canvas;
	}
	public Tree getTree() {
		return tree;
	}
	public TreeItem gettrtmMoebel() {
		return trtmMoebel;
	}
	public Shell getShell() {
		return shlMbelplaner;
	}
	public void sethasChanged(boolean changed) {
		hasChanged = changed;
		if(changed && !hasStar ) {
			shlMbelplaner.setText(shlMbelplaner.getText()+"*");
			hasStar = true;
		}
	}
	public Text getNew_x() {
		return new_x;
	}

	public void setNew_x(Text new_x) {
		this.new_x = new_x;
	}

	public Text getNew_y() {
		return new_y;
	}

	public void setNew_y(Text new_y) {
		this.new_y = new_y;
	}

	public Text getNew_angle() {
		return new_angle;
	}

	public void setNew_angle(Text new_angle) {
		this.new_angle = new_angle;
	}

	public Text getNew_name() {
		return new_name;
	}

	public void setNew_name(Text new_name) {
		this.new_name = new_name;
	}

	public Text getNew_height() {
		return new_height;
	}

	public void setNew_height(Text new_height) {
		this.new_height = new_height;
	}

	public Text getNew_width() {
		return new_width;
	}

	public void setNew_width(Text new_width) {
		this.new_width = new_width;
	}
	
	public Group getGrpObjektobjektname() {
		return grpObjektobjektname;
	}
	
	
	
	//Dateispeicherung
	
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