package safetymaintenance.parts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import org.osgi.framework.Bundle;

public class Main {

	private static GraphClass graph = new GraphClass();
	private static GraphView visualGraph = new GraphView();
	private static NodeInfoView infoView = new NodeInfoView();
	
	public static void main(String[] args) {
	
		
		for(String source: graph.getCriticalClasses()) {
			graph.traverseGraphFrom(source);
		}
	}
}
