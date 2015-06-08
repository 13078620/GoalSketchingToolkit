/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

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
public class GoalSketchingToolkitTester {

    //static GoalSketchingToolkit tk = new GoalSketchingToolkit(1000, 800);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, Exception {
        // TODO code application logic here
        //graphParserTester();
        //graphBuilderTester();
        GoalSketchingModel model = new GoalSketchingModel();
        GoalSketchingController controller = new GoalSketchingController(model);
    }

    public static void graphBuilderTester() throws Exception {
        //GoalSketchingToolkit tk = new GoalSketchingToolkit(1000, 800);

        Proposition p = new Proposition("/m/", "Satisfy motivation concerns", false);
        Proposition p1 = new Proposition("/b/", "Satisfy behaviour concerns", false);
        Proposition p2 = new Proposition("/c/", "Satisfy constraint concerns", false);

        GraphNode root = new GraphNode(450, 75, 100, 60, p, true, false, "G1");

        GraphNode child1 = new GraphNode(root.getX() - (root.getWidth() / 2 + 100), root.getY() + root.getHeight() + 160, 100, 60, p1, false, true, "G2");

        Operationalizer scales = new Operationalizer("Scales");
        String subDomain1 = "SD1";
        String subDomain2 = "SD2";
        scales.addSubDomain(subDomain1);
        scales.addSubDomain(subDomain2);
        ArrayList<Operationalizer> ozs = new ArrayList<>();
        ozs.add(scales);

        OperationalizerNode ozn1 = new OperationalizerNode(child1.getX(), child1.getY() + child1.getHeight() + 80, 80, 40, ozs);

        child1.setOperationalizerNode(ozn1);

        GraphNode child2 = new GraphNode(root.getX() + (root.getWidth() / 2 + 100), root.getY() + root.getHeight() + 160, 100, 60, p2, true, true, "G3");

        GraphNode child3 = new GraphNode(child2.getX() + (child2.getWidth() / 2 + 100), child2.getY() + child2.getHeight() + 160, 100, 60, p2, false, true, "G4");
        OperationalizerNode ozn2 = new OperationalizerNode(child3.getX(), child3.getY() + child3.getHeight() + 80, 80, 40, ozs);

        child3.setOperationalizerNode(ozn1);

        GraphNode child4 = new GraphNode(child2.getX() - (child2.getWidth() / 2 + 100), child2.getY() + child2.getHeight() + 160, 100, 60, p2, false, true, "G5");
        OperationalizerNode ozn3 = new OperationalizerNode(child4.getX(), child4.getY() + child4.getHeight() + 80, 80, 40, ozs);

        /*tk.addRootGraphNode(root);
        tk.addChildGraphNode(root, child1);
        tk.addChildGraphNode(root, child2);
        tk.addOperationalizerNode(child1, ozn1);

        tk.addChildGraphNode(child2, child3);
        tk.addOperationalizerNode(child3, ozn2);

        tk.addChildGraphNode(child2, child4);
        tk.addOperationalizerNode(child4, ozn3);*/

        GraphBuilder gb = new GraphBuilder(root);
        Document doc = gb.build();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("testGoalGraph.xml"));
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(4));
        transformer.transform(source, result);
    }

    public static void graphParserTester() throws Exception {

        GraphParser parser = new GraphParser();
        Document xmlDoc = parser.getDocument("testGoalGraph.xml");
        Element theRoot = xmlDoc.getDocumentElement();
        NodeList nodes = theRoot.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodes.item(i);

                GraphNode root = parser.getNode(element);
                //tk.addToGoalSketchingNodes(root);
                //ArrayList<GraphNode> childrenOfRoot = root.getChildNodes();
                /*for (int j = 0; j < childrenOfRoot.size(); j++) {
                 GraphNode c = childrenOfRoot.get(j);
                 //System.out.println(c.getId());
                 tk.addToGoalSketchingNodes(c);
                 }*/

                drawGraphFromRoot(root);

            }
        }

    }

    public static void drawGraphFromRoot(GraphNode graphNode) throws Exception {

        // if (graphNode.getId().equals("G1")) {
        //     tk.addRootGraphNode(graphNode);
        //drawGraphFromRoot(graphNode);
        //   }
        if (graphNode.isParent()) {

            //System.out.println("parent: " + graphNode.getId());
            ArrayList<GraphNode> childGraphNodes = graphNode.getChildNodes();
            //System.out.println(childGraphNodes.size());
            //tk.addToGoalSketchingNodes(graphNode);

            for (int i = 0; i < childGraphNodes.size(); i++) {

                System.out.println(graphNode.getId());

                System.out.println("child: " + childGraphNodes.get(i).getId());

               // tk.addToGoalSketchingNodes(childGraphNodes.get(i));

                drawGraphFromRoot(childGraphNodes.get(i));

            }

            //} else if (graphNode.isOperationalized()) {
            //      OperationalizerNode on = graphNode.getOperationalizerNode();
            //      tk.addOpToGoalSketchingNodes(on);
            //  }

            /*ArrayList<GraphNode> rootChildNodes = root.getChildNodes();

             for (GraphNode child : rootChildNodes) {

             if (child.isParent()) {
             ArrayList<GraphNode> children = child.getChildNodes();
             for (GraphNode c : children) {

             }

             } else if (child.isParentAndChild()) {

             } else {

             }

             tk.addChildGraphNode(root, child);
             }

             System.out.println("ID: " + root.getId());
             System.out.println("proposition: " + root.getProposition());
             System.out.println("parent: " + root.isParent());

             ArrayList<String> cn = new ArrayList<>();

             for (int n = 0; n < root.getChildNodes().size(); n++) {
             GraphNode tn = (GraphNode) root.getChildNodes().get(n);
             String id = tn.getId();
             cn.add(id);
             }

             for (String s : cn) {
             System.out.println("children IDs: " + s);
             }

             for (int j = 0; j < rootChildNodes.size(); j++) {

             GraphNode g = (GraphNode) rootChildNodes.get(j);

             System.out.println("ID: " + g.getId());
             System.out.println("proposition: " + g.getProposition());
             System.out.println("parent: " + g.isParent());

             if (!g.isParent()) {
             //System.out.println(g.getOperationalizers().size());
             //for (int k = 0; k < g.getOperationalizers().size(); k++) {
             //      Operationalizer op = (Operationalizer) g.getOperationalizers().get(k);
             //          System.out.println("Operationalizer: " + op.getAgentName());
             }
             }*/
        }

        if (graphNode.isOperationalized()) {

            OperationalizerNode on = graphNode.getOperationalizerNode();
            ArrayList<Operationalizer> os = on.getOperationalizers();
            for (int j = 0; j < os.size(); j++) {
                System.out.println("Operationalizer: " + os.get(j).getAgentName() + os.toString());
            }

            //System.out.println(on.getWidth());
           //tk.addOpToGoalSketchingNodes(on);

        }
    }

}
