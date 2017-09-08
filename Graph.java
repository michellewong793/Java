import java.util.*;
import java.io.*;
import java.lang.*;

/**Implements a graph.
 *@author: Michelle Wong
 */

public class Graph<V,E>{
    /**The list of edges*/
    protected ArrayList<Edge> edgeList;

    /**The list of nodes*/
    protected ArrayList<Node> nodeList;
    
    /**Constructor initializes with empty node and edge lists.*/
    public Graph(){
	edgeList = new ArrayList<Edge>();
	nodeList = new ArrayList<Node>();
    }

    /**Implements a graph's edge.*/
    public class Edge{
	/**the data*/
	private V data;
	/**the head*/
	private Node head;
	/**the tail*/
	private Node tail;

	/**Constructor of an edge.*/
	public Edge(V data, Node head, Node tail){
	    this.data = data;
	    this.head = head;
	    this.tail = tail; 
  	}
	
	/**Empty Constructor used in some methods*/
	public Edge(){
	}
	/**Accessor for head*/
	public Node getHead(){
	    return head; 
	}	
	/**Accessor for tail*/
	public Node getTail(){
	    return tail; 
	}
	/**Accessor for data*/
	public V getData(){
            return data;
        }
	/**Manipulator for data*/
	public void setData(V data){
	    this.data = data; 
	}
	
	/**Accessor for opposite node*/
	public Node oppositeTo(Node node){
	    Node opposite;
	    if(node == this.getHead()){
		opposite = this.tail;
	    }else{
		opposite = this.head;
	    }
	    return opposite;     
	}
	
	/**Function that tests if two edges are equal.
	 *Two edges are equal if their end nodes and start nodes are equal.
	 *@params: edge2 is the edge we are comparing to
	 */ 
	public boolean equals(Edge edge2){
	    boolean equality = false;
	    if((this.head == edge2.getHead()) && (this.tail == edge2.getTail())
	       || (this.head == edge2.getTail() && this.tail == edge2.getHead())){
		equality = true;
	    }
	    return equality;
	}
	
	/**Rewriting the hashcode function so that two edges that are equal will return same hash code.*/
	public int hashCode(){    
	    int hash;
	    hash = this.getHead().hashCode() + this.getTail().hashCode(); //two edges that have the same head or tails will return same hash code
	    return hash;
	}
    }
    
    /**Implements a graph's node.*/
    public class Node{
	/**The list of edges*/
	protected ArrayList<Graph<V,E>.Edge> edgeList;
	/**The data*/
	public E data; 

	/**Constructor of a disconnected node.*/
	public Node(E data, ArrayList<Graph<V,E>.Edge> edgeList){
	    this.data = data;
	    this.edgeList = edgeList;
	}
	       	
	/**Empty constructor*/
	public Node(){
	}

	/**Adds an edge to the edge list*/
        public void addEdgeRef(Edge edge){
	    edgeList.add(edge);
	}
	
	/**Returns the edge to a specified node, or null if it doesn't exist.*/
	public Edge edgeTo(Node neighbor){
	    Edge returnEdge = new Edge();
	    /**This is the edge that may or may not exist.*/
	    for(Graph<V,E>.Edge edge: edgeList){ //for each edge in the edgelist
		if((edge.getHead() == neighbor && edge.getTail() == this) || (edge.getHead() == this && edge.getTail() == neighbor)){
		    returnEdge = edge; //return the edge if the edge's head is the neighbor and this is the tail, or vice versa 
		}
	    }
	    return returnEdge;
	}
	/**Accessor for data*/
        public E getData(){
            return data;
        }
	/**Accessor  for neighbors of a node.*/
	public LinkedList<Node> getNeighbors(){
	    /**A linkedlist for the nodes that are neighbors*/
	    LinkedList<Node> list = new LinkedList<Node>();
	    for(Node node: nodeList){
		if(this.isNeighbor(node)){
		    list.add(node);
		}
	    }
	    return list;
	}
	
