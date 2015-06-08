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

    private GoalSketchingModel model;
    private GoalSketchingView view;

    private GraphParser parser;

    public GoalSketchingController(GoalSketchingModel model) {

        this.model = model;
        view = new GoalSketchingView(model, this);
        view.createGUI();
        view.createContextualMenuControls();
        view.createFileControls();

        parser = new GraphParser();

    }

    public void addToGoalSketchingNodes(GraphNode gn) {
        model.addToGoalSketchingNodes(gn);
        view.addDrawable(new GraphNodeDrawer(gn));
    }

    public void addOpToGoalSketchingNodes(OperationalizerNode osn) {
        model.addOpToGoalSketchingNodes(osn);
        view.addDrawable(new OperationalizerNodeDrawer(osn));
    }

    public void addTerminationGoalSketchingNodes(AssumptionTerminationNode astn) {

        model.addTerminationGoalSketchingNodes(astn);
        view.addDrawable(new AssumptionTerminationNodeDrawer(astn));

    }

    public void loadGraph(String file) {

        try {

            Document xmlDoc = parser.getDocument(file);
            Element theRoot = xmlDoc.getDocumentElement();
            NodeList nodes = theRoot.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodes.item(i);

                    GraphNode root = parser.getNode(element);
                    model.setRootGraphNode(root);
                    drawGraphFromRoot(root);

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void drawGraphFromRoot(GraphNode graphNode) throws Exception {

        if (graphNode.isParent()) {

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

        }
    }

    public void saveGraph(GraphNode root, String fileName) throws ParserConfigurationException {
        GraphBuilder gb = new GraphBuilder(root);
        Document doc = gb.build();

        //System.out.println("file path: "+fileName);
        try {
            if (gb.isComplete()) {
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(fileName));
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(4));
                transformer.transform(source, result);
            } else {
                view.displayErrorMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reset() {
        view.reset();
        model.reset();
    }

}
