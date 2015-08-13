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
        
        if (goal.isRootGoal()) {
            e.appendChild(createTextElement("root", "true"));
        }

        if (goal.hasGraphics()) {
            GSnodeGraphics graphics = goal.getGraphicalProperties();
            e.appendChild(createTextElement("height", "" + graphics.getHeight()));
            e.appendChild(createTextElement("width", "" + graphics.getWidth()));
            e.appendChild(createTextElement("y", "" + graphics.getY()));
            e.appendChild(createTextElement("x", "" + graphics.getX()));
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
                    ee.appendChild(createTextElement("length", "" + graphics.getLength()));
                    ee.appendChild(createTextElement("toY", "" + graphics.getToY()));
                    ee.appendChild(createTextElement("toX", "" + graphics.getToX()));
                    ee.appendChild(createTextElement("y", "" + graphics.getY()));
                    ee.appendChild(createTextElement("x", "" + graphics.getX()));
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
                    ee.appendChild(createTextElement("length2", "" + graphics.getLength2()));
                    ee.appendChild(createTextElement("toY2", "" + graphics.getToY2()));
                    ee.appendChild(createTextElement("toX2", "" + graphics.getToX2()));
                    ee.appendChild(createTextElement("length", "" + graphics.getLength()));
                    ee.appendChild(createTextElement("toY", "" + graphics.getToY()));
                    ee.appendChild(createTextElement("toX", "" + graphics.getToX()));
                    ee.appendChild(createTextElement("y", "" + graphics.getY()));
                    ee.appendChild(createTextElement("x", "" + graphics.getX()));
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
                ope.appendChild(createTextElement("height", "" + graphics.getHeight()));
                ope.appendChild(createTextElement("width", "" + graphics.getWidth()));
                ope.appendChild(createTextElement("y", "" + graphics.getY()));
                ope.appendChild(createTextElement("x", "" + graphics.getX()));
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
                ate.appendChild(createTextElement("height", "" + graphics.getHeight()));
                ate.appendChild(createTextElement("width", "" + graphics.getWidth()));
                ate.appendChild(createTextElement("y", "" + graphics.getY()));
                ate.appendChild(createTextElement("x", "" + graphics.getX()));
            }

            e.appendChild(ate);
        }

        
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
            twinGoalElement.appendChild(createTextElement("x", "" + g.getX()));
            twinGoalElement.appendChild(createTextElement("y", "" + g.getY()));
            twinGoalElement.appendChild(createTextElement("width", "" + g.getWidth()));
            twinGoalElement.appendChild(createTextElement("height", "" + g.getHeight()));
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
                    ae.appendChild(createTextElement("height", "" + graphics.getHeight()));
                    ae.appendChild(createTextElement("width", "" + graphics.getWidth()));
                    ae.appendChild(createTextElement("y", "" + graphics.getY()));
                    ae.appendChild(createTextElement("x", "" + graphics.getX()));
                }

                Judgement j = a.getJudgement();

                if (a.getJudgement().getClass().toString().contains("GoalJudgement")) {

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
