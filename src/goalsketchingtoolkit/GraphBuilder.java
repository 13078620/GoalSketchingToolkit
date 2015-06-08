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
import org.w3c.dom.Text;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GraphBuilder {

    private DocumentBuilder builder;
    private Document doc;
    private GraphNode root;
    
    //private boolean complete;
    private int errorCount;

    /**
     * Constructs a graph builder.
     *
     * @param root the root graph node for this document.
     * @throws ParserConfigurationException .
     */
    public GraphBuilder(GraphNode root) throws ParserConfigurationException {

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
        Element e = doc.createElement("graph");
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
    private Element createNode(GraphNode gn) throws ParserConfigurationException {

        Element e = doc.createElement("graphnode");
        //System.out.println(gn.getClass());
        if (gn.isRootNode()) {
            e.appendChild(createTextElement("root", "" + gn.isRootNode()));
        }

        e.appendChild(createTextElement("x", "" + gn.getX()));
        e.appendChild(createTextElement("y", "" + gn.getY()));
        e.appendChild(createTextElement("width", "" + gn.getWidth()));
        e.appendChild(createTextElement("height", "" + gn.getHeight()));

        if (gn.getId() != null) {
            e.appendChild(createTextElement("ID", gn.getId()));
        }

        if (gn.getProposition() != null) {
            Element pe = doc.createElement("proposition");
            String prefix = gn.getProposition().getPrefix();
            String statement = gn.getProposition().getStatement();
            pe.appendChild(createTextElement("prefix", prefix));
            pe.appendChild(createTextElement("statement", statement));
            e.appendChild(pe);
        }

        if (gn.isParentAndChild()) {
            e.appendChild(createTextElement("hasparent", "true"));
        }

        if (gn.isParent()) {
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
                     }*/
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
                errorCount ++;
            }
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
    
       
    public boolean isComplete(){
        return errorCount == 0;
    }

}
