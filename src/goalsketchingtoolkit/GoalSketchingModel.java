/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.Observable;
import java.util.ArrayList;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GoalSketchingModel extends Observable  {
    
    

   /* private GraphNode rootGraphNode;
    private ArrayList<GoalSketchingNode> goalSketchingNodes = new ArrayList<>();

    /**
     * Sets the root graph node of this goal sketching model (goal graph).
     *
     * @param root the root graph node.
     
    public void setRootGraphNode(GraphNode root) {

        this.rootGraphNode = root;
        //setChanged();
        //notifyObservers();

        this.goalSketchingNodes.add(root);

    }
    
    public GraphNode getRootGraphNode() {

        return this.rootGraphNode;

    }

    /**
     * Appends a child graph node to a given parent graph node.
     *
     * @param parent the parent graph node.
     * @param child the child graph node to add to the parent.
     
    public void addChildGraphNode(GraphNode parent, GraphNode child) {

        parent.addChildNode(child);
        setChanged();
        notifyObservers();

        this.goalSketchingNodes.add(child);

    }

    /**
     * Appends an operationalizer node to a given parent graph node.
     *
     * @param parent the parent graph node.
     * @param ozn the operationalizer node to add to the parent.
     
    public void addOperationalizerNode(GraphNode parent, OperationalizerNode ozn) {

        parent.setOperationalizerNode(ozn);
        setChanged();
        notifyObservers();

        this.goalSketchingNodes.add(ozn);

    }

    public void addToGoalSketchingNodes(GraphNode gn) {

        this.goalSketchingNodes.add(gn);
        notifyView();

    }

    public void addOpToGoalSketchingNodes(OperationalizerNode on) {

        setChanged();
        notifyObservers();
        this.goalSketchingNodes.add(on);

    }
    
    public void addTerminationGoalSketchingNodes(AssumptionTerminationNode astn) {

        this.goalSketchingNodes.add(astn);
        notifyView();

    }

    public ArrayList getGoalSketchingNodes() {

        return this.goalSketchingNodes;

    }

    /**
     * Sets the child graph node list for a given graph node.
     *
     * @param children the list of children to set.
     */
    /*public void setChildGraphNodes(GraphNode parent, ArrayList children) {

     parent.setChildGraphNodes(children);
        
     for (Object o : children) {
            
     GraphNode gn = (GraphNode) o;
     this.goalSketchingNodes.add(gn);
            
     } 
        
     setChanged();
     notifyObservers();

     } */
    /* public void moveGoalSketchingNode(GoalSketchingNode gsn) {
        
     for (Object o : goalSketchingNodes) {
            
     GoalSketchingNode n = (GoalSketchingNode) o;
            
     if(n.getX() == gsn.getX() && n.getY() == gsn.getY() && n.getHeight() == gsn.getHeight() && n.getWidth() == gsn.getWidth()) {
     //m 
     }
            
     } */
    public void notifyView() {

        setChanged();
        notifyObservers();

    }
/*
    public void reset() {

        for (int i = goalSketchingNodes.size() - 1; i >= 0; i--) {
            goalSketchingNodes.remove(i);
        }
    }*/

}
