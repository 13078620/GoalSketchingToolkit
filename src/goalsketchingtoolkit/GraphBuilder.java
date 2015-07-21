/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.Text;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GraphBuilder {

    private DocumentBuilder builder;
    private Document doc;
    private Goal root;

    //private boolean complete;
    private int errorCount;

    /**
     * Constructs a graph builder.
     *
     * @param root the root graph node for this document.
     * @throws ParserConfigurationException .
     */
    public GraphBuilder(Goal root) throws ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        this.root = root;

    }

    /**
     * Builds a DOM document for a goal graph.
     *
     * @return a DOM document describing the goal graph.
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public Document build() throws ParserConfigurationException {

        doc = builder.newDocument();
        Element e = doc.createElement("Graph");
        e.appendChild(createNode(root));
        doc.appendChild(e);

        return doc;

    }

    /**
     * Builds a DOM element for a graph node.
     *
     * @param node the graph node.
     * @return a DOM element describing the graph node.
     */
    private Element createNode(Goal goal) throws ParserConfigurationException {

        Element e = doc.createElement("GSnode");

        if (goal.hasGraphics()) {
            GSnodeGraphics graphics = goal.getGraphicalProperties();
            e.appendChild(createAttribute("x", "" + graphics.getX()));
            e.appendChild(createAttribute("y", "" + graphics.getY()));
            e.appendChild(createAttribute("width", "" + graphics.getWidth()));
            e.appendChild(createAttribute("height", "" + graphics.getHeight()));
        }

        if (goal.isRootGoal()) {
            e.appendChild(createAttribute("root", "true"));
        }

        if (goal.getId() != null) {
            e.appendChild(createTextElement("ID", goal.getId()));
        }

        if (goal.hasGop()) {

            GoalOrientedProposition gop = goal.getProposition();

            Element pe = doc.createElement("Proposition");

            if (gop.hasPrefix()) {
                String prefix = goal.getProposition().getPrefix();
                pe.appendChild(createTextElement("Prefix", prefix));
            }

            if (gop.getPrefix() != null) {
                String statement = gop.getStatement();
                pe.appendChild(createTextElement("Statement", statement));
            }

            if (gop.getContext() != null) {
                String context = gop.getContext();
                pe.appendChild(createTextElement("Context", context));
            }

            if (gop.isParent()) {

                Element ase = doc.createElement("Annotations");

                for (GSnode n : gop.getChildren()) {

                    Annotation a = (Annotation) n;
                    Element ae = doc.createElement("Annotation");

                    if (a.hasGraphics()) {
                        GSnodeGraphics graphics = a.getGraphicalProperties();
                        ae.appendChild(createAttribute("x", "" + graphics.getX()));
                        ae.appendChild(createAttribute("y", "" + graphics.getY()));
                        ae.appendChild(createAttribute("width", "" + graphics.getWidth()));
                        ae.appendChild(createAttribute("height", "" + graphics.getHeight()));
                    }

                    Judgement j = a.getJudgement();

                    if (j.getClass().toString().contains("GoalJudgement")) {

                        GoalJudgement gj = (GoalJudgement) j;

                        Element gje = doc.createElement("GoalJudgement");

                        ConfidenceFactorRating rcfr = gj.getRefineConfidenceFactorRating();
                        ConfidenceFactorRating ecfr = gj.getEngageConfidenceFactorRating();
                        SignificanceFactorRating sfr = gj.getSignificanceFactorRating();

                        gje.appendChild(createTextElement("" + rcfr.getKey(), "" + rcfr.getValue()));
                        gje.appendChild(createTextElement("" + ecfr.getKey(), "" + ecfr.getValue()));
                        gje.appendChild(createTextElement("" + sfr.getKey(), "" + sfr.getValue()));

                        ae.appendChild(gje);
                        ase.appendChild(ae);

                    } else if (j.getClass().toString().contains("LeafJudgement")) {

                        LeafJudgement lj = (LeafJudgement) j;

                        Element lje = doc.createElement("LeafJudgement");

                        ConfidenceFactorRating acfr = lj.getConfidenceFactorRating();
                        SignificanceFactorRating sfr = lj.getSignificanceFactorRating();

                        lje.appendChild(createTextElement("" + acfr.getKey(), "" + acfr.getValue()));
                        lje.appendChild(createTextElement("" + sfr.getKey(), "" + sfr.getValue()));

                        ae.appendChild(lje);
                        ase.appendChild(ae);

                    } else if (j.getClass().toString().contains("AssumptionJudgement")) {

                        LeafJudgement aj = (LeafJudgement) j;

                        Element aje = doc.createElement("AssumptionJudgement");

                        ConfidenceFactorRating ascfr = aj.getConfidenceFactorRating();

                        aje.appendChild(createTextElement("" + ascfr.getKey(), "" + ascfr.getValue()));

                        ae.appendChild(aje);
                        ase.appendChild(ae);
                    }

                }

            }

            e.appendChild(pe);
        }

        if (goal.isEntailed()) {
            
            Element ee = doc.createElement("Entailment");

            GSnode ent = goal.getEntailment();

            if (ent.getClass().toString().contains("ANDentailment")) {
                
                ANDentailment aent = (ANDentailment) ent;
                
                if (aent.hasGraphics()) {
                    GSentailmentGraphics graphics = aent.getGraphicalProperties();
                    ee.appendChild(createAttribute("x", "" + graphics.getX()));
                    ee.appendChild(createAttribute("y", "" + graphics.getY()));
                    ee.appendChild(createAttribute("length", "" + graphics.getLength()));
                    ee.appendChild(createAttribute("toX", "" + graphics.getToX()));
                    ee.appendChild(createAttribute("toY", "" + graphics.getToY()));
                }
                
                if (aent.isParent()) {
                    
                    ArrayList<GSnode> goals = aent.getChildren();
                    
                    for(GSnode n: goals) {
                        Goal g = (Goal) n;
                        ee.appendChild(createNode(g));
                    }
                }

            } else if (ent.getClass().toString().contains("ORentailment")) {

                ORentailment oent = (ORentailment) ent;
                
                if (oent.hasGraphics()) {
                    GSorEntailmentGraphics graphics = oent.getGraphicalProperties();
                    ee.appendChild(createAttribute("x", "" + graphics.getX()));
                    ee.appendChild(createAttribute("y", "" + graphics.getY()));
                    ee.appendChild(createAttribute("length", "" + graphics.getLength()));
                    ee.appendChild(createAttribute("toX", "" + graphics.getToX()));
                    ee.appendChild(createAttribute("toY", "" + graphics.getToY()));
                    ee.appendChild(createAttribute("length2", "" + graphics.getLength2()));
                    ee.appendChild(createAttribute("toX2", "" + graphics.getToX2()));
                    ee.appendChild(createAttribute("toY2", "" + graphics.getToY2()));
                }  
                
                if (oent.isParent()) {
                    
                    ArrayList<GSnode> goals = oent.getChildren();
                    
                    for(GSnode n: goals) {
                        Goal g = (Goal) n;
                        ee.appendChild(createNode(g));
                    }
                }
            }
            
        }

        /*if (gn.isParent()) {
            ArrayList<GraphNode> children = gn.getChildNodes();
            Element ce = doc.createElement("children");
            e.appendChild(ce);
            for (GraphNode n : children) {
                ce.appendChild(createNode(n));
            }
        } else {
            if (gn.getOperationalizerNode() != null) {

                Element oze = doc.createElement("operationalizernode");
                OperationalizerNode opNode = gn.getOperationalizerNode();
                oze.appendChild(createTextElement("x", "" + opNode.getX()));
                oze.appendChild(createTextElement("y", "" + opNode.getY()));
                oze.appendChild(createTextElement("width", "" + opNode.getWidth()));
                oze.appendChild(createTextElement("height", "" + opNode.getHeight()));
                Element oe = doc.createElement("operationalizers");
                oze.appendChild(oe);
                ArrayList<Operationalizer> operationalizers = opNode.getOperationalizers();
                for (Operationalizer op : operationalizers) {
                    Element operationalizer = doc.createElement("operationalizer");
                    oe.appendChild(operationalizer);
                    String agentName = op.getAgentName();
                    operationalizer.appendChild(createTextElement("agentname", agentName));

                    /*ArrayList<String> subDomains = op.getSubDomains();
                     if (subDomains != null) {
                     Element sds = doc.createElement("subdomains");
                     operationalizer.appendChild(sds);

                     for (String s : subDomains) {
                     sds.appendChild(createTextElement("subdomain", s));
                     }
                     }////
                }

                e.appendChild(oze);
            } else if (gn.getAssumptionTerminationNode() != null) {
                Element aste = doc.createElement("assumptionterminationnode");
                AssumptionTerminationNode astNode = gn.getAssumptionTerminationNode();
                aste.appendChild(createTextElement("x", "" + astNode.getX()));
                aste.appendChild(createTextElement("y", "" + astNode.getY()));
                aste.appendChild(createTextElement("width", "" + astNode.getWidth()));
                aste.appendChild(createTextElement("height", "" + astNode.getHeight()));
                e.appendChild(aste);
            } else {
                //System.out.println("Not operationalized or terminated ");
                errorCount++;
            }
        } */

        return e;
    }

    /**
     * Builds a text element for an atomic value.
     *
     * @param name the name of the element.
     * @param text the text to include in the element.
     * @return a DOM text element that contains the text value.
     */
    private Element createTextElement(String name, String text) {

        Text t = doc.createTextNode(text);
        Element e = doc.createElement(name);
        e.appendChild(t);

        return e;
    }

    /**
     * Builds an attribute for an atomic value.
     *
     * @param name the name of the attribute.
     * @param text the text to include in the attribute.
     * @return a DOM attribute that contains the text value.
     */
    private Attr createAttribute(String name, String text) {

        Text t = doc.createTextNode(text);
        Attr a = doc.createAttribute(name);
        a.appendChild(t);

        return a;
    }

    public boolean isComplete() {
        return errorCount == 0;
    }

}
