package safetymaintenance.parts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;
import org.eclipse.swt.graphics.Color;

public class GraphView extends ViewPart {
        public static final String ID = "Safety Maintenance View";
        public static Graph visualGraph;
        private int layout = 1;
        public Color codeColor = new Color(null,155,255, 204); 
        public Color fmecaColor = new Color(null,255,145,160);
        public Color assumptColor = new Color(null,100,255,255);
        public Color desColor = new Color(null,255,153,51);
        public Color reqColor = new Color(null,204,153,255);
        public Color criticalGreen = new Color(null, 49, 250, 0);
        public Color criticalYellow = new Color(null, 255, 255, 0);
        public Color criticalOrange = new Color(null, 255, 153, 0);
        public Color criticalRed = new Color(null, 255, 92, 51);

        public static GraphClass graph = new GraphClass();
        
        public void createVisualGraph() {
        	createPartControl(visualGraph);
        }
       
        
        public void createPartControl(Composite parent) {
                // Graph will hold all other objects
                visualGraph = new Graph(parent, SWT.NONE);

                int maxCritical = 0;
                
                // Get critical classses
                for(String source: graph.getCriticalClasses()) {
        			graph.traverseGraphFrom(source);
        		}
   
                Set<String> cc = graph.getCriticalClasses();
                Iterator<String> it = cc.iterator();
                String criticalClass = null;
                for (int i=0; i<10; i++) {
                	criticalClass = it.next();
                }
                //criticalClass = it.next();
                
                // Get requirements for specified critical class
        		ArrayList<String> requirements = graph.getRequirements(criticalClass);
        		Set<String> otherArtifacts = new HashSet<String>();
        		Map<String, GraphNode> otherArtifactNodes = new HashMap<String, GraphNode>();
        		
        		// Make criticality Node
    			GraphNode criticalityNode = new GraphNode(visualGraph, SWT.NONE, "Unknown");

        		// Set class node
        		GraphNode classNode = new GraphNode(visualGraph, SWT.NONE, criticalClass);
        		classNode.setBackgroundColor(codeColor);
        		classNode.setHighlightColor(new Color(null, 255,255,90));

            	int criticalNum = 0;

        		// Traverse class to connect requirements, design decisions, fmeca, and assumptions
        		for (int i=0; i<requirements.size(); i++) {
	        		// Set requirement node
        			String reqDescription = graph.getRequirementDescription(requirements.get(i));
        			GraphNode requirementNode;
        			if(otherArtifacts.contains(reqDescription)) {
        				requirementNode = otherArtifactNodes.get(reqDescription);
        			} else {
	        			requirementNode = new GraphNode(visualGraph, SWT.NONE, requirements.get(i));
		        	    requirementNode.setBackgroundColor(reqColor);
		                requirementNode.setHighlightColor(new Color(null,255,255,90));
		                otherArtifacts.add(reqDescription);
		                otherArtifactNodes.put(reqDescription, requirementNode);
        			}
	                
	                
	                // Node hover
	                IFigure requirementHover = new Label(reqDescription);
	        		requirementNode.setTooltip(requirementHover);
	                
	        		ArrayList<String> designDecisions = graph.getDesign(requirements.get(i));
	        		ArrayList<String> fmecas = graph.getFMECA(requirements.get(i));
	        		ArrayList<String> assumptions = graph.getAssumptions(requirements.get(i));
	        		
	                new GraphConnection(visualGraph, SWT.NONE, classNode, requirementNode);
	                for (int j=0; j<designDecisions.size(); j++) {
	        			String desDescription = graph.getDesDescription(requirements.get(i), designDecisions.get(j));
	        			GraphNode designNode;
	        			if(otherArtifacts.contains(desDescription)) {
	        				designNode = otherArtifactNodes.get(desDescription);
	        			} else {
		                	designNode = new GraphNode(visualGraph,SWT.NONE, designDecisions.get(j));
		                	designNode.setBackgroundColor(desColor);
		                	designNode.setHighlightColor(new Color(null,255,255,90));
		                	otherArtifacts.add(desDescription);
		                	otherArtifactNodes.put(desDescription, designNode);
	        			}
		                // Node hover
		                IFigure designHover = new Label(desDescription);
		        		designNode.setTooltip(designHover);
	                	
	                	new GraphConnection(visualGraph, SWT.NONE, requirementNode, designNode);    
	                }
	                
	                for (int k=0; k<fmecas.size(); k++) {
	        			String fmecaDescription = graph.getFmecaDescription(requirements.get(i), fmecas.get(k));      
	                	GraphNode fmecaNode;
	                	if(otherArtifacts.contains(fmecaDescription)) {
	                		fmecaNode = otherArtifactNodes.get(fmecaDescription);
	                	} else {
		                	fmecaNode = new GraphNode(visualGraph,SWT.NONE, fmecas.get(k));
		                	fmecaNode.setBackgroundColor(fmecaColor);
		                	fmecaNode.setHighlightColor(new Color(null,255,255,90));
		                	otherArtifacts.add(fmecaDescription);
		                	otherArtifactNodes.put(fmecaDescription, fmecaNode);
	                	}
	                	 // Node hover
		                IFigure fmecaHover = new Label(fmecaDescription);
		        		fmecaNode.setTooltip(fmecaHover);
	                	new GraphConnection(visualGraph, SWT.NONE, requirementNode, fmecaNode);

	               		// Set critical node
	                	String criticality = graph.nodes.get(fmecas.get(k)).getCriticality();
	                	System.out.println(criticality);
	                	
	            		if (criticality.equals("Critical")) {     
	            			criticalNum = 3;
	            		}
	       
	            		else if (criticality.equals("Medium Critical") && criticalNum < 2){
	            			criticalNum = 2;
	            		}
	            		else if (criticality.equals("Medium") && criticalNum < 1) {
	            			criticalNum = 1;
	            		}	
	                }
	                
	                for (int l=0; l<assumptions.size(); l++) {
	        			String assumpDescription = graph.getAssumpDescription(requirements.get(i), assumptions.get(l)); 
	        			GraphNode assumptionNode;
	                	if(otherArtifacts.contains(assumpDescription)) {
	                		assumptionNode = otherArtifactNodes.get(assumpDescription);
	                	} else {
	        				assumptionNode = new GraphNode(visualGraph,SWT.NONE, assumptions.get(l));
	        				assumptionNode.setBackgroundColor(assumptColor);
	        				assumptionNode.setHighlightColor(new Color(null,255,255,90));
	        				otherArtifacts.add(assumpDescription);
	        				otherArtifactNodes.put(assumpDescription, assumptionNode);
	                	}
	                	 // Node hover
		                IFigure assumpHover = new Label(assumpDescription);
		        		assumptionNode.setTooltip(assumpHover);
	                	new GraphConnection(visualGraph, SWT.NONE, requirementNode, assumptionNode);

	                }
	                
	              
        		}
        		if (criticalNum == 3) {
        			criticalityNode.setText("Critical");
        			//GraphNode criticalityNode = new GraphNode(visualGraph, SWT.NONE, "Critical");
        			criticalityNode.setBackgroundColor(criticalRed);
        		}
            	else if (criticalNum == 2){
            		criticalityNode.setText("Medium Critical");
        			//GraphNode criticalityNode = new GraphNode(visualGraph, SWT.NONE, "Medium Critical");
        			criticalityNode.setBackgroundColor(criticalOrange);
        		}
        		else if (criticalNum == 1) {
        			criticalityNode.setText("Medium");
        			//GraphNode criticalityNode = new GraphNode(visualGraph, SWT.NONE, "Medium");
        			criticalityNode.setBackgroundColor(criticalYellow);
        		}
        		
        		else if (criticalNum == 0) {	
        			criticalityNode.setText("Non-Critical");
        			//GraphNode criticalityNode = new GraphNode(visualGraph, SWT.NONE, "Non-Critical");
        			criticalityNode.setBackgroundColor(criticalGreen);
        		}

        		

                visualGraph.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
                visualGraph.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                                System.out.println(e.getClass().getName());
                                
                        }

                });
        }
        /**
         * Passing the focus request to the viewer's control.
         */
        public void setFocus() {
        }
}