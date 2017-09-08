import java.awt.*;
import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**Class that controls the GUI for Graph.
 *@author: Michelle Wong
 */

public class GraphGUI{
    /**Creates an instance of the GraphViewer class.*/
    private GraphCanvas canvas;
    
    /**Creates an instance of the graph.*/
    private Graph<DisplayEdgeData,DisplayNodeData> graph = new Graph<DisplayEdgeData,DisplayNodeData>();
    
    /**Label for the input mode instructions*/
    private JLabel instr;

    /**The input mode*/
    InputMode mode = InputMode.ADD_POINTS;

    /**Remembers the point where last mousedown event occurred*/
    Point pointUnderMouse;

    /**                                                                         
     *  Schedules a job for the event-dispatching thread                        
     *  creating and showing this application's GUI.                            
     */
    public static void main(String[] args) {
        final GraphGUI GUI = new GraphGUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GUI.createAndShowGUI();
                }
            });
    }

    /** Sets up the GUI window */
    public void createAndShowGUI() {
        // Make sure we have nice window decorations.                           
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.                                        
        JFrame frame = new JFrame("Graph GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

	// Add components                                                       
        createComponents(frame);

        // Display the window.                                                  
        frame.pack();
        frame.setVisible(true);
    }
    
    	    
    /**Creates the components for the GUI*/
    public void createComponents(JFrame frame){
	// graph display
	Container pane = frame.getContentPane();
        pane.setLayout(new FlowLayout());
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        canvas = new GraphCanvas(graph);
        PointMouseListener pml = new PointMouseListener();
        canvas.addMouseListener(pml);
        canvas.addMouseMotionListener(pml);
        panel1.add(canvas);
        instr = new JLabel("Click to add new points; drag to move. Click on a node and another node to add new edge.");
        panel1.add(instr,BorderLayout.NORTH);
        pane.add(panel1);

	// controls                                                             
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(4,1));
        
	JButton addPointButton = new JButton("Add/Move Nodes");
        panel2.add(addPointButton);
        addPointButton.addActionListener(new AddPointListener());
        
	JButton rmvPointButton = new JButton("Remove Nodes");
        panel2.add(rmvPointButton);
        rmvPointButton.addActionListener(new RmvPointListener());
	
	JButton addEdgeButton = new JButton("Add Edges");
        panel2.add(addEdgeButton);
        addEdgeButton.addActionListener(new AddEdgeListener());
	
	JButton rmvEdgeButton = new JButton("Remove Edges");
        panel2.add(rmvEdgeButton);
        rmvEdgeButton.addActionListener(new RmvEdgeListener());
        
	JButton breadthButton = new JButton("Breadth-first traversal");
	panel2.add(breadthButton);
	breadthButton.addActionListener(new BreadthListener());

	JButton depthButton = new JButton("Depth-first traversal");
	panel2.add(depthButton);
	depthButton.addActionListener(new DepthListener());

	JButton shortPathButton = new JButton("Shortest Paths to node");
	panel2.add(shortPathButton);
	shortPathButton.addActionListener(new ShortListener());

	pane.add(panel2);
	}
    
    /**                                                                         
     * Returns a point found within the drawing radius of the given location,   
     * or null if none                                                          
     *                                                                          
     *  @param x  the x coordinate of the location                              
     *  @param y  the y coordinate of the location                              
     *  @return  a point from the canvas if there is one covering this location\
,                                                                               
*  or a null reference if not                                              
*/
    public Point findNearbyPoint(int x, int y) {
	/**This is the point we want to return*/
        Point returnPt = new Point(0,0);
        for(Graph<DisplayEdgeData,DisplayNodeData>.Node node: graph.nodeList){
	    if(node.getData().getPoint().distance((double)x, (double)y) <= 5){
	    returnPt = node.getData().getPoint();
	    }
        }
        return returnPt;
    }
   
    /**Returns a nearby node from a point*/
    public Graph.Node findNearbyNode(Point pt){
	/**This is the node we want to return*/
	Graph<DisplayEdgeData,DisplayNodeData>.Node rtNode = null;
	for(Graph<DisplayEdgeData,DisplayNodeData>.Node node: graph.nodeList){
	    Point nodePoint = node.getData().getPoint();
	    if((pt.getX() == nodePoint.getX())&&
	       pt.getY() == nodePoint.getY()){
		rtNode = node;
	    }
	}
	return rtNode;
    }
    
   
    /** Constants for recording the input mode */
    enum InputMode {
        ADD_POINTS, RMV_POINTS, ADD_EDGES, RMV_EDGES, BFT, DFT, SHORTEST_PATH
            }

    /** Listener for AddPoint button */
    private class AddPointListener implements ActionListener {
        /** Event handler for AddPoint button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.ADD_POINTS;
            instr.setText("Click to add new points.");
        }
    }

    /** Listener for RmvPoint button */
    private class RmvPointListener implements ActionListener {
        /** Event handler for RmvPoint button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.RMV_POINTS;
            instr.setText("Click to remove points or edges.");

        }
    }

    /** Listener for AddEdge button */
    private class AddEdgeListener implements ActionListener {
        /** Event handler for AddPoint button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.ADD_EDGES;
            instr.setText("Click and drag from one node to another to add an edge.");
        }
    }

    /** Listener for RmvEdge button */
    private class RmvEdgeListener implements ActionListener {
        /** Event handler for RmvPoint button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.RMV_EDGES;
            instr.setText("Click and drag from one node to another to remove an edge.");
        }
    }
	
    /**Listener for breadth first traversal*/
    private class BreadthListener implements ActionListener{
	/**Event handler for BreadthFirst Traversal button*/
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.BFT;
	    instr.setText("Breadth first traversal of the graph.");
	}
    }

    /**Listener for depth first traversal*/
    private class DepthListener implements ActionListener{
	/**Event handler for DepthFirst Traversal button*/
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.DFT;
	    instr.setText("Depth first traversal of the graph");
	}
    }

    /**Listener for shortest path algorithm*/
    private class ShortListener implements ActionListener{
	/**Event handler for Shortest Path Algorithm*/
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.SHORTEST_PATH;
	    instr.setText("Click on a node to find the value of the paths from every other node to it.");
	}
    }

    /** Mouse listener for GraphCanvas element */
    private class PointMouseListener extends MouseAdapter
        implements MouseMotionListener {
	/**A variable for the head of an edge we might want to add*/
        Graph<DisplayEdgeData, DisplayNodeData>.Node head;
        /**A variable for the tail of an edge we might want to add*/
        Graph<DisplayEdgeData,DisplayNodeData>.Node tail;
	/**Counter for node labels*/
	int i = 0;
	
	/**Adds a new node on click in add mode, removes the node clicked on remove mode.*/
        public void mouseClicked(MouseEvent e) {
	    /**The x coordinate of the click*/
            int x = e.getX();
            /**The y coordinate of the click*/
            int y = e.getY();
	    i++;
            switch (mode) {
            case ADD_POINTS:
		DisplayNodeData data = new DisplayNodeData(new Point(x,y),"Node "+String.valueOf(i), Color.black,null);
		graph.addNode(data);
		canvas.repaint();		
		break;
		
	    case RMV_POINTS:
		if(findNearbyPoint(x,y) !=null){
		    head = findNearbyNode(findNearbyPoint(x,y));
		    graph.removeNode(head);
		    head = null;
		}
                break;	
	    
	    case BFT:
		/**This is a point from find nearby point*/
		Point pt = findNearbyPoint(x,y);
		LinkedList<Graph.Edge> edges = graph.BFT(findNearbyNode(pt));
		for(Graph<DisplayEdgeData,DisplayNodeData>.Edge edge: edges){
		    System.out.println("Edge from " + edge.getHead().getData().getLabel()+ " to " + edge.getTail().getData().getLabel());
		   
		}
		break;
	    case DFT:
		/**This is the point from find nearby point*/
		Point pt1 = findNearbyPoint(x,y);
		LinkedList<Graph.Edge> edges1 = graph.DFT(findNearbyNode(pt1));
		for(Graph<DisplayEdgeData,DisplayNodeData>.Edge edge : edges1){
		    System.out.println("Edge from " + edge.getHead().getData().getLabel()+ " to " + edge.getTail().getData().getLabel());
		}
		break;
		
		//DOES NOT WORK 
	    case SHORTEST_PATH:
		/**This is the point we want to get the paths to*/
		Point pt2 = findNearbyPoint(x,y);
		//static method for the shortest path of the nodes of the graph
		//HashMap<Graph.Node, Double> paths = Graph.distances(graph,findNearbyNode(pt2));
		//for(Graph<DisplayEdgeData,DisplayNodeData>.Node node: graph.nodeList){
		//  node.getData().setDistance((paths.get(node))); //set the distance to a double from the hashmap
		    
		}
	    }
	    canvas.repaint();
		
	}
	
	/** Should save a head node for both cases so we can either add or delete an edge from upcoming tail*/
        public void mousePressed(MouseEvent e) {
            // Record point under mouse, if any                                                                    
            /**The x coordinate of the click*/
            int x = e.getX();
            /**The y coordinate of the click*/
            int y = e.getY();
	    
	    //saves the point under mouse if it is in the nodelist 
	    for(Graph<DisplayEdgeData,DisplayNodeData>.Node node: graph.nodeList){
		if((node.getData().getPoint().getX() == findNearbyPoint(x,y).getX())
		   && node.getData().getPoint().getY() == findNearbyPoint(x,y).getY()){
		    pointUnderMouse = findNearbyPoint(x,y);
		} 
	    }

	    switch(mode){
	    
	    case ADD_EDGES:
	    //ADD AN EDGE                                                                                                          
	    //finds a node associated with the pointundermouse to keep as head of edge                                              
		if(pointUnderMouse != null){
		    head = findNearbyNode(findNearbyPoint(x,y));
		}
		
		
		break;
	    case RMV_EDGES:
                //REMOVE AN EDGE                                                                                                       
                //finds a node associated with pointundermouse to keep as head of edge to remove
                if(pointUnderMouse != null){
		    head = findNearbyNode(findNearbyPoint(x,y));
                }
                break;
	    }
	}
	/**Saves a tail so we can either add an edge or remove it.*/
        public void mouseReleased(MouseEvent e) {
       	    int x = e.getX();
	    int y = e.getY(); 
	    
	    //saves the point under mouse if it is in the nodelist                                                                      
            for(Graph<DisplayEdgeData,DisplayNodeData>.Node node: graph.nodeList){
                if((node.getData().getPoint().getX() == findNearbyPoint(x,y).getX())
                   && node.getData().getPoint().getY() == findNearbyPoint(x,y).getY()){
                    pointUnderMouse = findNearbyPoint(x,y);
                }
            }

	    switch(mode){
	    case ADD_POINTS:
		// Clear record of point under mouse, if any     
		if(pointUnderMouse != null){
		    pointUnderMouse = null;
		}
		break;
	    case RMV_POINTS:
		// Clear record of point under mouse, if any                                                                           
		if(pointUnderMouse != null){
                    pointUnderMouse = null;
                }
		break;

	    case ADD_EDGES:
		//ADD EDGE
		if((head != null) && (findNearbyNode(findNearbyPoint(x,y) != null)){
		    tail = findNearbyNode(findNearbyPoint(x,y));
		    graph.addEdge(new DisplayEdgeData(0.0,null,Color.black), head, tail);
		    //		    System.out.println("graph edge size= " + graph.edgeList.size());
		    canvas.repaint();
		    if(pointUnderMouse != null){
			pointUnderMouse = null;
			
		    }
		}
		break;
		
	    case RMV_EDGES: 
		if(head != null){
		    tail = findNearbyNode(findNearbyPoint(x,y));
		    
		    if(graph.getEdgeRef(head,tail) != null){
			graph.removeEdge(graph.getEdgeRef(head,tail));
			canvas.repaint();
			if(pointUnderMouse != null){
			    pointUnderMouse = null;
			}
		    }
		}   
		
		break;
	    }
	}
	    
	    
	/** Changes the point under mouse's coordinates to the current mouse coordinates and updates display */
	    public void mouseDragged(MouseEvent e) {
            /**The x coordinate of the click*/
            int x = e.getX();
            /**The y coordinate of the click*/
            int y = e.getY();
            switch(mode){
	    case ADD_POINTS:
		if(pointUnderMouse != null){
		    for(Graph<DisplayEdgeData,DisplayNodeData>.Node node: graph.nodeList){
			if((node.getData().getPoint().getX() == pointUnderMouse.getX()) && 
			   node.getData().getPoint().getY() == pointUnderMouse.getY()){
			    pointUnderMouse.x = x;
			    pointUnderMouse.y = y;
			    canvas.repaint();
			}
		    }
		}
		break;
	    }
	}	    
        // Empty but necessary to comply with MouseMotionListener interface.
	public void mouseMoved(MouseEvent e) {}
    }



    
}