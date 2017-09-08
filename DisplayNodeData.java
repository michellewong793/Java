import java.util.*;
import java.lang.*;
import java.io.*;
import java.awt.*;

/**Class that stores the data for a node to be displayed in a GUI.*/

public class DisplayNodeData{
    /**The coordinates of the node*/
    Point coord;
    /**The label for the node*/
    String label;
    /**The color of the node*/
    Color color; 
    /**The distance label*/
    Double distance;
    
    /**Constructor of the node data*/
    public DisplayNodeData(Point coord, String label, Color color, Double distance){
	this.coord= coord;
	this.label = label;
	this.color = color;
	this.distance = distance;
    }
    
    /**Accessor  for the distance after dijkstra's algorithm*/
    public Double getDistance(){
	return distance;
    }
    /**Accessor for the coords of the node*/
    public Point getPoint(){
	return coord;
    }
    
    /**Accessor for the colors of the node*/
    public Color getColor(){
	return color;
    }
    
    /**Accessor for the label*/
    public String getLabel(){
	return label;
    }
    
    /**Manipulator for the distance*/
    public void setDistance(Double distance){
	this.distance = distance;
    }

    /**Manipulator for the point*/
    public void setPoint(Point point){
	this.coord = point;
    }
    
    /**Manipulator for the color*/
    public void setColor(Color color){
	this.color = color;
    }
    
    /**Manipulator for the label*/
    public void setLabel(String label){
	this.label = label;
    }
}