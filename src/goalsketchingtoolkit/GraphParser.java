/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GraphParser {

    private ArrayList<Operationalizer> operationalizers = new ArrayList<>();

    /**
     * Constructs a graph parser.
     */
    public GraphParser() {

    }

    /**
     * Parses an input source and returns an XML document.
     *
     * @param fileName the name of the file to parse.
     * @return the XML document to parse.
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     */
    public Document getDocument(String fileName) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setValidating(false);
        //factory.setSchema(Schema) ;
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new InputSource(fileName));

    }

    /**
     * Parses a graph node element from an XML file and instantiates a super
     * ordinate graph node based on the information.
     *
     * @param gNode a DOM element node describing a graph node.
     * @return a graph node object with instance variable set as described by
     * the element.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public GraphNode getNode(Node gNode) throws ParserConfigurationException, SAXException, IOException {

        GraphNode gn = new GraphNode();
        Element theRoot = (Element) gNode;
        NodeList nodes = theRoot.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) nodes.item(i);
                NodeList textNodes = nodes.item(i).getChildNodes();
                String value = element.getNodeName();
                String textValue = "";
                for (int j = 0; j < textNodes.getLength(); j++) {
                    if (textNodes.item(j).getNodeType() == Node.TEXT_NODE) {
                        textValue = element.getChildNodes().item(0).getTextContent().trim();
                    }
                }
                //System.out.println(textValue);
                if (value.equalsIgnoreCase("root")) {
                    //System.out.println(textValue);
                    gn.setIsRootNode(true);
                } else if (value.equalsIgnoreCase("x")) {
                    double x = Double.parseDouble(textValue);
                    gn.setX(x);
                } else if (value.equalsIgnoreCase("y")) {
                    double y = Double.parseDouble(textValue);
                    gn.setY(y);
                } else if (value.equalsIgnoreCase("width")) {
                    int width = Integer.parseInt(textValue);
                    gn.setWidth(width);
                } else if (value.equalsIgnoreCase("height")) {
                    int height = Integer.parseInt(textValue);
                    gn.setHeight(height);
                } else if (value.equalsIgnoreCase("id")) {
                    gn.setID(textValue);
                } else if (value.equalsIgnoreCase("proposition")) {
                    Element propsitionElement = element;
                    NodeList propNodes = propsitionElement.getChildNodes();
                    Proposition prop = new Proposition();
                    for (int j = 0; j < propNodes.getLength(); j++) {
                        if (propNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element propElement = (Element) propNodes.item(j);
                            String propElementValue = propElement.getNodeName();

                            NodeList propTextNodes = propNodes.item(j).getChildNodes();
                            String propElementTextValue = "";

                            for (int k = 0; k < propTextNodes.getLength(); k++) {
                                if (nodes.item(k).getNodeType() == Node.TEXT_NODE) {
                                    propElementTextValue = propElement.getChildNodes().item(0).getTextContent().trim();
                                }
                            }

                            String prefix;
                            String statement;
                            boolean assumption;
                            //Proposition prop = new Proposition();

                            if (propElementValue.equalsIgnoreCase("prefix")) {
                                prefix = propElementTextValue;
                                if (prefix.contains("a")) {
                                    assumption = true;
                                    prop.setIsAssumption(assumption);
                                }
                                prop.setPrefix(prefix);

                            } else if (propElementValue.equalsIgnoreCase("statement")) {
                                statement = propElementTextValue;
                                prop.setStatement(statement);
                            }

                            //Proposition prop = new Proposition(prefix, statement, assumption);
                        }
                    }
                    gn.setProposition(prop);
                    //System.out.println(gn.getProposition().getStatement());
                } else if (element.getNodeName().equals("hasparent")) {
                    gn.setIsChild(true);
                } else if (element.getNodeName().equals("children")) {

                    gn.setIsParent(true);
                    Element childrenElement = element;
                    NodeList nodesOfChildren = childrenElement.getChildNodes();
                    ArrayList<GraphNode> c = new ArrayList<>();

                    for (int k = 0; k < nodesOfChildren.getLength(); k++) {
                        if (nodesOfChildren.item(k).getNodeType() == Node.ELEMENT_NODE) {
                            Element elementOfChildren = (Element) nodesOfChildren.item(k);

                            c.add(getNode(elementOfChildren));
                        }
                    }
                    gn.setChildGraphNodes(c);

                } else if (element.getNodeName().equals("operationalizernode")) {

                    gn.setIsChild(true);
                    gn.setIsOperationalized(true);
                    OperationalizerNode opNode = new OperationalizerNode();
                    gn.setOperationalizerNode(opNode);
                    Element opzNodeElement = element;
                    NodeList nodesOfOpzNodeElement = opzNodeElement.getChildNodes();

                    for (int n = 0; n < nodesOfOpzNodeElement.getLength(); n++) {

                        if (nodesOfOpzNodeElement.item(n).getNodeType() == Node.ELEMENT_NODE) {

                            Element opzsElement = (Element) nodesOfOpzNodeElement.item(n);
                            String opzsElementValue = opzsElement.getNodeName();
                            String opzsElementTextValue;

                            if (opzsElementValue.equalsIgnoreCase("x")) {
                                opzsElementTextValue = opzsElement.getChildNodes().item(0).getTextContent().trim();
                                double x = Double.parseDouble(opzsElementTextValue);
                                opNode.setX(x);
                            } else if (opzsElementValue.equalsIgnoreCase("y")) {
                                opzsElementTextValue = opzsElement.getChildNodes().item(0).getTextContent().trim();
                                double y = Double.parseDouble(opzsElementTextValue);
                                opNode.setY(y);
                            } else if (opzsElementValue.equalsIgnoreCase("width")) {
                                opzsElementTextValue = opzsElement.getChildNodes().item(0).getTextContent().trim();
                                int width = Integer.parseInt(opzsElementTextValue);
                                opNode.setWidth(width);
                            } else if (opzsElementValue.equalsIgnoreCase("height")) {
                                opzsElementTextValue = opzsElement.getChildNodes().item(0).getTextContent().trim();
                                int height = Integer.parseInt(opzsElementTextValue);
                                opNode.setHeight(height);
                            } else if (opzsElementValue.equalsIgnoreCase("operationalizers")) {

                                //-------------- operationalizer element processing -------------------------
                                NodeList nodesOfOperationalizersElement = opzsElement.getChildNodes();
                                for (int x = 0; x < nodesOfOperationalizersElement.getLength(); x++) {

                                    if (nodesOfOperationalizersElement.item(x).getNodeType() == Node.ELEMENT_NODE) {
                                        Element opElement = (Element) nodesOfOperationalizersElement.item(x);
                                        Operationalizer op = getOperationalizer(opElement);
                                        opNode.addOperationalizer(op);
                                    }
                                }
                            }

                        }

                    }

                } else if (element.getNodeName().equals("assumptionterminationnode")) {

                    gn.setIsChild(true);
                    gn.setIsTerminated(true);
                    AssumptionTerminationNode astn = new AssumptionTerminationNode();
                    gn.setAssumptionTerminationNode(astn);
                    Element astNodeElement = element;
                    NodeList nodesOfAstNodeElement = astNodeElement.getChildNodes();
                    for (int n = 0; n < nodesOfAstNodeElement.getLength(); n++) {

                        if (nodesOfAstNodeElement.item(n).getNodeType() == Node.ELEMENT_NODE) {

                            Element astnElement = (Element) nodesOfAstNodeElement.item(n);
                            String astnElementValue = astnElement.getNodeName();
                            String astnElementTextValue;

                            if (astnElementValue.equalsIgnoreCase("x")) {
                                astnElementTextValue = astnElement.getChildNodes().item(0).getTextContent().trim();
                                double x = Double.parseDouble(astnElementTextValue);
                                astn.setX(x);
                            } else if (astnElementValue.equalsIgnoreCase("y")) {
                                astnElementTextValue = astnElement.getChildNodes().item(0).getTextContent().trim();
                                double y = Double.parseDouble(astnElementTextValue);
                                astn.setY(y);
                            } else if (astnElementValue.equalsIgnoreCase("width")) {
                                astnElementTextValue = astnElement.getChildNodes().item(0).getTextContent().trim();
                                int width = Integer.parseInt(astnElementTextValue);
                                astn.setWidth(width);
                            } else if (astnElementValue.equalsIgnoreCase("height")) {
                                astnElementTextValue = astnElement.getChildNodes().item(0).getTextContent().trim();
                                int height = Integer.parseInt(astnElementTextValue);
                                astn.setHeight(height);
                            }

                        }

                    }

                }

            }
        }
        return gn;
    }

    /**
     *
     * @param opElement
     * @return
     */
    public Operationalizer getOperationalizer(Element opElement) {

        Operationalizer op = new Operationalizer();

        NodeList nodesOfOpElement = opElement.getChildNodes();

        for (int y = 0; y < nodesOfOpElement.getLength(); y++) {

            if (nodesOfOpElement.item(y).getNodeType() == Node.ELEMENT_NODE) {

                Element operationalizerElement = (Element) nodesOfOpElement.item(y);
                operationalizerElement.getNextSibling();
                String opElementValue = operationalizerElement.getNodeName();
                String opElementValueTextValue = operationalizerElement.getChildNodes().item(0).getTextContent().trim();

                if (opElementValue.equalsIgnoreCase("agentname")) {
                    op.setAgentName(opElementValueTextValue);
                    for (int i = 0; i < operationalizers.size(); i++) {
                        if (operationalizers.get(i).getAgentName().equalsIgnoreCase(opElementValueTextValue)) {
                            op = operationalizers.get(i);
                        }
                    }
                } /*else if (opElementValue.equalsIgnoreCase("subdomains")) {
                 ArrayList<String> subDomains = new ArrayList<>();
                 Element subDomainsNode = operationalizerElement;
                 NodeList nodesOfsubDomainsNode = subDomainsNode.getChildNodes();
                 for (int s = 0; s < nodesOfsubDomainsNode.getLength(); s++) {
                 if (nodesOfsubDomainsNode.item(s).getNodeType() == Node.ELEMENT_NODE) {
                 String subDomainTextValue = subDomainsNode.getChildNodes().item(0).getTextContent().trim();
                 subDomains.add(subDomainTextValue); next sibling
                 }

                 }
                 op.setSubDomains(subDomains);

                 }*/

                operationalizers.add(op);
            }

        }

        return op;

    }

}
