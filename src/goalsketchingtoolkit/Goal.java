/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;
import java.awt.geom.Point2D;

/**
 * Describes a node on a goal graph which may be a parent, child or leaf node.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class Goal extends GoalSketchingNode {

    /**
     * The goal oriented proposition for this graph node.
     */
    private Proposition proposition;

    /**
     * The unique id for this graph node.
     */
    private String id;

    /**
     * The list of children graph nodes for this graph node.
     */
    private ArrayList<GraphNode> children = new ArrayList<>();

    /**
     * The list of operationalizers for this graph node contained in an
     * operationalizer node.
     */
    private OperationalizerNode operationalizerNode;

    private AssumptionTerminationNode assumptionTerminationNode;

    /**
     * Specifies whether this graph node is a parent or not.
     */
    private boolean parent;

    /**
     * Specifies whether this graph node is a child or not.
     */
    private boolean child;
    
    private boolean operationalized;
    
    private boolean terminated;

    /**
     * The parent graph node for this graph node if it has one.
     */    
    private GraphNode par;

    /**
     * The description for this graph node if it is a description node.
     */
    private String description;
    //private boolean hasTwin;

    private boolean root;

    /**
     * Constructs a new graph node with no proposition, description or ID.
     */
    public GraphNode() {
        super();
    }

    /**
     * Constructs a graph node with no specified proposition for the purposes of
     * describing description nodes.
     *
     * @param x the starting x position of the graph node.
     * @param y the starting y position of the graph node.
     * @param width the width of the graph node.
     * @param height the height of the graph node.
     * @param description the description of the graph node.
     * @param id the unique identifier for this graph node.
     */
    public GraphNode(double x, double y, int width, int height, String description, String id) {

        super(x, y, width, height);
        this.description = description;
        this.parent = true;
        this.id = id;

    }

    /**
     * Constructs a graph node with a specified proposition, id and boolean
     * value which denotes if this graph node is a parent or not.
     *
     * @param x the starting x position of the graph node.
     * @param y the starting y position of the graph node.
     * @param width the width of the graph node.
     * @param height the height of the graph node.
     * @param proposition the proposition of this graph node.
     * @param parent a true boolean value if this graph node is a parent, false
     * otherwise.
     * @param id the unique identifier for this graph node.
     */
    public GraphNode(double x, double y, int width, int height, Proposition proposition, boolean parent, boolean child, String id) {

        super(x, y, width, height);
        this.proposition = proposition;
        this.parent = parent;
        this.child = child;
        this.id = id;
        //this.children = new ArrayList<>();

    }

    /**
     * Sets the id of this graph node.
     *
     * @param id the id.
     */
    public void setID(String id) {

        this.id = id;

    }

    /**
     * Returns the id of this graph node.
     *
     * @return the id.
     */
    public String getId() {

        return id;

    }

    /**
     * Sets the goal oriented proposition for this graph node.
     *
     * @param proposition the proposition to specify.
     */
    public void setProposition(Proposition proposition) {

        this.proposition = proposition;

    }

    /**
     * Returns the goal oriented proposition for this graph node.
     *
     * @return the proposition of this graph node.
     */
    public Proposition getProposition() {

        return proposition;

    }

    /**
     * Appends a child graph node to this graph node and sets a reference to
     * this graph node as the child's parent.
     *
     * @param child the child graph node to add to this graph node.
     */
    public void addChildNode(GraphNode child) {

        child.setParent(this);
        children.add(child);

    }

    /**
     * Sets the child graph node list for this graph node.
     *
     * @param children the list of children to set.
     */
    public void setChildGraphNodes(ArrayList<GraphNode> children) {

        for (GraphNode c : children) {
            c.setParent(this);
        }
        this.children = children;

    }

    /**
     * Returns the list of child graph nodes for this graph node.
     *
     * @return the list of this graph node's children.
     */
    public ArrayList getChildNodes() {

        return children;

    }

    /**
     * Sets whether this graph node is a parent or not.
     *
     * @param parent true if this graph node is a parent, false otherwise.
     */
    public void setIsParent(boolean parent) {

        this.parent = parent;

    }

    /**
     * Sets whether this graph node is a child or not.
     *
     * @param child true if this graph node is a child, false otherwise.
     */
    public void setIsChild(boolean child) {

        this.child = child;

    }

    /**
     * Sets whether this graph node is operationalized or not.
     *
     * @param operationalized true if this graph node operationalized, false
     * otherwise.
     */
    public void setIsOperationalized(boolean operationalized) {

        this.operationalized = operationalized;

    }
    
    /**
     * Gets whether this graph node is operationalized or not.
     *
     * @return true if this graph node is operationalized, false otherwise.
     */
    public boolean isOperationalized() {

        return operationalized;

    }

    /**
     * Returns whether this graph node is a parent or not.
     *
     * @return true if this graph node is a parent, false otherwise.
     */
    public boolean isParent() {

        return parent;

    }

    /**
     * Returns whether this graph node is a child or not.
     *
     * @return true if this graph node is a child, false otherwise.
     */
    public boolean isChild() {

        return child;

    }

    /**
     * Returns whether this graph node is both a parent and child or not.
     *
     * @return true if this graph node is a parent and child, false otherwise.
     */
    public boolean isParentAndChild() {

        return child && parent;

    }

    /**
     * Sets the operationalizer node of this graph node.
     *
     * @param ozn the operationalizer node.
     */
    public void setOperationalizerNode(OperationalizerNode ozn) {

        ozn.setParent(this);
        this.operationalizerNode = ozn;

    }
    
    /**
     * Returns the operationalizer node of this graph node.
     *
     * @return operationalizer node of this graph node.
     */
    public OperationalizerNode getOperationalizerNode() {

        return operationalizerNode;

    }

    /**
     * Sets the Assumption Termination node of this graph node.
     *
     * @param ozn the Assumption Termination node.
     */
    public void setAssumptionTerminationNode(AssumptionTerminationNode astn) {

        astn.setParent(this);
        this.assumptionTerminationNode = astn;

    }
    
    /**
     * Returns the Assumption Termination node of this graph node.
     *
     * @return Assumption Termination node of this graph node.
     */
    public AssumptionTerminationNode getAssumptionTerminationNode() {

        return assumptionTerminationNode;

    }

    public void setIsTerminated(boolean terminated) {
        
        this.terminated = terminated;
        
    }
    
    public boolean isTerminated() {
        
        return this.assumptionTerminationNode != null;
        
    }

    /**
     * Appends an operationalizer to the operationalizer node for this graph
     * node.
     *
     * @param operationalizer the operationalizer to add.
     */
    public void addOperationalizer(Operationalizer operationalizer) {

        this.operationalizerNode.addOperationalizer(operationalizer);

    }

    /**
     * Appends an operationalizer with a given sub domain to the operationalizer
     * node for this graph node.
     *
     * @param operationalizer the operationalizer to add.
     * @param subDomain the sub domain of the operationalizer to add.
     */
    public void addOperationalizer(Operationalizer operationalizer, String subDomain) {

        operationalizer.addSubDomain(subDomain);
        this.operationalizerNode.addOperationalizer(operationalizer);

    }

    /**
     * Appends an operationalizer with a given list of sub domains to the
     * operationalizer node for this graph node.
     *
     * @param operationalizer the operationalizer to add.
     * @param subDomains the sub domains of the operationalizer to add.
     */
    public void addOperationalizer(Operationalizer operationalizer, ArrayList subDomains) {

        operationalizer.setSubDomains(subDomains);
        this.operationalizerNode.addOperationalizer(operationalizer);

    }

    /**
     * Returns whether this graph node is a operationalized or not.
     *
     * @return true if this graph node is operationalized, false otherwise.
     */
    public boolean isOperationalized2() {

        return this.operationalizerNode != null;

    }

    /**
     * Sets the reference to this graph node's parent.
     *
     * @param parent the specified parent graph node for this graph node.
     */
    public void setParent(GraphNode parent) {

        this.par = parent;

    }

    /**
     * Returns the reference to this graph node's parent.
     *
     * @return the parent graph node of this graph node.
     */
    public GraphNode getParent() {

        return this.par;

    }

    //getDescription()
    public double getRefinementBottomX() {

        return getX() + getWidth() / 2;

    }

    public double getRefinementBottomY() {

        return getY() + getHeight() + 100;

    }

    public double getRefinementCentreX() {

        return getX() + getWidth() / 2;

    }

    public double[] getRefinementIntersection(double x1, double y1, double x2, double y2) {

        double off_x = x1 - x2;
        double off_y = y1 - y2;
        double R = 10;
        //double[] intersectionArray;

        double ls = off_x * off_x + off_y * off_y;

        //if (ls < R * R) {
        //  intersectionArray = new double[0];
        //return intersectionArray 
        //  } else {
        double scale = R / Math.sqrt(ls);
        double res_x = off_x * scale + x2;
        double res_y = off_y * scale + y2;
        double[] intersectionArray = {res_x, res_y};
        // }

        return intersectionArray;

    }

    public double getRefinementCentreY() {

        return getY() + getHeight() + 90;

    }

    public void setIsRootNode(boolean isRoot) {

        this.root = isRoot;

    }

    public boolean isRootNode() {

        return this.root;

    }

}
