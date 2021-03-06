/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.xml.sax.SAXException;

/**
 * Describes the strategy for the goal sketching view.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GoalSketchingController implements GoalSketchingControllerInterface {

    /**
     * A reference to the model passed to the constructor of this goal sketching
     * controller.
     */
    private final GoalGraphModelInterface model;

    /**
     * A reference to the view of this goal sketching controller.
     */
    private final GoalSketchingView view;

    /**
     * A reference to the mouse listener nested class from the view of this goal
     * sketching controller.
     */
    private final GoalSketchingView.GSmouseListener mouseListener;

    /**
     * The graph parser for this goal sketching controller.
     */
    private final GraphParser parser;

    /**
     * The goal sketching node factory used for the instantiation of goal
     * sketching nodes.
     */
    private final GSnodeFactory factory;

    /**
     * The currently selected goal sketching node.
     */
    private GSnode currentSelection;

    /**
     * Denotes if the goal sketching panel is empty or not.
     */
    private boolean rootNode;

    /**
     * The start x position of the root node.
     */
    private int rootStartX;

    /**
     * The start y position of the root node.*
     */
    private int rootStartY;

    /**
     * The integer value for the start y of an OR entailment graphics object.
     */
    public static final int OR_ENTAILMENT_START_Y = 125;

    /**
     * The integer value for the start y of an AND entailment graphics object.
     */
    public static final int AND_ENTAILMENT_START_Y = 80;

    /**
     * The integer value for the start length of an entailment graphics object.
     */
    public static final int ENTAILMENT_START_LENGTH = 80;

    /**
     * The integer value for the start width of a goal.
     */
    public static final int GOAL_START_WIDTH = 100;

    /**
     * The integer value for the start height of a goal.
     */
    public static final int GOAL_START_HEIGHT = 60;

    /**
     * The integer value for the start width of operationalizing products
     * graphics objects.
     */
    public static final int PRODUCTS_START_WIDTH = 80;

    /**
     * The integer value for the start height of operationalizing products
     * graphics objects.
     */
    public static final int PRODUCTS_START_HEIGHT = 40;

    /**
     * Constructs a goal sketching controller and initialises the view, graph
     * parser, mouse listener and goal sketching node factory.
     *
     * @param model the goal graph model interface implementation.
     */
    public GoalSketchingController(GoalGraphModelInterface model) {

        this.model = model;
        view = new GoalSketchingView(model, this);
        view.createGUI();
        view.createContextualMenuControls();
        view.createFileControls();
        parser = new GraphParser(model, view);
        factory = new GSnodeFactory();
        mouseListener = view.getMouseListener();

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
            }
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

                    Goal root = parser.getGoal(element);
                    model.addRootGoal(root);

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }

    }

    /**
     * Saves a goal graph.
     *
     * @param root the root goal.
     * @param fileName the file path.
     * @throws javax.xml.transform.TransformerException if an exceptional
     * condition occured during the transformation process.
     */
    @Override
    public void saveGraph(Goal root, String fileName) throws ParserConfigurationException, TransformerException {

        GraphBuilder gb = new GraphBuilder(root);
        Document doc = gb.build();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(4));
        transformer.transform(source, result);

    }

    /**
     * resets the model and view.
     */
    @Override
    public void reset() {
        this.currentSelection = null;
        view.reset();
        model.reset();
        parser.reset();
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
     * Adds an AND entailment to the currently selected goal.
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
     * Adds an OR entailment to the currently selected goal.
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
     * Adds a leaf goal to the currently selected entailment.
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
     * Adds an Operationalizing Products to the currently selected leaf goal.
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
     * Adds an Product to the currently selected Operationalizing Products.
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
     * Adds an assumption termination to the currently selected leaf goal.
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
     * Removes the currently selected goal from the entailment.
     */
    @Override
    public void deleteGoal() {
        Goal goal = (Goal) currentSelection;
        if (goal.hasParent) {
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
        
        if (goal.hasTwin()) {
            String message = "Goal: " + goal.getId() + "has twins, are you sure you want to delete it?";
            int option = view.displayTwinWarning(message);
            if (option == JOptionPane.OK_OPTION) {
                ArrayList<GSnode> twins = goal.getTwins();
                for (GSnode n : twins) {
                    Twin t = (Twin) n;
                    goal.removeChild(t);
                    GSnode entailment = t.getParent();
                    entailment.removeChild(t);
                    view.removeDrawable(t.getGraphicalProperties());
                    model.removeFromGSnodes(t);                    
                }
                deleteGoal(goal);
            } else {
                return;
            }
            if (option == JOptionPane.NO_OPTION) {
             
            }
            if (option == JOptionPane.CLOSED_OPTION) {
                
            }        
        }
        deleteGoal(goal);        
    }

    /**
     * Recursively removes the given goal and it's children.
     *
     * @param goal the goal to remove.
     */
    @Override
    public void deleteGoal(Goal goal) {
             

            if (goal.hasGop()) {
                GoalOrientedProposition gop = goal.getProposition();
                if (gop.isParent()) {
                    for (GSnode n : gop.getChildren()) {
                        Annotation a = (Annotation) n;
                        view.removeDrawable(a.getGraphicalProperties());
                        model.removeFromGSnodes(a);
                    }
                    gop.deleteAnnotations();
                }
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
            view.removeDrawable(goal.getGraphicalProperties());
            model.removeFromGSnodes(goal);
            currentSelection = null;        

    }

    /**
     * Deletes the currently selected goal's ID.
     *
     * @param goal the goal to deleter the ID from.
     */
    @Override
    public void deleteGoalID(Goal goal) {

    }

    /**
     * Adds a GOP to the currently selected goal.
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
     * Adds a GOP to a given goal.
     *
     * @param statement the statement of the description node.
     */
    @Override
    public void addDescriptionNode(String statement) {

        Goal goal = (Goal) currentSelection;
        GoalOrientedProposition gop = factory.createDescriptionNode(statement);
        try {
            goal.addChild(gop);
            model.addToGSnodes(gop);
        } catch (UnsupportedOperationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Deletes a GOP from the currently selected goal.
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

        if (gop.hasChildren) {
            for (GSnode n : gop.getChildren()) {
                Annotation a = (Annotation) n;
                view.removeDrawable(a.getGraphicalProperties());
                model.removeFromGSnodes(a);
            }
            gop.deleteAnnotations();
        }

        model.removeFromGSnodes(gop);

    }

    /**
     * Deletes operationalizing products from the currently selected goal.
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
     * Deletes an assumption termination products from the currently selected
     * goal.
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
     * Adds annotation to the GOP of the currently selected goal.
     */
    @Override
    public void addAnnotation() {

        try {
            Goal goal = (Goal) currentSelection;
            GSnodeGraphics g = goal.getGraphicalProperties();
            GoalOrientedProposition gop = goal.getProposition();
            int x = g.getX() - g.getWidth();
            int y = g.getY() - g.getHeight();
            ArrayList<GSnode> childNodes = gop.getChildren();

            if (childNodes != null && !childNodes.isEmpty()) {
                GSnodeGraphics gs = (GSnodeGraphics) childNodes.get(childNodes.size() - 1).getGraphicalProperties();
                x = gs.getX() + 15;
                y = gs.getY() + 15;
            }

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
        this.mouseListener.setMousePressed(false);

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

                //if (g.getGraphicalProperties().contains(x, y)) {
                view.showGoalPopUpMenu(e, x, y);
                // }

            } else if (currentSelectionType.contains("ANDentailment")) {

                ANDentailment ae = (ANDentailment) currentSelection;

                view.enableAddGoalMenuItem();
                view.enableAddTwinMenuItem();
                view.enableDeleteEntailmentMenuItem();

                if (ae.getGraphicalProperties().contains(x, y)) {
                    view.showEntailmentPopUpMenu(e, x, y);
                }

            } else if (currentSelectionType.contains("ORentailment")) {

                ORentailment oe = (ORentailment) currentSelection;

                if (oe.getChildren().size() >= 2) {
                    view.disableAddGoalMenuItem();
                    view.disableAddTwinMenuItem();
                } else {
                    view.enableAddGoalMenuItem();
                    view.enableAddTwinMenuItem();
                }
                view.enableDeleteEntailmentMenuItem();
                if (oe.getGraphicalProperties().contains(x, y)) {
                    view.showEntailmentPopUpMenu(e, x, y);
                }

            } else if (currentSelectionType.contains("OperationalizingProducts")) {

                OperationalizingProducts ops = (OperationalizingProducts) currentSelection;

                view.enableDeleteProductsMenuItem();
                view.enableAddProductMenuItem();
                if (ops.getProducts().size() > 0) {
                    view.enableRemoveProductMenuItem();
                } else {
                    view.disableRemoveProductMenuItem();
                }
                if (ops.getGraphicalProperties().contains(x, y)) {
                    view.showProductsPopUpMenu(e, x, y);
                }

            } else if (currentSelectionType.contains("AssumptionTermination")) {

                AssumptionTermination at = (AssumptionTermination) currentSelection;
                view.enableDeleteAnnotationMenuItem();

                if (at.getGraphicalProperties().contains(x, y)) {
                    view.showAssumpTerminationPopUpMenu(e, x, y);
                }

            } else if (currentSelectionType.contains("Annotation")) {

                Annotation a = (Annotation) currentSelection;
                Goal g = (Goal) a.getParent().getParent();

                if (g.isEntailed()) {
                    view.disableAddAssumptionJudgementMenuItem();
                    view.disableAddLeafJudgementMenuItem();
                    view.enableAddGoalJudgementMenuItem();
                } else {
                    view.enableAddAssumptionJudgementMenuItem();
                    view.enableAddLeafJudgementMenuItem();
                    view.disableAddGoalJudgementMenuItem();
                }

                view.enableDeleteAnnotationMenuItem();

                if (a.getGraphicalProperties().contains(x, y)) {
                    view.showAnnotationPopUpMenu(e, x, y);
                }
            } else if (currentSelectionType.contains("Twin")) {

                Twin t = (Twin) currentSelection;
                view.enableDeleteTwinMenuItem();

                if (t.getGraphicalProperties().contains(x, y)) {
                    view.showTwinPopUpMenu(e, x, y);
                }

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
                    || currentSelectionType.contains("Annotation")
                    || currentSelectionType.contains("Twin")) {
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
                        || currentSelectionType.contains("Twin")) {
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

                if (currentSelection.getClass().toString().contains("ANDentailment")) {
                    GSentailmentGraphics g2 = (GSentailmentGraphics) currentSelection.getGraphicalProperties();
                    g2.setCircleLocation(eventX, eventY);
                    model.notifyView();
                } else if (currentSelection.getClass().toString().contains("ORentailment")) {
                    GSorEntailmentGraphics g2 = (GSorEntailmentGraphics) currentSelection.getGraphicalProperties();

                    g2.setCircleLocation(eventX, eventY);
                    model.notifyView();
                } else {

                    GSgraphics g = currentSelection.getGraphicalProperties();
                    g.setLocation(eventX, eventY);
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
            //if (getOutcode(p) != 0) {
            int outcode = getOutcode(p);
            GSnode gsn = currentSelection;
            String currentSelectionType = gsn.getClass().toString();
            if (currentSelectionType.contains("Goal")
                    || currentSelectionType.contains("OperationalizingProducts")
                    || currentSelectionType.contains("Twin")) {
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
            // }
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
                || currentSelectionType.contains("Twin")) {

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
        int outcode = 0;

        if (currentSelectionType.contains("Goal")
                || currentSelectionType.contains("OperationalizingProducts")
                || currentSelectionType.contains("Twin")) {

            GSnodeGraphics g = (GSnodeGraphics) currentSelection.getGraphicalProperties();

            Rectangle r = new Rectangle((int) g.getX(), (int) g.getY(), g.getWidth(), g.getHeight());
            r.grow(-mouseListener.PROX_DIST, -mouseListener.PROX_DIST);
            outcode = r.outcode(p.x, p.y);

        }

        return outcode;
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
            if (nodeType.contains("Goal") && !nodeType.contains("OrientedProposition")) {
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

        GoalType gt = GoalType.GENERAL;

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

        if (prefix.isEmpty()) {
            addDescriptionNode(statement);
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

    /**
     * Adds an Product to a given Operationalizing Products.
     *
     * @param cfr1 the rating for the refine confidence factor rating.
     * @param scale the rating for the value significance factor rating.
     * @param cfr2 the rating for the engage confidence factor rating.
     */
    @Override
    public void addGoalJudgement(String cfr1, String cfr2, int scale) {

        Annotation a = (Annotation) currentSelection;
        GoalJudgement gj = factory.createGoalJudgement(cfr1, cfr2, scale);
        a.setJudgement(gj);
        model.notifyView();
    }

    /**
     * Removes an annotation.
     */
    @Override
    public void deleteAnnotation() {
        Annotation a = (Annotation) currentSelection;
        GoalOrientedProposition gop = (GoalOrientedProposition) a.getParent();
        gop.removeChild(a);
        view.removeDrawable(a.getGraphicalProperties());
        model.removeFromGSnodes(a);
        currentSelection = null;
    }

    /**
     * Handles events for editing the leaf judgement from an annotation.
     */
    @Override
    public void editLeafJudgement() {

        String currentSelectionType = currentSelection.getClass().toString();
        if (currentSelectionType.contains("Annotation")) {

            JDialog dialog = view.getAddLeafJudgementDialog();
            dialog.setVisible(true);

        }
    }

    /**
     * Adds a leaf judgement to the currently selected annotation.
     *
     * @param cfr the rating for the refine confidence factor rating.
     * @param scale the rating for the value significance factor rating.
     */
    @Override
    public void addLeafJudgement(String cfr, int scale) {

        Annotation a = (Annotation) currentSelection;
        LeafJudgement lj = factory.createLeafJudgement(cfr, scale);
        a.setJudgement(lj);
        model.notifyView();

    }

    /**
     * Handles events for editing the assumption judgement from an annotation.
     */
    @Override
    public void editAssumptionJudgement() {
        String currentSelectionType = currentSelection.getClass().toString();
        if (currentSelectionType.contains("Annotation")) {

            JDialog dialog = view.getAddAssumptionJudgementDialog();
            dialog.setVisible(true);

        }
    }

    /**
     * Adds an assumption judgement to the currently selected annotation.
     *
     * @param cfr the rating for the refine confidence factor rating.
     */
    @Override
    public void addAssumptionJudgement(String cfr) {

        Annotation a = (Annotation) currentSelection;
        AssumptionJudgement aj = factory.createAssumptionJudgement(cfr);
        a.setJudgement(aj);
        model.notifyView();

    }

    /**
     * Handles events for editing a twin goal of an entailment.
     */
    @Override
    public void editTwinGoal() {
        
        JDialog dialog = view.getAddTwinGoalDialog();
        dialog.setVisible(true);
        
    }

    /**
     * Returns a combo box with the IDs of the current goals if they have one.
     *
     * @return a combo box containing the IDs of the goals which have an ID.
     */
    @Override
    public JComboBox getGoalsCombobox() {

        JComboBox cb = new JComboBox();
        ArrayList<GSnode> nodes = model.getGSnodes();
        for (GSnode n : nodes) {
            String nodeType = n.getClass().toString();
            if (nodeType.contains("Goal") && !nodeType.contains("OrientedProposition")) {
                Goal g = (Goal) n;
                if (g.getId() != null) {
                    cb.addItem(g.getId());
                }
            }
        }
        return cb;
    }

    /**
     * Creates a twin goal of the goal with the given ID.
     *
     * @param goalID the ID of the goal to twin.
     */
    @Override
    public void addTwin(String goalID) {

        String currentSelectionType = currentSelection.getClass().toString();
        try {

            if (model.getGoal(goalID) != null) {

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
                    Goal goal = model.getGoal(goalID);
                    Twin t = factory.createTwin(x, y, GOAL_START_WIDTH, GOAL_START_HEIGHT, goal);
                    ae.addChild(t);
                    view.addDrawable(t.getGraphicalProperties());
                    model.addToGSnodes(t);

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

                    Goal goal = model.getGoal(goalID);
                    Twin t = factory.createTwin(x, y, GOAL_START_WIDTH, GOAL_START_HEIGHT, goal);
                    oe.addChild(t);
                    view.addDrawable(t.getGraphicalProperties());
                    model.addToGSnodes(t);

                }
            }

        } catch (UnsupportedOperationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Removes a twin goal.
     */
    @Override
    public void deleteTwin() {
        Twin t = (Twin) currentSelection;
        Goal g = t.getOriginal();
        g.removeChild(t);
        view.removeDrawable(t.getGraphicalProperties());
        model.removeFromGSnodes(t);
    }
}
