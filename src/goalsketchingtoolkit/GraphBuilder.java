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

        Element e = doc.createElement("Goal");

        if (goal.hasGraphics()) {
            GSnodeGraphics graphics = goal.getGraphicalProperties();
            e.setAttributeNode(createAttribute("height", "" + graphics.getHeight()));
            e.setAttributeNode(createAttribute("width", "" + graphics.getWidth()));
            e.setAttributeNode(createAttribute("y", "" + graphics.getY()));
            e.setAttributeNode(createAttribute("x", "" + graphics.getX()));
        }

        if (goal.isRootGoal()) {
            e.setAttributeNode(createAttribute("root", "true"));
        }

        if (goal.getId() != null) {
            e.appendChild(createTextElement("ID", goal.getId()));
        }

        if (goal.hasGop()) {

            GoalOrientedProposition gop = goal.getProposition();

            e.appendChild(createPropositionElement(gop));
        }

        if (goal.hasTwin()) {

            Element tse = doc.createElement("Twins");
            ArrayList<GSnode> twins = goal.getTwins();

            if (!twins.isEmpty()) {
                for (GSnode n : twins) {
                    Twin tg = (Twin) n;
                    tse.appendChild(createTwinGoalElement(tg));
                }
            }
            e.appendChild(tse);
        }

        if (goal.getFit() != null) {
            e.appendChild(createTextElement("Fit", goal.getFit()));
        }

        if (goal.isEntailed()) {

            Element ee = null;

            GSnode ent = goal.getEntailment();

            if (ent.getClass().toString().contains("ANDentailment")) {

                ee = doc.createElement("ANDentailment");

                ANDentailment aent = (ANDentailment) ent;

                if (aent.hasGraphics()) {
                    GSentailmentGraphics graphics = aent.getGraphicalProperties();
                    ee.setAttributeNode(createAttribute("length", "" + graphics.getLength()));
                    ee.setAttributeNode(createAttribute("toY", "" + graphics.getToY()));
                    ee.setAttributeNode(createAttribute("toX", "" + graphics.getToX()));
                    ee.setAttributeNode(createAttribute("y", "" + graphics.getY()));
                    ee.setAttributeNode(createAttribute("x", "" + graphics.getX()));
                }

                if (aent.isParent()) {

                    ArrayList<GSnode> goals = aent.getChildren();

                    for (GSnode n : goals) {
                        if (n instanceof Goal) {
                            Goal g = (Goal) n;
                            ee.appendChild(createNode(g));
                        } else {
                            Twin t = (Twin) n;
                            ee.appendChild(createTwinGoalElement(t));
                        }

                    }
                }

            } else if (ent.getClass().toString().contains("ORentailment")) {

                ORentailment oent = (ORentailment) ent;

                ee = doc.createElement("ORentailment");

                if (oent.hasGraphics()) {
                    GSorEntailmentGraphics graphics = oent.getGraphicalProperties();
                    ee.setAttributeNode(createAttribute("length2", "" + graphics.getLength2()));
                    ee.setAttributeNode(createAttribute("toY2", "" + graphics.getToY2()));
                    ee.setAttributeNode(createAttribute("toX2", "" + graphics.getToX2()));
                    ee.setAttributeNode(createAttribute("length", "" + graphics.getLength()));
                    ee.setAttributeNode(createAttribute("toY", "" + graphics.getToY()));
                    ee.setAttributeNode(createAttribute("toX", "" + graphics.getToX()));
                    ee.setAttributeNode(createAttribute("y", "" + graphics.getY()));
                    ee.setAttributeNode(createAttribute("x", "" + graphics.getX()));
                }

                if (oent.isParent()) {

                    ArrayList<GSnode> goals = oent.getChildren();

                    for (GSnode n : goals) {
                        Goal g = (Goal) n;
                        ee.appendChild(createNode(g));
                    }
                }
            }
            e.appendChild(ee);
        }

        if (goal.isOperationalized()) {

            Element ope = doc.createElement("OperationalizingProducts");

            OperationalizingProducts ops = goal.getOperationalizingProducts();
            ArrayList<String> products = ops.getProducts();

            if (ops.hasGraphics()) {
                GSnodeGraphics graphics = ops.getGraphicalProperties();
                ope.setAttributeNode(createAttribute("height", "" + graphics.getHeight()));
                ope.setAttributeNode(createAttribute("width", "" + graphics.getWidth()));
                ope.setAttributeNode(createAttribute("y", "" + graphics.getY()));
                ope.setAttributeNode(createAttribute("x", "" + graphics.getX()));
            }

            for (String s : products) {
                ope.appendChild(createTextElement("Operationalizer", s));
            }

            e.appendChild(ope);
        }

        if (goal.isTerminated()) {
            Element ate = doc.createElement("AssumptionTermination");
            AssumptionTermination at = goal.getAssumptionTermination();

            if (at.hasGraphics()) {
                GSnodeGraphics graphics = at.getGraphicalProperties();
                ate.setAttributeNode(createAttribute("height", "" + graphics.getHeight()));
                ate.setAttributeNode(createAttribute("width", "" + graphics.getWidth()));
                ate.setAttributeNode(createAttribute("y", "" + graphics.getY()));
                ate.setAttributeNode(createAttribute("x", "" + graphics.getX()));
            }

            e.appendChild(ate);
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

    public Element createTwinGoalElement(Twin tg) {

        Element twinGoalElement = doc.createElement("Twin");
        
        if (tg.hasGraphics()) {
            GSnodeGraphics g = (GSnodeGraphics) tg.getGraphicalProperties();
            twinGoalElement.appendChild(createAttribute("x",""+g.getX()));
            twinGoalElement.appendChild(createAttribute("y",""+g.getY()));
            twinGoalElement.appendChild(createAttribute("width",""+g.getWidth()));
            twinGoalElement.appendChild(createAttribute("height",""+g.getHeight()));
        }
        twinGoalElement.appendChild(createTextElement("ID", tg.getID()));
        twinGoalElement.appendChild(createPropositionElement(tg.getProposition()));

        return twinGoalElement;
    }

    public Element createPropositionElement(GoalOrientedProposition gop) {

        Element pe = doc.createElement("Proposition");

        if (gop.hasPrefix()) {
            String prefix = gop.getPrefix();
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
                    ae.setAttributeNode(createAttribute("height", "" + graphics.getHeight()));
                    ae.setAttributeNode(createAttribute("width", "" + graphics.getWidth()));
                    ae.setAttributeNode(createAttribute("y", "" + graphics.getY()));
                    ae.setAttributeNode(createAttribute("x", "" + graphics.getX()));
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

                    AssumptionJudgement aj = (AssumptionJudgement) j;

                    Element aje = doc.createElement("AssumptionJudgement");

                    ConfidenceFactorRating ascfr = aj.getConfidenceFactorRating();

                    aje.appendChild(createTextElement("" + ascfr.getKey(), "" + ascfr.getValue()));

                    ae.appendChild(aje);
                    ase.appendChild(ae);
                }

            }
            pe.appendChild(ase);
        }
        return pe;
    }

    public boolean isComplete() {
        return errorCount == 0;
    }

}
