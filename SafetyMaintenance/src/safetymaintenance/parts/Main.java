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
	
		//graph.readClasses();
		//graph.readCriticalClasses();
		//visualGraph.createVisualGraph();
		
		// Functions that will help get design decisions and fmeca for certain requirement
		Set<String> cc = graph.getCriticalClasses();
        Iterator<String> it = cc.iterator();
        String criticalClass = it.next();
		
		ArrayList<String> requirementList = graph.getRequirements(criticalClass);
		ArrayList<String> designList = graph.getDesign(requirementList.get(0));
		ArrayList<String> FMECAList = graph.getFMECA(requirementList.get(0));
		
		System.out.println(requirementList.get(0));
		for (String d: designList) {
			System.out.println(d);
		}
		for (String f: FMECAList) {
			System.out.println(f);
		}
		
		for(String source: graph.getCriticalClasses()) {
			graph.traverseGraphFrom(source);
		}
		
	
		
		/*for (String source: graph.getCriticalClasses()) {
			System.out.println(source);	
			ArrayList<String> requirementList = graph.getRequirements(source);
		}*/
		
		
	}
}
