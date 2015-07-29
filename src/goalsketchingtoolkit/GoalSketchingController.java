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
public class GoalSketchingController {

    private GoalGraphModel model;
    private GoalSketchingView view;

    private GraphParser parser;

   /* public GoalSketchingController(GoalSketchingModel model) {

        this.model = model;
        view = new GoalSketchingView(model, this);
        view.createGUI();
        view.createContextualMenuControls();
        view.createFileControls();

        parser = new GraphParser();

    } */
    
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
     * @param root the root goal to set.
     */
    void addRootGoal(Goal root){
        
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
    public void addOrEntailment(Goal parent, ORentailment entailment) {

    }

    /**
     * Adds a leaf goal to a given parent.
     *
     * @param parent the parent to add the leaf to.
     * @param leaf the leaf goal to add.
     */
    public void addLeafGoal(Goal parent, Goal leaf) {

    }

    /**
     * Adds an Operationalizing Products to a given leaf goal.
     *
     * @param parent the leaf goal to add the Operationalizing Products to.
     * @param ops the Operationalizing Products to add.
     */
    public void addOperationalizingProducts(Goal parent, OperationalizingProducts ops) {

    }

    /**
     * Adds an Product to a given Operationalizing Products.
     *
     * @param ops the Operationalizing Products to add the product to.
     * @param product the product to add.
     */
    public void addProduct(OperationalizingProducts ops, String product) {

    }

    /**
     * Removes a goal.
     *
     * @param child the goal to remove.
     */
    public void removeGoal(Goal child) {

    }

    /**
     * Removes an Operationalizing Products.
     *
     * @param ops the Operationalizing Products to remove.
     */
    public void removeOperationalisingProducts(OperationalizingProducts ops) {

    }

    /**
     * Deletes a given goal's ID.
     *
     * @param goal the goal to deleter the ID from.
     */
    public void deleteGoalID(Goal goal) {

    }

    /**
     * Deletes a GOP from a given goal.
     *
     * @param goal the goal to delete the GOP from.
     */
    public void deleteGoalOrientedProposition(Goal goal) {

    }

    /**
     * Adds annotation to the GOP of a goal.
     *
     * @param goal the goal which has the GOP.
     * @param annotation the annotation to add.
     */
    public void addAnnotation(Goal goal, Annotation annotation) {

    }

}
