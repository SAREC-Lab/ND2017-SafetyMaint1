package safetymaintenance.parts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.swt.graphics.Color;

public class View extends ViewPart {
        public static final String ID = "Safety Maintenance View";
        private Graph visualGraph;
        private int layout = 1;
        public Color codeColor = new Color(null,155,255, 204); 
        public Color fmecaColor = new Color(null,255,145,160);
        public Color assumptColor = new Color(null,100,255,255);
        public Color desColor = new Color(null,255,153,51);
        public Color reqColor = new Color(null,204,153,255);
        
        private static GraphClass graph = new GraphClass();
        
        public void createVisualGraph() {
        	createPartControl(visualGraph);
        }
        
        public void createPartControl(Composite parent) {
                // Graph will hold all other objects
                visualGraph = new Graph(parent, SWT.NONE);
                // now a few nodes
                
                // Get critical classes
                for(String source: graph.getCriticalClasses()) {
        			graph.traverseGraphFrom(source);
        		}
   
                Set<String> cc = graph.getCriticalClasses();
                Iterator<String> it = cc.iterator();
                String criticalClass = null;
                for (int i=0; i<10; i++) {
                	criticalClass = it.next();
                }
                
                // Get requirements for specified critical class
        		ArrayList<String> requirements = graph.getRequirements(criticalClass);
        		
        		// Set class node
        		GraphNode classNode = new GraphNode(visualGraph, SWT.NONE, criticalClass);
        		classNode.setBackgroundColor(codeColor);
        		classNode.setHighlightColor(new Color(null, 255,255,90));
        		
        		// Traverse class to connect requirements, design decisions, fmeca, and assumptions
        		for (int i=0; i<requirements.size(); i++) {
	        		GraphNode requirementNode = new GraphNode(visualGraph, SWT.NONE, requirements.get(i));
	        	    requirementNode.setBackgroundColor(reqColor);
	                requirementNode.setHighlightColor(new Color(null,255,255,90));
	        		
	        		ArrayList<String> designDecisions = graph.getDesign(requirements.get(i));
	        		ArrayList<String> fmecas = graph.getFMECA(requirements.get(i));
	        		ArrayList<String> assumptions = graph.getAssumptions(requirements.get(i));
	        		
	                new GraphConnection(visualGraph, SWT.NONE, classNode, requirementNode);
	                for (int j=0; j<designDecisions.size(); j++) {
	                	GraphNode designNode = new GraphNode(visualGraph,SWT.NONE, designDecisions.get(j));
	                	designNode.setBackgroundColor(desColor);
	                	designNode.setHighlightColor(new Color(null,255,255,90));
	                	new GraphConnection(visualGraph, SWT.NONE, requirementNode, designNode);    
	                }
	                for (int k=0; k<fmecas.size(); k++) {
	                	GraphNode fmecaNode = new GraphNode(visualGraph,SWT.NONE, fmecas.get(k));
	                	fmecaNode.setBackgroundColor(fmecaColor);
	                	fmecaNode.setHighlightColor(new Color(null,255,255,90));
	                	new GraphConnection(visualGraph, SWT.NONE, requirementNode, fmecaNode);
	                }
	                for (int l=0; l<assumptions.size(); l++) {
	                	GraphNode assumptionNode = new GraphNode(visualGraph,SWT.NONE, assumptions.get(l));
	                	assumptionNode.setBackgroundColor(assumptColor);
	                	assumptionNode.setHighlightColor(new Color(null,255,255,90));
	                	new GraphConnection(visualGraph, SWT.NONE, requirementNode, assumptionNode);
	                }
        		}
                
                // Lets have a directed connection
                /*new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, node1,
                                node2);
                // Lets have a dotted graph connection
                new GraphConnection(graph, ZestStyles.CONNECTIONS_DOT, node2, node3);
                // Standard connection

                
                new GraphConnection(graph, SWT.NONE, node3, node1);
                new GraphConnection(graph, SWT.NONE, node4, node5);
                // Change line color and line width
                GraphConnection graphConnection = new GraphConnection(graph, SWT.NONE,
                                node1, node4);
                graphConnection.changeLineColor(parent.getDisplay().getSystemColor(
                                SWT.COLOR_GREEN));*/
                // Also set a text
               // graphConnection.setText("This is a text");
                //graphConnection.setHighlightColor(parent.getDisplay().getSystemColor(
                 //               SWT.COLOR_RED));
               // graphConnection.setLineWidth(3);

                visualGraph.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
                // Selection listener on graphConnect or GraphNode is not supported
                // see https://bugs.eclipse.org/bugs/show_bug.cgi?id=236528
                visualGraph.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                                System.out.println(e);
                        }

                });
        }
        /**
         * Passing the focus request to the viewer's control.
         */
        public void setFocus() {
        }
}