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

    private static GraphParser parser = new GraphParser();

    static ArrayList<GSnode> gsNodes = new ArrayList();

    private static String file = "compositeTestXMLOutput.xml";

    //static GoalSketchingToolkit tk = new GoalSketchingToolkit(1000, 800);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, Exception {
        // TODO code application logic here
        //graphParserTester();
        //graphBuilderTester();
        //GoalSketchingModel model = new GoalSketchingModel();
        //GoalSketchingController controller = new GoalSketchingController(model);
        //ANDentailment ae = new ANDentailment();
        //Goal g2 = new Goal();
        //ae.addChild(g2);
        //System.out.println(ae.getChildren().size());
        //ae.removeChild(g2);
        //System.out.println(ae.getChildren().size());

        /*try {

            String file = "compositeTestXMLOutput.xml";
            Document xmlDoc = parser.getDocument(file);

            Element theRoot = xmlDoc.getDocumentElement();
            NodeList nodes = theRoot.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodes.item(i);

                    Goal root = parser.getNode(element);
                    //drawGraphFromRoot(root);
                    GraphBuilder gb = new GraphBuilder(root);
                    Document doc = gb.build();
                    String fileName = "compositeTestXMLOutput2.xml";

                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(fileName));
                    TransformerFactory tFactory = TransformerFactory.newInstance();
                    Transformer transformer = tFactory.newTransformer();
                    transformer.setOutputProperty("encoding", "UTF-8");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(4));
                    transformer.transform(source, result);

                }
            }
            //for (GSnode n : gsNodes) {
            //System.out.println(n.getGraphicalProperties().getX());
            //}

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }*/
        //
       GoalGraphModelInterface model = new GoalGraphModel();
       GoalSketchingController controller = new GoalSketchingController(model);

    }

    public static void drawGraphFromRoot(Goal g) throws Exception {

        if (g.hasGop()) {
            // System.out.println("ID: " + g.getId());
            GoalOrientedProposition gop = g.getProposition();
            //System.out.println("prefix: " + gop.getPrefix());
            //System.out.println("statement: " + gop.getStatement());
            ArrayList<GSnode> as = g.getProposition().getChildren();

            for (GSnode n : as) {
                Annotation an = (Annotation) n;
                if (an.getJudgement() != null && an.getJudgement().getClass().toString().contains("GoalJudgement")) {
                    GoalJudgement gj = (GoalJudgement) an.getJudgement();
                    System.out.print(gj.getEngageConfidenceFactorRating().getKey() + "  ");
                    System.out.println(gj.getEngageConfidenceFactorRating().getValue());
                }

            }
        }

        //System.out.println();
        if (g.isEntailed()) {

            ANDentailment ae = (ANDentailment) g.getEntailment();

            ArrayList<GSnode> children = ae.getChildren();

           // gsNodes.add(g);
            for (int i = 0; i < children.size(); i++) {

                // System.out.println(children.get(i).getClass().toString());
                Goal goal = (Goal) children.get(i);
                //System.out.println(goal.getId());
                drawGraphFromRoot(goal);

                //gsNodes.add(children.get(i));
            }
        }
    }

}
