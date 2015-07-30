 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

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

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GoalSketchingController implements GoalSketchingControllerInterface {

    private GoalGraphModelInterface model;
    private GoalSketchingView view;
    private GoalSketchingView.MouseListener mouseListener;

    private GraphParser parser;
    private GSnodeFactory factory;
    private GSnode currentSelection;

    private boolean rootNode;
    private int rootStartX;
    private int rootStartY;

    public GoalSketchingController(GoalGraphModelInterface model) {

        this.model = model;
        view = new GoalSketchingView(model, this);
        mouseListener = view.new MouseListener();

        //view.createGUI();
        //view.createContextualMenuControls();
        //view.createFileControls();
        parser = new GraphParser();
        factory = new GSnodeFactory();

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
     * @param eventX the x coordinate of the event.
     * @param eventY the y coordinate of the event.
     */
    @Override
    public void setCurrentSelection(int eventX, int eventY) {

        ArrayList<Drawable> drawables = view.getDrawables();

        for (int i = drawables.size() - 1; i >= 0; i--) {

            Drawable d = drawables.get(i);

            if (d.contains(eventX, eventY)) {
                mouseListener.setMousePressed(true);
                currentSelection = d.getGSnode();
                d.setSelected(true);
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
    public void drawGraphFromRoot(Goal goal) throws Exception {

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
    public void reset() {
        //view.reset();
        //model.reset();
    }

    /**
     * Sets the root goal for the goal graph model.
     *
     * @param x the x coordinate of the root goal.
     * @param y the y coordinate of the root goal.
     */
    public void addRootGoal(int x, int y) {

        int startWidth = 100;
        int startHeight = 60;
        Goal root = factory.createGoal(x, y, startWidth, startHeight);
        root.setIsRootGoal(true);
        view.addDrawable(root.getGraphicalProperties());
        model.addRootGoal(root);
        rootNode = false;

    }

    /**
     * Adds an AND entailment to a goal.
     *
     * @param parent the goal to add the entailment to.
     * @param entailment the entailment.
     */
    public void addAndEntailment(Goal parent, ANDentailment entailment) {

    }

    /**
     * Adds an OR entailment to a goal.
     *
     * @param parent the goal to add the entailment to.
     * @param entailment the entailment.
     */
    @Override
    public void addOrEntailment(Goal parent, ORentailment entailment) {

    }

    /**
     * Adds a leaf goal to a given parent.
     *
     * @param parent the parent to add the leaf to.
     * @param leaf the leaf goal to add.
     */
    @Override
    public void addLeafGoal(Goal parent, Goal leaf) {

    }

    /**
     * Adds an Operationalizing Products to a given leaf goal.
     *
     * @param parent the leaf goal to add the Operationalizing Products to.
     * @param ops the Operationalizing Products to add.
     */
    @Override
    public void addOperationalizingProducts(Goal parent, OperationalizingProducts ops) {

    }

    /**
     * Adds an Product to a given Operationalizing Products.
     *
     * @param ops the Operationalizing Products to add the product to.
     * @param product the product to add.
     */
    @Override
    public void addProduct(OperationalizingProducts ops, String product) {

    }

    /**
     * Removes a goal.
     *
     * @param child the goal to remove.
     */
    @Override
    public void removeGoal(Goal child) {

    }

    /**
     * Removes an Operationalizing Products.
     *
     * @param ops the Operationalizing Products to remove.
     */
    @Override
    public void removeOperationalisingProducts(OperationalizingProducts ops) {

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
     * Deletes a GOP from a given goal.
     *
     * @param goal the goal to delete the GOP from.
     */
    @Override
    public void deleteGoalOrientedProposition(Goal goal) {

    }

    /**
     * Adds annotation to the GOP of a goal.
     *
     * @param goal the goal which has the GOP.
     * @param annotation the annotation to add.
     */
    @Override
    public void addAnnotation(Goal goal, Annotation annotation) {

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
     */
    @Override
    public void configureContextualMenuItems() {

        String currentSelectionType = currentSelection.getClass().toString();

        if (currentSelectionType.contains("Goal")) {
            Goal g = (Goal) currentSelection;

            if (!g.hasGop()) {
                view.enableAddGOPMenuItem();
                view.disableDeleteGOPMenuItem();
                view.disableAddAnnotationMenuItem();
                view.disableDeleteAnnotationMenuItem();
            } else {
                GoalOrientedProposition gop = g.getProposition();
                view.disableAddGOPMenuItem();
                view.enableDeleteGOPMenuItem();
                if (gop.hasChildren) {

                    view.enableAddAnnotationMenuItem();
                } else {
                    view.enableAddAnnotationMenuItem();
                    view.disableDeleteAnnotationMenuItem();
                }

            }

            if (g.isRootGoal() && !g.isEntailed()) {

                view.enableAddANDEntailmentMenuItem();
                view.enableAddOREntailmentMenuItem();
                view.disableDeleteEntailmentMenuItem();
                view.disableAddProductsMenuItem();
                view.disableAddAssumpTerminationMenuItem();
                view.disableDeleteProductsMenuItem();
                view.disableDeleteAssumpTerminationMenuItem();

            } else if (g.isRootGoal() && g.isEntailed()) {
                view.disableAddANDEntailmentMenuItem();
                view.disableAddOREntailmentMenuItem();
                view.enableDeleteEntailmentMenuItem();
                view.disableAddProductsMenuItem();
                view.disableAddAssumpTerminationMenuItem();
                view.disableDeleteProductsMenuItem();
                view.disableDeleteAssumpTerminationMenuItem();
            }
//----------------------------------------------------------------------------//
            if (!g.isEntailed() && !g.isOperationalized()) {
                view.enableAddANDEntailmentMenuItem();
                view.enableAddOREntailmentMenuItem();
                view.enableAddProductsMenuItem();
                view.enableAddAssumpTerminationMenuItem();
                view.disableDeleteEntailmentMenuItem();
                view.disableDeleteProductsMenuItem();
                view.disableDeleteAssumpTerminationMenuItem();
            } else {
                view.disableAddProductsMenuItem();
                view.disableAddANDEntailmentMenuItem();
                view.disableAddOREntailmentMenuItem();
                view.disableAddAssumpTerminationMenuItem();
            }

            if (!g.isOperationalized()) {
                view.enableAddANDEntailmentMenuItem();
                view.enableAddOREntailmentMenuItem();
                view.enableAddProductsMenuItem();
                view.enableAddAssumpTerminationMenuItem();
            } else {
                view.disableAddProductsMenuItem();
                view.disableAddANDEntailmentMenuItem();
                view.disableAddOREntailmentMenuItem();
            }

            if (!g.isTerminated()) {
                view.enableAddAssumpTerminationMenuItem();
            }

        } else if (currentSelectionType.contains("ANDentailment")) {
            ANDentailment ae = (ANDentailment) currentSelection;

        } else if (currentSelectionType.contains("ORentailment")) {
            ORentailment oe = (ORentailment) currentSelection;

        } else if (currentSelectionType.contains("OperationalizingProducts")) {
            OperationalizingProducts ops = (OperationalizingProducts) currentSelection;

        } else if (currentSelectionType.contains("AssumptionTermination")) {
            AssumptionTermination at = (AssumptionTermination) currentSelection;

        } else if (currentSelectionType.contains("Annotation")) {
            Annotation a = (Annotation) currentSelection;

        }

    }
}