	/**Tests if a node is a neighbor to the node.*/
	public boolean isNeighbor(Node node2){
	    boolean neighboring = false; 
	    for(Graph<V,E>.Edge edge: edgeList){
		//if the node2 is the head or the tail of some edge in the edgelist 
		//get the data of the edge we are writing the function on
	        if(new Graph<V,E>.Edge(edge.getData(),node2, this).equals(edge) || new Graph<V,E>.Edge(edge.getData(),this,node2).equals(edge)){
		    neighboring = true;
		}
	    }
	    return neighboring;
	}
	/**Removes an edge from the edge list*/
	public void removeEdgeRef(Edge edge){
	    edgeList.remove(edge);
	}
	/**Manipulator for data*/
        public void setData(E data){
            this.data = data;
        }
    }

    //Beginning of graph methods.

    /**Accessor for nodes*/
    public Node getNode(int i){
	return nodeList.get(i);
    }
    /**Accessor for edges*/
    public Edge getEdge(int i){
	return edgeList.get(i);
    }

    /**Accessor for specific edge*/
    public Edge getEdgeRef(Node head, Node tail){
	Edge returnEdge = new Edge();
	for(Edge edge: edgeList){
	    if((edge.getHead() == head) && (edge.getTail() == tail)||
	       (edge.getTail() == head) && (edge.getHead() == tail)){
		returnEdge = edge; 
	    }
	}
	return returnEdge;
    }
    
    /**Accessor for number of nodes*/
    public int numNodes(){
	return nodeList.size();
    }
    
    /**Accessor  for number of edges*/
    public int numEdges(){
	return edgeList.size();
    }
    
    /**Adds a node to graph.*/
    public Node addNode(E data){
	/**The new node's array list.*/
	ArrayList<Graph<V,E>.Edge> list = new ArrayList<Graph<V,E>.Edge>();
        /**The new node.*/
        Node node = new Node(data,list);
        nodeList.add(node);
	return node;
    }

    /**Adds an edge to the graph.*/
    public Edge addEdge(V data, Node head, Node tail){
	/**The new edge.*/
	Edge edge = new Edge(data, head, tail);
	edgeList.add(edge);
	head.edgeList.add(edge);
	tail.edgeList.add(edge);
	return edge;
    }
    
    /**Removes a node.*/
    public void removeNode(Node node){
	/**Edge reference we want to remove*/
	Edge remEdge = new Edge(); 
	nodeList.remove(node); //removes node from masterlist
	//finds the edges that the node is connected to and removes them 
	for(Edge edge: edgeList){
	    if((node == edge.getHead()) || (node == edge.getTail())){
		remEdge = edge;
	    }
	}
	edgeList.remove(remEdge);
    }

    /**Removes an edge.*/
    public void removeEdge(Edge edge){
	edgeList.remove(edge); //removes the edge from the edgelist
	Edge remEdge = new Edge();
	for(Node node: nodeList){ //for each node
	    for(Edge edge1: node.edgeList){//for each edge in the edge list
		if(edge.equals(edge1)){//if the edge is equal to the edge we want to remove
		    remEdge = edge1;
		}
	    }
	    node.edgeList.remove(remEdge);  
	}
    }

    /**Removes an edge*/
    public void removeEdge(Node head, Node tail){
	for(Edge edge: edgeList){ //for each edge in the edge list
	    if((edge.getHead() == head) && (edge.getTail() == tail)
	       || ((edge.getHead() == tail) && (edge.getTail() == head))){//if the edge is equal to the edge we want to remove
		edgeList.remove(edge);//remove the edge
	    }
	}
    }

    /**Returns nodes not on a given list.*/
    public HashSet<Node> otherNodes(HashSet<Node> group){
	/**This is the hashset we want to return*/
	HashSet<Node> returnNodes = new HashSet<Node>();
	for(Node node: nodeList){
	    if(!group.contains(node)){
		returnNodes.add(node);
	    }//if the node is not in the hashset of group, add it to return
	}
	return returnNodes;
    }

