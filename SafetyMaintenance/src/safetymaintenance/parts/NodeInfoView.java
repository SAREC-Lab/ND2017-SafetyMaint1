package safetymaintenance.parts;

import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;

public class NodeInfoView extends ViewPart {
	private Label label;
	private Graph graph = GraphView.visualGraph;
	private GraphClass graphClass = new GraphClass();

	@Override
	public void createPartControl(Composite parent) {
		updateNodeInfo(parent);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(new IPartListener() {

			@Override
			public void partOpened(IWorkbenchPart part) {

				if (part instanceof CompilationUnitEditor) {
					updateNodeInfo(parent);
				}
			}

			public void partBroughtToTop(IWorkbenchPart part) {
				// TODO Auto-generated method stub

				if (part instanceof CompilationUnitEditor) {
					updateNodeInfo(parent);
				}
			}

			public void partClosed(IWorkbenchPart part) {
				// TODO Auto-generated method stub

			}

			public void partDeactivated(IWorkbenchPart part) {
				// TODO Auto-generated method stub

			}

			public void partActivated(IWorkbenchPart part) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void updateNodeInfo(Composite parent) {
		// TODO Auto-generated method stub
		graph = GraphView.visualGraph;
		if (label == null) {
			label = new Label(parent, 0);
		}
		System.out.println("BACK AGAIN");

		// Selection event for node clicks
		graph.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String id = e.item.toString().substring(16);
				String newID = "";
				String descrip = graphClass.nodes.get(id).getDescription();
				label.setText(id + ": " + descrip);
			}
		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		label.setFocus();
	}
}
