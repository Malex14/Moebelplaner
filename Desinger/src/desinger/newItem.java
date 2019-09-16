package desinger;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
@SuppressWarnings("unused")

public class newItem extends Dialog {

	protected Object result;
	protected Shell shlObjektnamenDefinieren;
	private Text text;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public newItem(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlObjektnamenDefinieren.open();
		shlObjektnamenDefinieren.layout();
		Display display = getParent().getDisplay();
		while (!shlObjektnamenDefinieren.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlObjektnamenDefinieren = new Shell(getParent(), getStyle());
		shlObjektnamenDefinieren.setSize(340, 131);
		shlObjektnamenDefinieren.setText("Objektnamen definieren");
		
		Button btnOk = new Button(shlObjektnamenDefinieren, SWT.NONE);
		
		btnOk.setSelection(true);
		btnOk.setBounds(249, 72, 75, 25);
		btnOk.setText("OK");
		
		Button btnAbbrechen = new Button(shlObjektnamenDefinieren, SWT.NONE);
		btnAbbrechen.setBounds(168, 72, 75, 25);
		btnAbbrechen.setText("Abbrechen");
		
		Group grpBitteGebenSie = new Group(shlObjektnamenDefinieren, SWT.NONE);
		grpBitteGebenSie.setText("Bitte geben Sie einen Namen f\u00FCr das Objekt ein");
		grpBitteGebenSie.setBounds(10, 10, 314, 56);
		
		text = new Text(grpBitteGebenSie, SWT.BORDER);
		text.setToolTipText("Objektname");
		text.setBounds(10, 25, 294, 21);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(text.getText()=="") {
					
				}
				else {
					result = text;
					shlObjektnamenDefinieren.close();
				}
				
			}
		});
	}
}
