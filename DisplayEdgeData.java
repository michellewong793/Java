import java.util.*;
import java.lang.*;
import java.io.*;
import java.awt.*;

/**Class that stores the data for a node to be displayed in a GUI.*/

public class DisplayEdgeData extends Number{
    /**The color of the edge*/
    Color color; 
    
    /**The label of the edge*/
    String label; 

    /**The cost of going to that edge*/
    Double cost; 

    /**Constructor of the node data*/
    public DisplayEdgeData(Double cost, String label, Color color){
	this.label = label;
	this.color = color;
	this.cost = cost;
    }
    
    /**Accessor for label*/
    public String getLabel(){
	return label;
    }
    
    /**Accessor for cost*/
    public Double getCost(){
	return cost;
    }
    
    /**Manipulator for label*/
    public void setLabel(String label){
	this.label = label;
    }

    /**Accessor of color*/
    public Color getColor(){
	return color;
    }
    
    /**Manipulator for colors*/
    public void setColor(Color color){
	this.color = color;
    }
}