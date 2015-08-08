 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.event.MouseEvent;
import javax.swing.JDialog;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GoalSketchingController implements GoalSketchingControllerInterface {

    private GoalGraphModelInterface model;
    private GoalSketchingView view;
    private GoalSketchingView.GSmouseListener mouseListener;
    private GoalSketchingView.EditGoalListener editGoalListener;

    private GraphParser parser;
    private GSnodeFactory factory;
    private GSnode currentSelection;

    private boolean rootNode;
    private int rootStartX;
    private int rootStartY;

    public static final int OR_ENTAILMENT_START_Y = 125;
    public static final int AND_ENTAILMENT_START_Y = 80;
    public static final int ENTAILMENT_START_LENGTH = 80;
    public static final int GOAL_START_WIDTH = 100;
    public static final int GOAL_START_HEIGHT = 60;
    public static final int PRODUCTS_START_WIDTH = 80;
    public static final int PRODUCTS_START_HEIGHT = 40;
    
    private int goalInsertShift = 5;

    public GoalSketchingController(GoalGraphModelInterface model) {

        this.model = model;
        view = new GoalSketchingView(model, this);

        view.createGUI();
        view.createContextualMenuControls();
        view.createFileControls();
        parser = new GraphParser();
        factory = new GSnodeFactory();
        mouseListener = view.getMouseListener();
        editGoalListener = view.getEditGoalListener();

    }

    /**
     * Returns the selected goal sketching node.
     *
     * @return the currently selected goal sketching node.
     */
    @Override
    public GSnode getCurrentSelection() {
        return this.currentSelection;
    }

    /**
     * Sets the selected goal sketching node.
     *
     * @param e the mouse event.
     */
    @Override
    public void setCurrentSelection(MouseEvent e) {

        int eventX = e.getX();
        int eventY = e.getY();

        ArrayList<Drawable> drawables = view.getDrawables();

        for (int i = drawables.size() - 1; i >= 0; i--) {

            Drawable d = drawables.get(i);

            if (d.contains(eventX, eventY)) {
                mouseListener.setMousePressed(true);
                currentSelection = d.getGSnode();
                //d.setSelected(true);
                model.notifyView();
                break;
            } //else {
               // d.setSelected(false);
           // }
        }
    }

    /**
     * Loads a goal graph.
     *
     * @param file the file path.
     */
    @Override
    public void loadGraph(String file) {

        try {

            Document xmlDoc = parser.getDocument(file);
            Element theRoot = xmlDoc.getDocumentElement();
            NodeList nodes = theRoot.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodes.item(i);

                    Goal root = parser.getNode(element);
                    model.addRootGoal(root);
                    drawGraphFromRoot(root);

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Recursively adds all goal sketching nodes from a root goal to the list in
     * the model.
     *
     * @param goal the root goal and then its subsequent children.
     */
    public void drawGraphFromRoot(Goal goal) {

        /*if (graphNode.isParent()) {

         ArrayList<GraphNode> childGraphNodes = graphNode.getChildNodes();
         addToGoalSketchingNodes(graphNode);

         for (int i = 0; i < childGraphNodes.size(); i++) {

         addToGoalSketchingNodes(childGraphNodes.get(i));
         drawGraphFromRoot(childGraphNodes.get(i));

         }
         }

         if (graphNode.isOperationalized()) {

         OperationalizerNode on = graphNode.getOperationalizerNode();
         addOpToGoalSketchingNodes(on);

         }

         if (graphNode.isTerminated()) {

         AssumptionTerminationNode an = graphNode.getAssumptionTerminationNode();
         addTerminationGoalSketchingNodes(an);

         } */
    }

    /**
     * Saves a goal graph.
     *
     * @param root the root goal.
     * @param fileName the file path.
     */
    @Override
    public void saveGraph(Goal root, String fileName) throws ParserConfigurationException {
        GraphBuilder gb = new GraphBuilder(root);
        Document doc = gb.build();

        //System.out.println("file path: "+fileName);
        try {
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(4));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * resets the model and view.
     */
    @Override
    public void reset() {
        view.reset();
        model.reset();
    }

    /**
     * Sets the root goal for the goal graph model.
     *
     * @param x the x coordinate of the root goal.
     * @param y the y coordinate of the root goal.
     */
    @Override
    public void addRootGoal(int x, int y) {

        Goal root = factory.createGoal(x, y, GOAL_START_WIDTH, GOAL_START_HEIGHT);
        root.setIsRootGoal(true);
        view.addDrawable(root.getGraphicalProperties());
        model.addRootGoal(root);
        rootNode = false;

    }

    /**
     * Adds an AND entailment to a goal.
     */
    @Override
    public void addAndEntailment() {

        Goal goal = (Goal) currentSelection;
        GSnodeGraphics g = goal.getGraphicalProperties();
        int x = g.getX() + g.getWidth() / 2;
        int y = g.getY() + g.getHeight();
        int toX = x;
        int toY = y + AND_ENTAILMENT_START_Y;
        ANDentailment ae = factory.createANDentailment(x, y, toX, toY, ENTAILMENT_START_LENGTH);
        currentSelection.addChild(ae);
        view.addDrawable(ae.getGraphicalProperties());
        model.addToGSnodes(ae);

    }

    /**
     * Adds an OR entailment to a goal.
     */
    @Override
    public void addOREntailment() {

        Goal goal = (Goal) currentSelection;
        GSnodeGraphics g = goal.getGraphicalProperties();
        int x = g.getX();
        int y = g.getY();
        int toX = x - 40;
        int toY = y + OR_ENTAILMENT_START_Y;
        int toX2 = x + 120;
        int toY2 = y + OR_ENTAILMENT_START_Y;
        ORentailment oe = factory.createORentailment(x, y, toX, toY, ENTAILMENT_START_LENGTH, toX2, toY2, ENTAILMENT_START_LENGTH);
        currentSelection.addChild(oe);
        view.addDrawable(oe.getGraphicalProperties());
        model.addToGSnodes(oe);

    }

    /**
     * Adds a leaf goal to a given parent.
     */
    @Override
    public void addLeafGoal() {

        String currentSelectionType = currentSelection.getClass().toString();

        if (currentSelectionType.contains("ANDentailment")) {

            ANDentailment ae = (ANDentailment) currentSelection;
            GSentailmentGraphics g = ae.getGraphicalProperties();

            int x = g.getX() - GOAL_START_WIDTH / 2;
            int y = g.getToY();
            ArrayList<GSnode> childNodes = ae.getChildren();

            if (childNodes != null && !childNodes.isEmpty()) {
                GSnodeGraphics gs = (GSnodeGraphics) childNodes.get(childNodes.size() - 1).getGraphicalProperties();
                x = gs.getX() + 15;
                y = gs.getY() + 15;
            } else {
                y += g.getLength();
            }

            
            Goal goal = factory.createGoal(x, y, GOAL_START_WIDTH, GOAL_START_HEIGHT);
            ae.addChild(goal);
            view.addDrawable(goal.getGraphicalProperties());
            model.addToGSnodes(goal);

        } else if (currentSelectionType.contains("ORentailment")) {
            ORentailment oe = (ORentailment) currentSelection;
            GSorEntailmentGraphics g = oe.getGraphicalProperties();
            int x = 0;
            int y = 0;
            ArrayList<GSnode> childNodes = oe.getChildren();

            if (childNodes.isEmpty()) {
                x = (int) g.getCircle().x + GSentailmentGraphics.CIRCLE_WIDTH / 2 - 50;
                y = (int) g.getCircle().y;
            } else if (childNodes.size() == 1) {
                x = (int) g.getSecondCircle().x + GSentailmentGraphics.CIRCLE_WIDTH / 2 - 50;
                y = (int) g.getSecondCircle().y;
            }

            y += g.getLength();

            Goal goal = factory.createGoal(x, y, GOAL_START_WIDTH, GOAL_START_HEIGHT);
            oe.addChild(goal);
            view.addDrawable(goal.getGraphicalProperties());
            model.addToGSnodes(goal);
        }

    }

    /**
     * Adds an Operationalizing Products to a given leaf goal.
     */
    @Override
    public void addOperationalizingProducts() {

        try {
            Goal goal = (Goal) currentSelection;
            GSnodeGraphics g = goal.getGraphicalProperties();
            int x = g.getX() + g.getWidth() / 2 - PRODUCTS_START_WIDTH / 2;
            int y = g.getY() + g.getHeight() + 60;
            OperationalizingProducts ops = factory.createOperationalizingProducts(x, y, PRODUCTS_START_WIDTH, PRODUCTS_START_HEIGHT);
            currentSelection.addChild(ops);
            view.addDrawable(ops.getGraphicalProperties());
            model.addToGSnodes(ops);
        } catch (UnsupportedOperationException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    /**
     * Adds an Product to a given Operationalizing Products.
     *
     * @param product the product to add.
     */
    @Override
    public void addProduct(String product) {
        OperationalizingProducts ops = (OperationalizingProducts) currentSelection;
        ops.addProduct(product);
        model.notifyView();
    }

    /**
     * Adds an assumption termination to a given leaf goal.
     */
    @Override
    public void addAssumptionTermination() {

        try {
            Goal goal = (Goal) currentSelection;
            GSnodeGraphics g = goal.getGraphicalProperties();
            int x = g.getX() + g.getWidth() / 2 - GSnodeGraphics.TERMINATION_WIDTH / 2;
            int y = g.getY() + g.getHeight() + 35;
            AssumptionTermination ats = factory.createAssumptionTermination(x, y);
            currentSelection.addChild(ats);
            view.addDrawable(ats.getGraphicalProperties());
            model.addToGSnodes(ats);
        } catch (UnsupportedOperationException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    /**
     * Removes a goal.
     */
    @Override
    public void deleteGoal() {
        Goal goal = (Goal) currentSelection;
        if(goal.hasParent) {
            GSnode entailment = goal.getParent();
            String entailmnetType = entailment.getClass().toString();
            if (entailmnetType.contains("ANDentailment")) {
                ANDentailment ae = (ANDentailment) entailment;
                ae.removeChild(goal);                
            } else if (entailmnetType.contains("ORentailment")) {
                ORentailment oe = (ORentailment) entailment;
                oe.removeChild(goal);
            }
        }
        deleteGoal(goal);
    }

    /**
     * Removes a goal.
     *
     * @param goal the goal to remove.
     */
    @Override
    public void deleteGoal(Goal goal) {       
        

        if (goal.hasGop()) {
            GoalOrientedProposition gop = goal.getProposition();
            goal.removeChild(gop);
            model.removeFromGSnodes(gop);
        }

        if (goal.isOperationalized()) {
            OperationalizingProducts ops = goal.getOperationalizingProducts();
            goal.removeChild(ops);
            view.removeDrawable(ops.getGraphicalProperties());
            model.removeFromGSnodes(ops);
        }

        if (goal.isTerminated()) {
            AssumptionTermination ats = goal.getAssumptionTermination();
            goal.removeChild(ats);
            view.removeDrawable(ats.getGraphicalProperties());
            model.removeFromGSnodes(ats);
        }

        if (goal.isEntailed()) {
            GSnode entailment = goal.getEntailment();
            String entailmnetType = entailment.getClass().toString();
            if (entailmnetType.contains("ANDentailment")) {
                ANDentailment ae = (ANDentailment) entailment;
                if (ae.hasChildren) {
                    ArrayList<GSnode> children = ae.getChildren();
                    for (GSnode n : children) {
                        String nodeType = n.getClass().toString();
                        if (nodeType.contains("Goal")) {
                            Goal g = (Goal) n;
                            deleteGoal(g);
                        }
                    }

                }

                goal.removeChild(ae);
                view.removeDrawable(ae.getGraphicalProperties());
                model.removeFromGSnodes(ae);

            } else if (entailmnetType.contains("ORentailment")) {
                ORentailment oe = (ORentailment) entailment;
                if (oe.hasChildren) {
                    ArrayList<GSnode> children = oe.getChildren();
                    for (GSnode n : children) {
                        String nodeType = n.getClass().toString();
                        if (nodeType.contains("Goal")) {
                            Goal g = (Goal) n;
                            deleteGoal(g);
                        }
                    }
                }

                goal.removeChild(oe);
                view.removeDrawable(oe.getGraphicalProperties());
                model.removeFromGSnodes(oe);

            }
        }
        
        /*if(goal.hasParent) {
            GSnode entailment = goal.getParent();
            String entailmnetType = entailment.getClass().toString();
            if (entailmnetType.contains("ANDentailment")) {
                ANDentailment ae = (ANDentailment) entailment;
                ae.removeChild(goal);                
            } else if (entailmnetType.contains("ORentailment")) {
                ORentailment oe = (ORentailment) entailment;
                oe.removeChild(goal);
            }
                
            
        }*/

        view.removeDrawable(goal.getGraphicalProperties());
        model.removeFromGSnodes(goal);
        currentSelection = null;

    }

    /**
     * Deletes a given goal's ID.
     *
     * @param goal the goal to deleter the ID from.
     */
    @Override
    public void deleteGoalID(Goal goal) {

    }

    /**
     * Adds a GOP to a given goal.
     *
     * @param prefix the goal type of the GOP.
     * @param statement the statement of the GOP.
     */
    @Override
    public void addGOP(GoalType prefix, String statement) {
        Goal goal = (Goal) currentSelection;
        GoalOrientedProposition gop = factory.createGOP(prefix, statement);

        try {
            goal.addChild(gop);
            model.addToGSnodes(gop);
        } catch (UnsupportedOperationException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    /**
     * Deletes a GOP from a given goal.
     */
    @Override
    public void deleteGoalOrientedProposition() {
        Goal goal = (Goal) currentSelection;
        GoalOrientedProposition gop = goal.getProposition();
        goal.removeChild(gop);
        goal.setRefinedFromAssumption(false);
        if (goal.isEntailed()) {
            String entailmentType = goal.getEntailment().getClass().toString();
            if (entailmentType.contains("ANDentailment")) {
                ANDentailment ae = (ANDentailment) goal.getEntailment();
                ae.setEntailsAssumption(false);
            } else if (entailmentType.contains("ORentailment")) {
                ORentailment oe = (ORentailment) goal.getEntailment();
                oe.setEntailsAssumption(false);
            }
        }
        model.removeFromGSnodes(gop);

    }

    /**
     * Deletes operationalizing products from a given goal.
     */
    @Override
    public void deleteOperationalizingProducts() {

        Goal goal = (Goal) currentSelection.getParent();
        OperationalizingProducts ops = (OperationalizingProducts) currentSelection;
        goal.removeChild(ops);
        view.removeDrawable(ops.getGraphicalProperties());
        model.removeFromGSnodes(ops);
        currentSelection = null;

    }

    /**
     * Deletes an assumption termination products from a given goal.
     */
    @Override
    public void deleteAssumptionTermination() {

        Goal goal = (Goal) currentSelection.getParent();
        AssumptionTermination ats = (AssumptionTermination) currentSelection;
        goal.removeChild(ats);
        view.removeDrawable(ats.getGraphicalProperties());
        model.removeFromGSnodes(ats);
        currentSelection = null;

    }

    /**
     * Adds annotation to the GOP of a goal.
     */
    @Override
    public void addAnnotation() {
        
        try {
            Goal goal = (Goal) currentSelection;
            GSnodeGraphics g = goal.getGraphicalProperties();
            GoalOrientedProposition gop = goal.getProposition();
            int x = g.getX() - g.getWidth();
            int y = g.getY() - g.getHeight();
            Annotation a = factory.createAnnotation(x, y, GSnodeGraphics.ANNOTATION_START_WIDTH, GSnodeGraphics.ANNOTATION_START_HEIGHT);
            gop.addChild(a);
            view.addDrawable(a.getGraphicalProperties());
            model.addToGSnodes(a);
        } catch (UnsupportedOperationException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    /**
     * Denotes whether or not the goal to add is the root node.
     *
     * @param rootNode true if the node to add is the root.
     */
    @Override
    public void setIsRootNode(boolean rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * Denotes whether or not the goal to add is the root node. *
     *
     * @return true if the goal to add is the root node, false otherwise.
     */
    @Override
    public boolean getIsRootNode() {
        return rootNode;
    }

    /**
     * Returns a boolean to denote whether or not there are any goal sketching
     * nodes in the model.
     *
     * @return true if the model is empty, false otherwise.
     */
    @Override
    public boolean modelIsEmpty() {
        return model.getGSnodes().isEmpty();
    }

    /**
     * Enables or disables the menu items relevant to the current node of
     * interest.
     *
     * @param e the mouse event.
     */
    @Override
    public void configureContextualMenuItems(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        if (modelIsEmpty()) {
            setIsRootNode(true);
            rootStartX = x;
            rootStartY = y;
        }

        if (getIsRootNode()) {
            view.showRootPopUpMenu(e, x, y);
            rootStartX = x;
            rootStartY = y;
        }

        if (currentSelection != null) {

            String currentSelectionType = currentSelection.getClass().toString();

            if (currentSelectionType.contains("Goal")) {
                Goal g = (Goal) currentSelection;

                if (!g.hasGop()) {
                    view.enableAddGOPMenuItem();
                    view.disableDeleteGOPMenuItem();
                    view.disableAddAnnotationMenuItem();
                } else {
                    //GoalOrientedProposition gop = g.getProposition();
                    view.disableAddGOPMenuItem();
                    view.enableDeleteGOPMenuItem();
                    view.enableAddAnnotationMenuItem();
                }

                if (g.isRootGoal() && !g.isEntailed()) {
                    view.enableAddANDEntailmentMenuItem();
                    view.enableAddOREntailmentMenuItem();
                    view.disableAddProductsMenuItem();
                    view.disableAddAssumpTerminationMenuItem();
                    view.enableDeleteGoalMenuItem();
                } else if (g.isRootGoal() && g.isEntailed()) {
                    view.disableAddANDEntailmentMenuItem();
                    view.disableAddOREntailmentMenuItem();
                    view.disableAddProductsMenuItem();
                    view.disableAddAssumpTerminationMenuItem();
                    view.enableDeleteGoalMenuItem();
                } else if (!g.isEntailed() && !g.isOperationalized() && !g.isTerminated()) {
                    view.enableAddANDEntailmentMenuItem();
                    view.enableAddOREntailmentMenuItem();
                    view.enableAddProductsMenuItem();
                    view.enableAddAssumpTerminationMenuItem();
                    view.enableDeleteGoalMenuItem();
                } else if (g.isEntailed()) {
                    view.disableAddProductsMenuItem();
                    view.disableAddANDEntailmentMenuItem();
                    view.disableAddOREntailmentMenuItem();
                    view.disableAddAssumpTerminationMenuItem();
                    view.enableDeleteGoalMenuItem();
                } else if (g.isOperationalized()) {
                    view.disableAddProductsMenuItem();
                    view.disableAddANDEntailmentMenuItem();
                    view.disableAddOREntailmentMenuItem();
                    view.enableDeleteGoalMenuItem();
                } else if (g.isTerminated()) {
                    view.disableAddAssumpTerminationMenuItem();
                    view.disableAddProductsMenuItem();
                    view.disableAddANDEntailmentMenuItem();
                    view.disableAddOREntailmentMenuItem();
                    view.enableDeleteGoalMenuItem();
                }

                view.showGoalPopUpMenu(e, x, y);

            } else if (currentSelectionType.contains("ANDentailment")) {
                //ANDentailment ae = (ANDentailment) currentSelection;

                view.enableAddGoalMenuItem();
                view.enableDeleteEntailmentMenuItem();
                view.showEntailmentPopUpMenu(e, x, y);

            } else if (currentSelectionType.contains("ORentailment")) {
                ORentailment oe = (ORentailment) currentSelection;

                if (oe.getChildren().size() >= 2) {
                    view.disableAddGoalMenuItem();
                } else {
                    view.enableAddGoalMenuItem();
                }
                view.enableDeleteEntailmentMenuItem();
                view.showEntailmentPopUpMenu(e, x, y);

            } else if (currentSelectionType.contains("OperationalizingProducts")) {
                OperationalizingProducts ops = (OperationalizingProducts) currentSelection;

                view.enableDeleteProductsMenuItem();
                view.enableAddProductMenuItem();
                if (ops.getProducts().size() > 0) {
                    view.enableRemoveProductMenuItem();
                } else {
                    view.disableRemoveProductMenuItem();
                }
                view.showProductsPopUpMenu(e, x, y);

            } else if (currentSelectionType.contains("AssumptionTermination")) {
                AssumptionTermination at = (AssumptionTermination) currentSelection;

                view.enableDeleteAnnotationMenuItem();
                view.showAssumpTerminationPopUpMenu(e, x, y);

            } else if (currentSelectionType.contains("Annotation")) {
                Annotation a = (Annotation) currentSelection;

                view.enableDeleteAnnotationMenuItem();
                view.showAnnotationPopUpMenu(e, x, y);
            }
        }

    }

    /**
     * Defines what happens when the mouse buttons are released.
     */
    @Override
    public void configureMouseReleased(MouseEvent e) {

        if (currentSelection != null) {

            GSnode gsn = currentSelection;
            String currentSelectionType = gsn.getClass().toString();

            if (currentSelectionType.contains("Goal")
                    || currentSelectionType.contains("OperationalizingProducts")
                    || currentSelectionType.contains("Annotation")) {
                GSnodeGraphics g = (GSnodeGraphics) gsn.getGraphicalProperties();

                int width = g.getWidth();
                int height = g.getHeight();

                if (width <= 0) {
                    g.setWidth(width + 100);
                    g.setHeight(height);
                    model.notifyView();
                }

                if (height <= 0) {
                    g.setHeight(height + 100);
                    g.setWidth(width);
                    model.notifyView();
                }

                if (width <= 0 && height <= 0) {
                    g.setWidth(width + 100);
                    g.setHeight(height + 100);
                }
            } else if (currentSelectionType.contains("ANDentailment")) {

                GSentailmentGraphics g = (GSentailmentGraphics) gsn.getGraphicalProperties();
                int length = g.getLength();

                if (length <= 0) {
                    g.setLength(length + ENTAILMENT_START_LENGTH);
                    model.notifyView();
                }

            } else if (currentSelectionType.contains("ORentailment")) {

                GSorEntailmentGraphics g = (GSorEntailmentGraphics) gsn.getGraphicalProperties();
                int length = g.getLength();
                int length2 = g.getLength2();

                if (length <= 0) {
                    g.setLength(length + ENTAILMENT_START_LENGTH);
                    model.notifyView();
                }

                if (length2 <= 0) {
                    g.setLength2(length2 + ENTAILMENT_START_LENGTH);
                    model.notifyView();
                }

            }

            if (e.getButton() == 1) {
                mouseListener.setMousePressed(false);
                if (getCurrentSelection() != null) {
                    GSgraphics g = gsn.getGraphicalProperties();
                    g.setSelected(false);
                }

            }

            view.hidePopUpMenu();
        }
    }

    /**
     * Defines what happens when the mouse is dragged.
     */
    @Override
    public void configureMouseDragged(MouseEvent e) {

        int eventX = e.getX();
        int eventY = e.getY();

        if (mouseListener.isDragging()) {

            mouseListener.setMousePressed(false);
            if (currentSelection != null) {

                Point p = e.getPoint();
                GSnode gsn = currentSelection;
                String currentSelectionType = gsn.getClass().toString();
                if (currentSelectionType.contains("Goal")
                        || currentSelectionType.contains("OperationalizingProducts")
                        || currentSelectionType.contains("Annotation")) {
                    GSnodeGraphics g = (GSnodeGraphics) gsn.getGraphicalProperties();

                    GoalSketchingPanel panel = view.getPanel();
                    int type = panel.getCursor().getType();
                    int dx = p.x - g.getX();
                    int dy = p.y - g.getY();
                    switch (type) {
                        case Cursor.N_RESIZE_CURSOR:
                            int height = g.getHeight() - (int) dy;
                            g.setY(g.getY() + dy);
                            g.setHeight(height);
                            break;
                        case Cursor.NW_RESIZE_CURSOR:
                            int width = g.getWidth() - dx;
                            height = g.getHeight() - dy;
                            g.setX(g.getX() + dx);
                            g.setY(g.getY() + dy);
                            g.setWidth(width);
                            g.setHeight(height);
                            //r.setRect(r.x + dx, r.y + dy, width, height);
                            break;
                        case Cursor.W_RESIZE_CURSOR:
                            width = g.getWidth() - dx;
                            g.setX(g.getX() + dx);
                            g.setWidth(width);
                            //r.setRect(r.x + dx, r.y, width, r.height);
                            break;
                        case Cursor.SW_RESIZE_CURSOR:
                            width = g.getWidth() - dx;
                            height = (int) dy;
                            g.setX(g.getX() + dx);
                            g.setWidth(width);
                            g.setHeight(height);
                            break;
                        case Cursor.S_RESIZE_CURSOR:
                            height = (int) dy;
                            g.setHeight(height);
                            //r.setRect(r.x, r.y, r.width, height);
                            break;
                        case Cursor.SE_RESIZE_CURSOR:
                            width = dx;
                            height = dy;
                            g.setWidth(width);
                            g.setHeight(height);
                            break;
                        case Cursor.E_RESIZE_CURSOR:
                            width = (int) dx;
                            g.setWidth(width);
                            break;
                        case Cursor.NE_RESIZE_CURSOR:
                            width = (int) dx;
                            height = g.getHeight() - dy;
                            g.setY(g.getY() + dy);
                            g.setWidth(width);
                            g.setHeight(height);
                            break;
                        default:
                        //System.out.println("unexpected type: " + type);
                    }
                    model.notifyView();
                }
            }
        }

        if (mouseListener.isMousePressed()) {

            if (currentSelection != null) {

                GSgraphics g = currentSelection.getGraphicalProperties();
                g.setLocation(eventX, eventY);
                model.notifyView();

                if (currentSelection.getClass().toString().contains("ANDentailment")) {
                    GSentailmentGraphics g2 = (GSentailmentGraphics) currentSelection.getGraphicalProperties();
                    g2.setCircleLocation(eventX, eventY);
                    model.notifyView();
                }

                if (currentSelection.getClass().toString().contains("ORentailment")) {
                    GSorEntailmentGraphics g2 = (GSorEntailmentGraphics) currentSelection.getGraphicalProperties();

                    g2.setCircleLocation(eventX, eventY);
                    model.notifyView();
                }
            }
        }
    }

    /**
     * Defines what happens when the mouse is moved.
     *
     * @param e the mouse event.
     */
    @Override
    public void configureMouseMoved(MouseEvent e) {

        if (currentSelection != null) {
            Point p = e.getPoint();
            GoalSketchingPanel panel = view.getPanel();
            if (!isOverRect(p)) {
                if (panel.getCursor() != Cursor.getDefaultCursor()) {
                    // If cursor is not over rect reset it to the default.
                    panel.setCursor(Cursor.getDefaultCursor());
                }
                return;
            }
            // Locate cursor relative to center of rect.
            int outcode = getOutcode(p);
            GSnode gsn = currentSelection;
            String currentSelectionType = gsn.getClass().toString();
            if (currentSelectionType.contains("Goal")
                    || currentSelectionType.contains("OperationalizingProducts")
                    || currentSelectionType.contains("Annotation")) {
                GSnodeGraphics g = (GSnodeGraphics) gsn.getGraphicalProperties();

                double y = g.getY();
                double x = g.getX();
                int height = g.getHeight();
                int width = g.getWidth();

                switch (outcode) {
                    case Rectangle.OUT_TOP:
                        if (Math.abs(p.y - y) < mouseListener.PROX_DIST) {
                            panel.setCursor(Cursor.getPredefinedCursor(
                                    Cursor.N_RESIZE_CURSOR));
                        }
                        break;
                    case Rectangle.OUT_TOP + Rectangle.OUT_LEFT:
                        if (Math.abs(p.y - y) < mouseListener.PROX_DIST
                                && Math.abs(p.x - x) < mouseListener.PROX_DIST) {
                            panel.setCursor(Cursor.getPredefinedCursor(
                                    Cursor.NW_RESIZE_CURSOR));
                        }
                        break;
                    case Rectangle.OUT_LEFT:
                        if (Math.abs(p.x - x) < mouseListener.PROX_DIST) {
                            panel.setCursor(Cursor.getPredefinedCursor(
                                    Cursor.W_RESIZE_CURSOR));
                        }
                        break;
                    case Rectangle.OUT_LEFT + Rectangle.OUT_BOTTOM:
                        if (Math.abs(p.x - x) < mouseListener.PROX_DIST
                                && Math.abs(p.y - (y + height)) < mouseListener.PROX_DIST) {
                            panel.setCursor(Cursor.getPredefinedCursor(
                                    Cursor.SW_RESIZE_CURSOR));
                        }
                        break;
                    case Rectangle.OUT_BOTTOM:
                        if (Math.abs(p.y - (y + height)) < mouseListener.PROX_DIST) {
                            panel.setCursor(Cursor.getPredefinedCursor(
                                    Cursor.S_RESIZE_CURSOR));
                        }
                        break;
                    case Rectangle.OUT_BOTTOM + Rectangle.OUT_RIGHT:
                        if (Math.abs(p.x - (x + width)) < mouseListener.PROX_DIST
                                && Math.abs(p.y - (y + height)) < mouseListener.PROX_DIST) {
                            panel.setCursor(Cursor.getPredefinedCursor(
                                    Cursor.SE_RESIZE_CURSOR));
                        }
                        break;
                    case Rectangle.OUT_RIGHT:
                        if (Math.abs(p.x - (x + width)) < mouseListener.PROX_DIST) {
                            panel.setCursor(Cursor.getPredefinedCursor(
                                    Cursor.E_RESIZE_CURSOR));
                        }
                        break;
                    case Rectangle.OUT_RIGHT + Rectangle.OUT_TOP:
                        if (Math.abs(p.x - (x + width)) < mouseListener.PROX_DIST
                                && Math.abs(p.y - y) < mouseListener.PROX_DIST) {
                            panel.setCursor(Cursor.getPredefinedCursor(
                                    Cursor.NE_RESIZE_CURSOR));
                        }
                        break;
                    default:    // centre
                        panel.setCursor(Cursor.getDefaultCursor());
                }

            }
        }
    }

    /**
     * Make a larger Rectangle and check to see if the cursor is over it.
     */
    @Override
    public boolean isOverRect(Point p) {

        String currentSelectionType = currentSelection.getClass().toString();
        Rectangle r = new Rectangle();
        if (currentSelectionType.contains("Goal")
                || currentSelectionType.contains("OperationalizingProducts")
                || currentSelectionType.contains("Annotation")) {

            GSnodeGraphics g = (GSnodeGraphics) currentSelection.getGraphicalProperties();
            r = new Rectangle((int) g.getX(), (int) g.getY(), g.getWidth(), g.getHeight());
        }

        r.grow(mouseListener.PROX_DIST, mouseListener.PROX_DIST);
        return r.contains(p);
    }

    /**
     * Make a smaller Rectangle and use it to locate the cursor relative to the
     * Rectangle centre.
     */
    @Override
    public int getOutcode(Point p) {

        String currentSelectionType = currentSelection.getClass().toString();
        GSnodeGraphics g = null;

        if (currentSelectionType.contains("Goal")
                || currentSelectionType.contains("OperationalizingProducts")
                || currentSelectionType.contains("Annotation")) {

            g = (GSnodeGraphics) currentSelection.getGraphicalProperties();

        }

        Rectangle r = new Rectangle((int) g.getX(), (int) g.getY(), g.getWidth(), g.getHeight());
        r.grow(-mouseListener.PROX_DIST, -mouseListener.PROX_DIST);
        return r.outcode(p.x, p.y);
    }

    /**
     * Returns the starting x coordinate of the root goal.
     *
     * @return the starting x coordinate.
     */
    @Override
    public int getRootStartX() {
        return this.rootStartX;
    }

    /**
     * Returns the starting y coordinate of the root goal.
     *
     * @return the starting y coordinate.
     */
    @Override
    public int getRootStartY() {
        return this.rootStartY;
    }

    /**
     * Sets the ID of the currently selected goal.
     */
    @Override
    public void editGoalID() {

        String currentSelectionType = currentSelection.getClass().toString();
        if (currentSelectionType.contains("Goal")) {

            JDialog dialog = view.getEditGoalIDdialog();
            dialog.setVisible(true);

        }
    }

    /**
     * Sets the ID of the currently selected goal.
     *
     * @param ID the ID to set.
     */
    @Override
    public void setGoalID(String ID) {
        Goal goal = (Goal) currentSelection;
        ArrayList<GSnode> nodes = model.getGSnodes();
        for (GSnode n : nodes) {
            String nodeType = n.getClass().toString();
            if (nodeType.contains("Goal")) {
                Goal g = (Goal) n;
                if (g.getId() != null && g.getId().equalsIgnoreCase(ID)) {
                    throw new UnsupportedOperationException("Goal with ID: \"" + ID + "\" already exists, goal IDs must be distinct");
                }
            }
        }

        goal.setID(ID);
        model.notifyView();
    }

    /**
     * Handles events for setting the GOP of the currently selected goal.
     */
    @Override
    public void editGOP() {

        String currentSelectionType = currentSelection.getClass().toString();
        if (currentSelectionType.contains("Goal")) {

            JDialog dialog = view.getEditGoalGOPdialog();
            dialog.setVisible(true);

        }

    }

    /**
     * Sets the goal type and statement for the GOP in question.
     *
     * @param prefix the goal type of the GOP.
     * @param statement th statement of the GOP.
     */
    @Override
    public void setGOP(String prefix, String statement) {

        GoalType gt = GoalType.MOTIVATION;

        if (prefix.equalsIgnoreCase("motivation")) {
            gt = GoalType.MOTIVATION;
        } else if (prefix.equalsIgnoreCase("behaviour")) {
            gt = GoalType.BEHAVIOUR;
        } else if (prefix.equalsIgnoreCase("constraint")) {
            gt = GoalType.CONSTRAINT;
        } else if (prefix.equalsIgnoreCase("assumption")) {
            gt = GoalType.ASSUMPTION;
        } else if (prefix.equalsIgnoreCase("obstacle")) {
            gt = GoalType.OBSTACLE;
        }

        addGOP(gt, statement);
    }

    /**
     * Handles events for editing the products of the currently selected
     * operationalizing products.
     */
    @Override
    public void editProducts() {

        String currentSelectionType = currentSelection.getClass().toString();
        if (currentSelectionType.contains("OperationalizingProducts")) {

            JDialog dialog = view.getAddProductsDialog();
            dialog.setVisible(true);

        }
    }
    
    /**
     * Handles events for editing the goal judgement from an annotation.
     */
    @Override
    public void editGoalJudgement() {
        
        String currentSelectionType = currentSelection.getClass().toString();
        if (currentSelectionType.contains("Annotation")) {

            JDialog dialog = view.getAddGoalJudgementDialog();
            dialog.setVisible(true);

        }
        
    }

}