    /**Returns nodes that are endpoints of a list of edges.*/
    public HashSet<Node> endpoints(HashSet<Edge> edges){
	/**This is the hashset we want to return of endpoints*/
	HashSet<Node> returnNodes = new HashSet<Node>();
	for(Edge edge: edgeList){
	    //add the endpoints of the edges into the hashset if they arent already there
	    if(!returnNodes.contains(edge.getHead())){
		returnNodes.add(edge.getHead());
	    }
	    if(!returnNodes.contains(edge.getTail())){
		returnNodes.add(edge.getTail());
	    }
	}
	return returnNodes;
    }
    
    /**Performs a breadth-first traversal of the graph. Returns hashset of edges for the traversal.*/
    public LinkedList<Graph.Edge> BFT(Graph.Node start){
	/**Creates a queue*/
	LinkedList<Graph.Node> queue = new LinkedList<Graph.Node>();
	/**HashSet of seen nodes*/
	HashSet<Graph.Node> seen = new HashSet<Graph.Node>();
	/**HashSet of visited nodes*/
	HashSet<Graph.Node> visited = new HashSet<Graph.Node>();
	/**List of edges to return*/
	LinkedList<Graph.Edge> edges = new LinkedList<Graph.Edge>();
	/**List of nodes to get the edge references to add to edge list*/
	LinkedList<Graph.Node> nodes = new LinkedList<Graph.Node>();

	queue.add(start);
	seen.add(start);

	System.out.println("Breadth first traversal:");

	while(!queue.isEmpty()){
	    Graph.Node popped = queue.getFirst(); //take something off the queue
	    visited.add(popped); //mark as visited
	    nodes.add(popped); //adds to node list so we can get the edges
	    // System.out.print(popped.getData()+ " "); //prints out the node
	 
	    //for all nodes, if they are a neighbor, if they have not been seen yet, mark as seen and add to queue
	    for(Graph.Node node: nodeList){
		if(node.isNeighbor(popped)){
		    if(!seen.contains(node)){
			seen.add(node); 
			queue.add(node);
			edges.add(getEdgeRef(popped,node));
		    }
		}
	    }
	    queue.removeFirst();
	}
	//print the edge list
	//for(Graph.Edge edge: edges){
	//  System.out.println(edge.getData());
	//}
	return edges;
}
    
    
    /**Method that performs the DFT traversal through recursion*/
    private LinkedList<Graph.Edge> DFT(HashSet<Graph.Node> visited, LinkedList<Graph.Edge> edges, LinkedList<Graph.Node> nodes, Graph.Node start){
	if(visited.contains(start)){
	    return edges;
	}else{
	    visited.add(start);
	    //	    for(Graph.Node node: visited){
	    //System.out.println(node.getData());
	    //}
	    /**List of neighbors*/
	    LinkedList<Graph.Node> neighbors = start.getNeighbors();
	    for(Graph.Node node: neighbors){
		if(!(visited.contains(node))){
		    if(!edges.contains(getEdgeRef(start,node))){
			edges.add(getEdgeRef(start,node));
		    }
		    DFT(visited, edges, nodes, node);
		}
            }
        }	
	return edges;
    }
    
    
    /**Performs a depth-first traversal of graph. Returns hashset of edges for the traversal.*/
    public LinkedList<Graph.Edge> DFT(Graph.Node start){
	/**LinkedList for the edges*/
	LinkedList<Graph.Edge> edges = new LinkedList<Graph.Edge>();
	/**List of nodes to get the edge references to add to edge list*/
        LinkedList<Graph.Node> nodes = new LinkedList<Graph.Node>();
	/**HashSet of visited nodes*/
        HashSet<Graph.Node> visited = new HashSet<Graph.Node>();
	System.out.println("Depth-first traversal");
	return DFT(visited, edges, nodes, start);
    }	
    
    /**Prints a represenation of the graph.*/
    public void print(){		
	for(Node node: nodeList){ //for each node in the nodelist
	    System.out.print("These are the nodes connected to "+node.getData()+ ": ");
	    for(Edge edge: node.edgeList){ //for each edge in the nodelist
		if(edge.getHead() == node){ //if the node is the head    
		    System.out.print(edge.getTail().getData() + " ");
		}
		else if(edge.getTail() == node){ 
		    System.out.print(edge.getHead().getData()+ " ");
		}
	    }
	    
	    System.out.println();
	}
    }
    
