import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
 *This class keeps the data of each graph and draws it.
 *@author: Michelle Wong
 *
 */

public class GraphCanvas extends JComponent{
    /**The graph*/
    Graph<DisplayEdgeData, DisplayNodeData> graph;
    
    /**The list of edges*/
    ArrayList<Graph.Edge> edges; 

    /**The constructor.*/
    public GraphCanvas(Graph graph){
	this.graph = graph;
	this.edges = graph.edgeList;
    }
    
    /**Draws the graph.*/
    public void paintComponent(Graphics g){
	for(Graph<DisplayEdgeData,DisplayNodeData>.Node node: graph.nodeList){
	    Point point = node.getData().getPoint();
	    g.setColor(Color.magenta);
	    g.drawOval((int)point.getX()-5, (int)point.getY()-5, 10, 10); 
	    g.fillOval((int)point.getX()-5, (int)point.getY()-5, 10,10);
	    
	    //prints label
	    String nodeLabel = node.getData().getLabel();
	    g.setColor(Color.black);
	    g.drawString(nodeLabel, (int)point.getX()+10, (int)point.getY()+10);
	 
	    //prints distance label 
	    if(node.getData().getDistance() != null){
		Double distanceLabel = node.getData().getDistance();
		g.setColor(Color.blue);
		g.drawString(Double.toString(distanceLabel), (int)point.getX()+20, (int)point.getY()+20);
	    }
	}

	
	for(Graph<DisplayEdgeData,DisplayNodeData>.Edge edge: edges){
	    g.setColor(Color.black);
	    Graph<DisplayEdgeData,DisplayNodeData>.Node head = edge.getHead();
	    Graph<DisplayEdgeData,DisplayNodeData>.Node tail = edge.getTail();
	    Point start = head.getData().getPoint();
	    Point end = tail.getData().getPoint();
	    g.drawLine((int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY());
	    
	}
    }
    
    /**
     *  The component will look bad if it is sized smaller than this
     *
     *  @returns The minimum dimension
     */
    public Dimension getMinimumSize() {
        return new Dimension(500,3000);
    }

    /**
     *  The component will look best at this size
     *
     *  @returns The preferred dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(500,300);
    }

}