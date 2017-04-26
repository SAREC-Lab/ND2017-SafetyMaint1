package safetymaintenance.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;

public class NodeInfoView extends ViewPart {
	private Label label;
	private Graph graph = GraphView.visualGraph;

	
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		label = new Label(parent, 0);
    	label.setText("HEY");
		
		
		graph.addSelectionListener(new SelectionAdapter() {

			@Override
            public void widgetSelected(SelectionEvent e) {
            	//System.out.println(e);
            	//Text text = new Text(parent, SWT.BORDER);
            	//label = new Label(parent, 0);
            	//label.setText("HEY");
            }
    });
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		label.setFocus();
	}
}