    /**Uses Dijkstra's shortest path algorithm to compute distances to nodes*/
    public static <S,T extends Number> HashMap<Graph<S,T>.Node,Double> distances(Graph<S,T> graph, Graph<S,T>.Node start){
	/**Hashset for the best cost to node so far*/
	HashMap<Graph<S,T>.Node,Double> costs = new HashMap<Graph<S,T>.Node,Double>();
	/**Hashset for the homeward sign post*/
	HashMap<Graph<S,T>.Node, Graph<S,T>.Node> homeDirection = new HashMap<Graph<S,T>.Node,Graph<S,T>.Node>();
	/**Hashset for visited nodes*/
	HashSet<Graph<S,T>.Node> visited1 = new HashSet<Graph<S,T>.Node>();
	
	//INITIALIZE COSTS
	costs.put(start, 0.0);
	for(Graph<S,T>.Node node: graph.nodeList){
	    if(!(costs.containsKey(node))){
		costs.put(node,Double.POSITIVE_INFINITY);
	    }
	}
	//INITIALIZE HOMEWARD NODES
	Graph<S,T>.Node nullHome = null;
	for(Graph<S,T>.Node node: graph.nodeList){
	    if(!(homeDirection.containsKey(node))){
		homeDirection.put(node,nullHome);
	    }
	}
	while(visited1.size() < graph.nodeList.size()){
	    /**the minimum value*/
	    Double minValue= Double.MAX_VALUE;
	    /**key with minumum value*/
	    Graph<S,T>.Node minKey = null;
	    //LOOP THROUGH TO FIND MINKEY  
	    if(minKey == null){ 
		minKey = graph.nodeList.get(0); //minkey as first in the node 
		//iterate through for the minkey
		for(Graph<S,T>.Node node: graph.nodeList){
		    if((!visited1.contains(node)) && costs.get(node) < minValue){
			minKey = node;
			minValue = costs.get(node);
		    }
		}
	    }
	    
	    visited1.add(minKey); //add key to visited
	    
	    if(minKey != null){
		/**Get the neighbors of minKey*/
		LinkedList<Graph<S,T>.Node> neighbors = minKey.getNeighbors();
		for(Graph<S,T>.Node node: neighbors){ 
			if(graph.getEdgeRef(minKey, node).getTail() == node){ //if the neighbor is a tail 
			    Double initialCost = graph.getEdgeRef(minKey,node).getData().getCost() + costs.get(minKey); 
			    if(initialCost < costs.get(node)){
				costs.put(node,initialCost);
				homeDirection.put(node,minKey);
			    }
			}
		}
	    }
	    
	}
	return costs;   
    }
    
    /**Checks the consistency of the graph.*/
    public void check(){
	for(Edge edge: edgeList){
	    if(edge.getHead() != null && edge.getTail() != null){ //if the gethead and gettail methods do not return null
	    }else{ 
		System.out.println("Edge: "+edge + "with head "+ edge.getData() + "does not have a head or tail reference.");
	    }
	    if(edgeList.contains(edge)) { //if every edge is contained in the edgelist 
	    }
	    else{
		System.out.println("Edge: "+edge+ "does not have a mainlist reference.");
	    }
	    if(edge.getHead().edgeList.contains(edge)){
	    }else{
		System.out.println("Reference error.");
	    }
	    if(edge.getTail().edgeList.contains(edge)){
	    }else{
		System.out.println("Reference error.");
	    }
	}
	for(Node node: nodeList){
	    //checks that the for each node, for each edge in its edgelist, the edge has a reference back to that node as a head or a tail 
	    for(Edge edge: node.edgeList){
		if(edgeList.contains(edge)) { //if every edge is contained in the edgelist                                                                        
		}
		else{
		    System.out.println("Error in node references.");
		}
	    }
	    
	}
    }
}

    
