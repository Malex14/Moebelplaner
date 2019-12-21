package designer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Export extends Dialog{
	private Button btnAbsolut;
	private Button btnRadioButton;
	private Spinner spinner;
	private Spinner spinner_1;
	private Spinner spinner_2;
	private int percent;
	private int width;
	private int height;
	private boolean abs;
	private Gui Gui;
	private float ratio;
	
	protected Export(Shell parentShell,Gui Gui) {
		super(parentShell);
		this.Gui = Gui;
	}

	@Override
    protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("M\u00F6belplaner - Exportieren");
		newShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		newShell = new Shell();
		newShell.setSize(280, 245);

	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
	
		
		
		spinner = new Spinner(container, SWT.BORDER);
		spinner.setBounds(11, 61, 53, 22);
		spinner.setMaximum(1000);
		spinner.setMinimum(1);
		spinner.setSelection(100);
		spinner.setBounds(106, 8, 68, 22);
		
		Label label = new Label(container, SWT.NONE);
		label.setBounds(11, 91, 10, 15);
		label.setText("%");
		label.setBounds(180, 11, 10, 15);
		
		Button btnSeitenverhltnisBeibehalten = new Button(container, SWT.CHECK);
		btnSeitenverhltnisBeibehalten.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnSeitenverhltnisBeibehalten.getSelection()) {
					ratio = (float)spinner_1.getSelection()/(float)spinner_2.getSelection();
				}
			}
		});
		btnSeitenverhltnisBeibehalten.setEnabled(false);
		btnSeitenverhltnisBeibehalten.setBounds(11, 114, 169, 16);
		btnSeitenverhltnisBeibehalten.setSelection(true);
		btnSeitenverhltnisBeibehalten.setBounds(20, 54, 169, 16);
		btnSeitenverhltnisBeibehalten.setText("Seitenverh\u00E4ltnis beibehalten");
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setEnabled(false);
		lblNewLabel.setBounds(30, 76, 56, 15);
		lblNewLabel.setText("Pixelgr\u00F6\u00DFe");
		
		spinner_1 = new Spinner(container, SWT.BORDER);
		spinner_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnSeitenverhltnisBeibehalten.getSelection()) getSpinner_2().setSelection((int) (spinner_1.getSelection()/ratio));
			}
		});
		spinner_1.setEnabled(false);
		spinner_1.setMaximum(2147483647);
		spinner_1.setBounds(106, 98, 68, 22);
		
		spinner_2 = new Spinner(container, SWT.BORDER);
		spinner_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnSeitenverhltnisBeibehalten.getSelection()) getSpinner_1().setSelection((int) (spinner_2.getSelection() * ratio));
			}
		});
		spinner_2.setEnabled(false);
		spinner_2.setMaximum(2147483647);
		spinner_2.setBounds(106, 126, 68, 22);
		
		Label lblPixel = new Label(container, SWT.NONE);
		lblPixel.setEnabled(false);
		lblPixel.setBounds(180, 101, 55, 15);
		lblPixel.setText("Pixel");
		
		Label label_1 = new Label(container, SWT.NONE);
		label_1.setEnabled(false);
		label_1.setText("Pixel");
		label_1.setBounds(180, 129, 55, 15);
		
		Label lblBreite = new Label(container, SWT.NONE);
		lblBreite.setEnabled(false);

		lblBreite.setBounds(40, 101, 55, 15);
		lblBreite.setText("Breite:");
		
		Label lblHhe = new Label(container, SWT.NONE);
		lblHhe.setEnabled(false);

		lblHhe.setBounds(40, 129, 55, 15);
		lblHhe.setText("H\u00F6he:");

		
		btnRadioButton = new Button(container, SWT.RADIO);
		btnRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblBreite.setEnabled(false);
				lblHhe.setEnabled(false);
				label_1.setEnabled(false);
				lblHhe.setEnabled(false);
				spinner_1.setEnabled(false);
				spinner_2.setEnabled(false);
				lblPixel.setEnabled(false);
				btnSeitenverhltnisBeibehalten.setEnabled(false);
				lblNewLabel.setEnabled(false);
				spinner.setEnabled(true);
				label.setEnabled(true);
			}
		});
		btnRadioButton.setBounds(10, 10, 90, 16);
		btnRadioButton.setText("Prozentual:");
		
		btnAbsolut = new Button(container, SWT.RADIO);
		btnAbsolut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblBreite.setEnabled(true);
				lblHhe.setEnabled(true);
				label_1.setEnabled(true);
				lblHhe.setEnabled(true);
				spinner_1.setEnabled(true);
				spinner_2.setEnabled(true);
				lblPixel.setEnabled(true);
				btnSeitenverhltnisBeibehalten.setEnabled(true);
				lblNewLabel.setEnabled(true);
				
				spinner.setEnabled(false);
				label.setEnabled(false);
			}
		});
		btnAbsolut.setBounds(11, 37, 65, 16);
		btnRadioButton.setSelection(true);
		btnAbsolut.setText("Absolut:");
		btnAbsolut.setBounds(10, 32, 90, 16);
		spinner_1.setMinimum(1);
		spinner_1.setSelection(Gui.getCanvas().getBounds().width);
		spinner_2.setMinimum(1);
		spinner_2.setSelection(Gui.getCanvas().getBounds().height);
		ratio = (float)Gui.getCanvas().getBounds().width/(float)Gui.getCanvas().getBounds().height;
		System.out.println(ratio);
		return container;
	}
	
	private Spinner getSpinner_1() {
		return spinner_1;
	}

	private Spinner getSpinner_2() {
		return spinner_2;
	}

	private void saveInput() {
        percent = spinner.getSelection();
        width = spinner_1.getSelection();
        height = spinner_2.getSelection();
        abs = btnAbsolut.getSelection();
    }
	
	public boolean getBtnAbsolut() {
		return abs;
	}
	public int getPercent() {
		return percent;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	 @Override
	    protected void okPressed() {
	        saveInput();
	        super.okPressed();
	    }
}