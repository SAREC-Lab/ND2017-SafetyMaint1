

import java.util.ArrayList;

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
        public static final String ID = "de.vogella.zest.first.view";
        private Graph visualGraph;
        private int layout = 1;
        public Color codeColor = new Color(null,255,255,0); 
        public Color fmecaColor = new Color(null,255,0,0);
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
                
                GraphNode node1 = new GraphNode(visualGraph, SWT.NONE, "Code");
                GraphNode node2 = new GraphNode(visualGraph, SWT.NONE, "FMECA");
                GraphNode node3 = new GraphNode(visualGraph, SWT.NONE, "Assumption");
                GraphNode node4 = new GraphNode(visualGraph, SWT.NONE, "Design");
                GraphNode node5 = new GraphNode(visualGraph, SWT.NONE, "Requirement");
                
                node1.setBackgroundColor(codeColor);
                node2.setBackgroundColor(fmecaColor);
                node3.setBackgroundColor(assumptColor);
                node4.setBackgroundColor(desColor);
                node5.setBackgroundColor(reqColor);
                
                node1.setHighlightColor(new Color(null, 155,255, 204));
                node2.setHighlightColor(new Color(null, 155,255, 204));
                node3.setHighlightColor(new Color(null, 155,255, 204));
                node4.setHighlightColor(new Color(null, 155,255, 204));
                node5.setHighlightColor(new Color(null, 155,255, 204));
                
                ArrayList<GraphNode> graphNodes = new ArrayList<GraphNode>();
                graphNodes.add(node1);
                graphNodes.add(node2);
                graphNodes.add(node3);
                graphNodes.add(node4);
                
                for (GraphNode g:graphNodes) {
                	new GraphConnection(visualGraph, SWT.NONE, node5, g);
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

                visualGraph.setLayoutAlgorithm(new SpringLayoutAlgorithm(
                                LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
                // Selection listener on graphConnect or GraphNode is not supported
                // see https://bugs.eclipse.org/bugs/show_bug.cgi?id=236528
                visualGraph.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                                System.out.println(e);
                        }

                });
        }

        public void setLayoutManager() {
                switch (layout) {
                case 1:
                        visualGraph.setLayoutAlgorithm(new TreeLayoutAlgorithm(
                                        LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
                        layout++;
                        break;
                case 2:
                        visualGraph.setLayoutAlgorithm(new SpringLayoutAlgorithm(
                                        LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
                        layout = 1;
                        break;

                }

        }

        /**
         * Passing the focus request to the viewer's control.
         */
        public void setFocus() {
        }
}