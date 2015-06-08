/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GoalSketchingToolkit {
    
    private int width;
    private int height;
    
    private GoalSketchingModel model;
    private GoalSketchingView view;
    private GoalSketchingController controller;
    
    private GraphParser parser;
    
    public GoalSketchingToolkit(int width, int height) {
        
        this.width = width;
        this.height = height;
        model = new GoalSketchingModel();
  ///      view = new GoalSketchingView(width, height, model);
        controller = new GoalSketchingController(model);
        
        parser = new GraphParser();
        
    }
    
    public void addRootGraphNode(GraphNode graphNode) {
        
        model.setRootGraphNode(graphNode);
        view.addDrawable(new GraphNodeDrawer(graphNode));
        
    }
    
    public void addChildGraphNode(GraphNode parent, GraphNode child) {
        
        model.addChildGraphNode(parent, child);
        view.addDrawable(new GraphNodeDrawer(child));
        
    }
    
    public void addOperationalizerNode(GraphNode parent, OperationalizerNode operationalizerNode) {
        
        model.addOperationalizerNode(parent, operationalizerNode);
        view.addDrawable(new OperationalizerNodeDrawer(operationalizerNode));
        
    }
    
    public void setChildGraphNodes(GraphNode parent, ArrayList children) {
       
    }
    
    public void addToGoalSketchingNodes(GraphNode gn) {
        model.addToGoalSketchingNodes(gn);
        view.addDrawable(new GraphNodeDrawer(gn));
    }
    
    public void addOpToGoalSketchingNodes(OperationalizerNode osn) {
        model.addOpToGoalSketchingNodes(osn);
        view.addDrawable(new OperationalizerNodeDrawer(osn));
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
            ArrayList<Operationalizer> os = on.getOperationalizers();
            for (int j = 0; j < os.size(); j++) {
                System.out.println("Operationalizer: " + os.get(j).getAgentName() + os.toString());
            }

            addOpToGoalSketchingNodes(on);

        }
    }
    
}
