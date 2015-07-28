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
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GraphParser {

    private ArrayList<Goal> goals = new ArrayList<>();

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
     * @param gsnode a DOM element node describing a goal.
     * @return a graph node object with instance variable set as described by
     * the element.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Goal getNode(Element gsnode) throws ParserConfigurationException, SAXException, IOException {

        Goal goal = new Goal();
        Element theRoot = gsnode;
        NodeList nodes = theRoot.getChildNodes();
 
        /*for (int i = 0; i < nodes.getLength(); i++) {
            
            if (nodes.item(i).getNodeType() == Node.ATTRIBUTE_NODE) {
               
                Attr attribute = (Attr) nodes.item(i);
                NodeList textNodes = attribute.getChildNodes();
                String value = attribute.getNodeName();
                String textValue = "";
                GSnodeGraphics graphics = new GSnodeGraphics();

                for (int j = 0; j < textNodes.getLength(); j++) {
                    if (textNodes.item(j).getNodeType() == Node.TEXT_NODE) {
                        textValue = textNodes.item(0).getTextContent().trim();
                    }
                }
                if (value.equalsIgnoreCase("height")) {
                    int height = Integer.parseInt(textValue);
                    graphics.setHeight(height);                    
                }
                if (value.equalsIgnoreCase("width")) {
                    int width = Integer.parseInt(textValue);
                    graphics.setWidth(width);
                }
                if (value.equalsIgnoreCase("x")) {
                    int x = Integer.parseInt(textValue);
                    graphics.setX(x);
                }
                if (value.equalsIgnoreCase("y")) {
                    int y = Integer.parseInt(textValue);
                    graphics.setY(y);
                }
                goal.setGraphicalProperties(graphics);
            }
        } */

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {

                GSnodeGraphics gs = new GSnodeGraphics();               
                
                Element element = (Element) nodes.item(i);
                
                //System.out.println("got here " +element.getAttribute("height"));
                //System.out.println("got here " +element.getAttribute("width"));
                //System.out.println("got here " +element.getAttribute("x"));
                //System.out.println("got here " +element.getAttribute("y"));
                
                if(!element.getAttribute("height").isEmpty()) {
                    System.out.println("height " +element.getAttribute("height"));
                }
                if(!element.getAttribute("width").isEmpty()) {
                    System.out.println("width " +element.getAttribute("width"));
                }
                if(!element.getAttribute("x").isEmpty()) {
                    System.out.println("x " +element.getAttribute("x"));
                }
                if(!element.getAttribute("y").isEmpty()) {
                    System.out.println("y " +element.getAttribute("y"));
                }
                
                //gs.setHeight(Integer.parseInt(element.getAttribute("x")));
                //goal.setGraphicalProperties(gs);
                
                
                
                NodeList textNodes = element.getChildNodes();
                String value = element.getNodeName();
                String textValue = "";
                for (int j = 0; j < textNodes.getLength(); j++) {
                    if (textNodes.item(j).getNodeType() == Node.TEXT_NODE) {
                        textValue = textNodes.item(0).getTextContent().trim();
                    }
                }

                if (value.equalsIgnoreCase("id")) {
                    goal.setID(textValue);
                } else if (value.equalsIgnoreCase("proposition")) {

                    NodeList propositionNodes = element.getChildNodes();
                    GoalOrientedProposition gop = new GoalOrientedProposition();

                    for (int j = 0; j < propositionNodes.getLength(); j++) {

                        if (propositionNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {

                            Element propsitionElement = (Element) propositionNodes.item(j);
                            NodeList propTextNodes = propositionNodes.item(j).getChildNodes();
                            String propValue = propsitionElement.getNodeName();
                            String propTextValue = "";

                            for (int k = 0; k < propTextNodes.getLength(); k++) {
                                if (propTextNodes.item(k).getNodeType() == Node.TEXT_NODE) {
                                    propTextValue = propsitionElement.getChildNodes().item(0).getTextContent().trim();
                                }
                            }

                            String prefix;
                            String statement;
                            //boolean assumption;

                            if (propValue.equalsIgnoreCase("prefix")) {

                                prefix = propTextValue;

                                if (prefix.contains("m")) {
                                    gop.setPrefix(GoalType.MOTIVATION);
                                } else if (prefix.contains("b")) {
                                    gop.setPrefix(GoalType.BEHAVIOUR);
                                } else if (prefix.contains("c")) {
                                    gop.setPrefix(GoalType.CONSTRAINT);
                                } else if (prefix.contains("a")) {
                                    gop.setPrefix(GoalType.ASSUMPTION);
                                } else if (prefix.contains("o")) {
                                    gop.setPrefix(GoalType.OBSTACLE);
                                }

                            } else if (propValue.equalsIgnoreCase("statement")) {
                                statement = propTextValue;
                                gop.setStatement(statement);
                            } else if (propValue.equalsIgnoreCase("context")) {
                                gop.setContext(propTextValue);
                            } else if (propValue.equalsIgnoreCase("annotations")) {

                                NodeList annotationsNodes = propsitionElement.getChildNodes();

                                for (int l = 0; l < annotationsNodes.getLength(); l++) {
                                    if (annotationsNodes.item(l).getNodeType() == Node.ELEMENT_NODE) {

                                        Annotation annotation = new Annotation();

                                        Element annotationElement = (Element) annotationsNodes.item(l);
                                        NodeList annotationNodes = annotationElement.getChildNodes();
                                        String annoValue = annotationElement.getNodeName();
                                        String annoElementValue = "";

                                        for (int m = 0; m < annotationNodes.getLength(); m++) {
                                            if (annotationNodes.item(m).getNodeType() == Node.ELEMENT_NODE) {
                                                annoElementValue = annotationNodes.item(m).getNodeName();
                                            }

                                            if (annoElementValue.equalsIgnoreCase("goaljudgement")) {

                                                GoalJudgement gj = new GoalJudgement();

                                                Node goalJudgementElement = (Node) annotationNodes.item(m);
                                                NodeList goalJudgementNodes = goalJudgementElement.getChildNodes();
                                                String goalJudgementValue = goalJudgementElement.getNodeName();
                                                String goalJudgementTextValue = "";

                                                for (int n = 0; n < goalJudgementNodes.getLength(); n++) {
                                                    if (goalJudgementNodes.item(n).getNodeType() == Node.ELEMENT_NODE) {

                                                        Element ratingElement = (Element) goalJudgementNodes.item(n);
                                                        NodeList ratingsNodes = ratingElement.getChildNodes();
                                                        String ratingsValue = ratingElement.getNodeName();
                                                        String ratingsTextValue = "";

                                                        for (int o = 0; o < ratingsNodes.getLength(); o++) {
                                                            if (ratingsNodes.item(o).getNodeType() == Node.TEXT_NODE) {
                                                                ratingsTextValue = ratingElement.getChildNodes().item(0).getTextContent().trim();
                                                            }
                                                        }

                                                        GSordinalScale s = null;

                                                        if (ratingsValue.equalsIgnoreCase("refine")) {

                                                            if (ratingsTextValue.equalsIgnoreCase("none")) {
                                                                s = GSordinalScale.NONE;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("low")) {
                                                                s = GSordinalScale.LOW;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("medium")) {
                                                                s = GSordinalScale.MEDIUM;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("high")) {
                                                                s = GSordinalScale.HIGH;

                                                            }

                                                            ConfidenceFactorRating cfr = new ConfidenceFactorRating(ConfidenceFactor.Refine, s);
                                                            gj.addConfidenceFactorRating(cfr);

                                                        } else if (ratingsValue.equalsIgnoreCase("engage")) {

                                                            if (ratingsTextValue.equalsIgnoreCase("none")) {
                                                                s = GSordinalScale.NONE;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("low")) {
                                                                s = GSordinalScale.LOW;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("medium")) {
                                                                s = GSordinalScale.MEDIUM;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("high")) {
                                                                s = GSordinalScale.HIGH;
                                                            }

                                                            ConfidenceFactorRating cfr2 = new ConfidenceFactorRating(ConfidenceFactor.Engage, s);
                                                            gj.addConfidenceFactorRating(cfr2);

                                                        } else if (ratingsValue.equalsIgnoreCase("value")) {

                                                            SignificanceFactorRating sfr = new SignificanceFactorRating(SignificanceFactor.Value, Integer.parseInt(ratingsTextValue));
                                                            gj.addSignificanceFactorRating(sfr);

                                                        }

                                                        annotation.setJudgement(gj);
                                                    }
                                                }

                                            } else if (annoElementValue.equalsIgnoreCase("leafjudgement")) {

                                                LeafJudgement lj = new LeafJudgement();

                                                Node leafJudgementElement = (Node) annotationNodes.item(m);
                                                NodeList leafJudgementNodes = leafJudgementElement.getChildNodes();
                                                String leafJudgementValue = leafJudgementElement.getNodeName();
                                                String leafJudgementTextValue = "";

                                                for (int p = 0; p < leafJudgementNodes.getLength(); p++) {
                                                    if (leafJudgementNodes.item(p).getNodeType() == Node.ELEMENT_NODE) {

                                                        Element ratingElement = (Element) leafJudgementNodes.item(p);
                                                        NodeList ratingsNodes = ratingElement.getChildNodes();
                                                        String ratingsValue = ratingElement.getNodeName();
                                                        String ratingsTextValue = "";

                                                        for (int r = 0; r < ratingsNodes.getLength(); r++) {
                                                            if (ratingsNodes.item(r).getNodeType() == Node.TEXT_NODE) {
                                                                ratingsTextValue = ratingElement.getChildNodes().item(0).getTextContent().trim();
                                                            }
                                                        }

                                                        GSordinalScale s = null;

                                                        if (ratingsValue.equalsIgnoreCase("achieve")) {

                                                            if (ratingsTextValue.equalsIgnoreCase("none")) {
                                                                s = GSordinalScale.NONE;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("low")) {
                                                                s = GSordinalScale.LOW;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("medium")) {
                                                                s = GSordinalScale.MEDIUM;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("high")) {
                                                                s = GSordinalScale.HIGH;
                                                            }

                                                            ConfidenceFactorRating cfr = new ConfidenceFactorRating(ConfidenceFactor.Achieve, s);
                                                            lj.addConfidenceFactorRating(cfr);

                                                        } else if (ratingsValue.equalsIgnoreCase("cost")) {

                                                            SignificanceFactorRating sfr = new SignificanceFactorRating(SignificanceFactor.Cost, Integer.parseInt(ratingsTextValue));
                                                            lj.addSignificanceFactorRating(sfr);
                                                        }

                                                        annotation.setJudgement(lj);
                                                    }

                                                }

                                            } else if (annoElementValue.equalsIgnoreCase("assumptionjudgement")) {

                                                AssumptionJudgement aj = new AssumptionJudgement();

                                                Node assumptionJudgementElement = (Node) annotationNodes.item(m);
                                                NodeList assumptionJudgementNodes = assumptionJudgementElement.getChildNodes();

                                                for (int s = 0; s < assumptionJudgementNodes.getLength(); s++) {

                                                    if (assumptionJudgementNodes.item(s).getNodeType() == Node.ELEMENT_NODE) {

                                                        Element ratingElement = (Element) assumptionJudgementNodes.item(s);
                                                        NodeList ratingsNodes = ratingElement.getChildNodes();
                                                        String ratingsValue = ratingElement.getNodeName();
                                                        String ratingsTextValue = "";

                                                        for (int t = 0; t < ratingsNodes.getLength(); t++) {
                                                            if (ratingsNodes.item(t).getNodeType() == Node.TEXT_NODE) {
                                                                ratingsTextValue = ratingElement.getChildNodes().item(0).getTextContent().trim();
                                                            }
                                                        }

                                                        GSordinalScale sc = null;

                                                        if (ratingsValue.equalsIgnoreCase("assume")) {

                                                            if (ratingsTextValue.equalsIgnoreCase("none")) {
                                                                sc = GSordinalScale.NONE;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("low")) {
                                                                sc = GSordinalScale.LOW;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("medium")) {
                                                                sc = GSordinalScale.MEDIUM;
                                                            } else if (ratingsTextValue.equalsIgnoreCase("high")) {
                                                                sc = GSordinalScale.HIGH;
                                                            }

                                                            ConfidenceFactorRating cfr = new ConfidenceFactorRating(ConfidenceFactor.Assume, sc);
                                                            aj.addConfidenceFactorRating(cfr);
                                                        }

                                                        annotation.setJudgement(aj);

                                                    }
                                                }

                                            }
                                        }
                                        gop.addChild(annotation);
                                    }
                                }

                            }

                        }
                    }
                    goal.addChild(gop);
                } else if (value.equalsIgnoreCase("fit")) {
                    goal.setFit(value);
                } else if (value.equalsIgnoreCase("andentailment")) {

                    ANDentailment andEntailment = new ANDentailment();
                    goal.addChild(andEntailment);
                    NodeList nodesOfAndEntailment = element.getChildNodes();
                    ArrayList<GSnode> c = new ArrayList<>();

                    for (int z = 0; z < nodesOfAndEntailment.getLength(); z++) {
                        if (nodesOfAndEntailment.item(z).getNodeType() == Node.ATTRIBUTE_NODE) {

                            Attr attribute2 = (Attr) nodesOfAndEntailment.item(z);
                            NodeList entailmentNodes = attribute2.getChildNodes();
                            String entailmentValue = attribute2.getNodeName();
                            String entailmentTextValue = "";
                            GSentailmentGraphics graphics = new GSentailmentGraphics();

                            for (int a = 0; a < entailmentNodes.getLength(); a++) {
                                if (entailmentNodes.item(a).getNodeType() == Node.TEXT_NODE) {
                                    entailmentTextValue = entailmentNodes.item(0).getTextContent().trim();
                                }
                            }

                            if (entailmentValue.equalsIgnoreCase("length")) {
                                int length = Integer.parseInt(entailmentTextValue);
                                graphics.setLength(length);
                            }
                            if (entailmentValue.equalsIgnoreCase("tox")) {
                                int tox = Integer.parseInt(entailmentTextValue);
                                graphics.setToX(tox);
                            }
                            if (entailmentValue.equalsIgnoreCase("toy")) {
                                int toy = Integer.parseInt(entailmentTextValue);
                                graphics.setToY(toy);
                            }
                            if (entailmentValue.equalsIgnoreCase("x")) {
                                int x = Integer.parseInt(entailmentTextValue);
                                graphics.setX(x);
                            }
                            if (entailmentValue.equalsIgnoreCase("y")) {
                                int y = Integer.parseInt(entailmentTextValue);
                                graphics.setY(y);
                            }
                            andEntailment.setGraphicalProperties(graphics);
                        }
                    }

                        for (int a = 0; a < nodesOfAndEntailment.getLength(); a++) {

                            if (nodesOfAndEntailment.item(a).getNodeType() == Node.ELEMENT_NODE) {

                                Element elementOfEntailment = (Element) nodesOfAndEntailment.item(a);
                                NodeList entailmentNodes = nodesOfAndEntailment.item(a).getChildNodes();
                                String entailmentValue = elementOfEntailment.getNodeName();

                                if (entailmentValue.equalsIgnoreCase("twin")) {

                                    GSnodeGraphics g = new GSnodeGraphics();
                                    NodeList nodesOfTwin = elementOfEntailment.getChildNodes();

                                    for (int n = 0; n < nodesOfTwin.getLength(); n++) {
                                        if (nodesOfTwin.item(n).getNodeType() == Node.ATTRIBUTE_NODE) {

                                            Attr twinAttribute = (Attr) nodesOfTwin.item(n);
                                            NodeList twinNodes = twinAttribute.getChildNodes();
                                            String twinValue = twinAttribute.getNodeName();
                                            String twinTextValue = "";

                                            for (int m = 0; m < entailmentNodes.getLength(); m++) {
                                                if (twinNodes.item(m).getNodeType() == Node.TEXT_NODE) {
                                                    twinTextValue = entailmentNodes.item(0).getTextContent().trim();
                                                }
                                            }

                                            if (twinValue.equalsIgnoreCase("height")) {
                                                int height = Integer.parseInt(twinTextValue);
                                                g.setHeight(height);
                                            }
                                            if (twinValue.equalsIgnoreCase("width")) {
                                                int width = Integer.parseInt(twinTextValue);
                                                g.setWidth(width);
                                            }
                                            if (twinValue.equalsIgnoreCase("x")) {
                                                int x = Integer.parseInt(twinTextValue);
                                                g.setX(x);
                                            }
                                            if (twinValue.equalsIgnoreCase("y")) {
                                                int y = Integer.parseInt(twinTextValue);
                                                g.setY(y);
                                            }

                                        } else if (nodesOfTwin.item(n).getNodeType() == Node.ELEMENT_NODE) {
                                            Element twinElement = (Element) nodesOfTwin.item(n);
                                            NodeList twinNodes = twinElement.getChildNodes();
                                            String twinValue = twinElement.getNodeName();
                                            String twinTextValue = "";

                                            for (int m = 0; m < twinNodes.getLength(); m++) {
                                                if (twinNodes.item(m).getNodeType() == Node.TEXT_NODE) {
                                                    twinTextValue = twinNodes.item(0).getTextContent().trim();
                                                }
                                            }

                                            if (twinValue.equalsIgnoreCase("id")) {
                                                for (Goal gl : goals) {
                                                    if (gl.getId().equalsIgnoreCase(twinTextValue)) {
                                                        Twin tg = new Twin(gl);
                                                        tg.setGraphicalProperties(g);
                                                        gl.addChild(tg);
                                                        andEntailment.addChild(tg);
                                                    }
                                                }
                                            }
                                        }

                                    }

                                } else if (entailmentValue.equalsIgnoreCase("goal")) {

                                    //Goal g = getNode(elementOfEntailment);
                                    //andEntailment.addChild(g);
                                    c.add(getNode(elementOfEntailment));
                                }

                            }
                            
                            
                        }
                        andEntailment.setChildren(c);
                        //goal.addChild(andEntailment);
                    

                } else if (value.equalsIgnoreCase("orentailment")) {

                    NodeList nodesOfOrEntailment = element.getChildNodes();
                    ORentailment orEntailment = new ORentailment();

                    for (int z = 0; z < nodesOfOrEntailment.getLength(); z++) {
                        if (nodesOfOrEntailment.item(z).getNodeType() == Node.ATTRIBUTE_NODE) {

                            Attr attribute2 = (Attr) nodesOfOrEntailment.item(z);
                            NodeList entailmentNodes = attribute2.getChildNodes();
                            String entailmentValue = attribute2.getNodeName();
                            String entailmentTextValue = "";
                            GSorEntailmentGraphics graphics = new GSorEntailmentGraphics();

                            for (int a = 0; a < entailmentNodes.getLength(); a++) {
                                if (entailmentNodes.item(a).getNodeType() == Node.TEXT_NODE) {
                                    entailmentTextValue = entailmentNodes.item(0).getTextContent().trim();
                                }
                            }

                            if (entailmentValue.equalsIgnoreCase("length")) {
                                int length = Integer.parseInt(entailmentTextValue);
                                graphics.setLength(length);
                            }

                            if (entailmentValue.equalsIgnoreCase("length2")) {
                                int length2 = Integer.parseInt(entailmentTextValue);
                                graphics.setLength2(length2);
                            }

                            if (entailmentValue.equalsIgnoreCase("tox")) {
                                int tox = Integer.parseInt(entailmentTextValue);
                                graphics.setToX(tox);
                            }
                            if (entailmentValue.equalsIgnoreCase("tox2")) {
                                int tox2 = Integer.parseInt(entailmentTextValue);
                                graphics.setToX2(tox2);
                            }
                            if (entailmentValue.equalsIgnoreCase("toy")) {
                                int toy = Integer.parseInt(entailmentTextValue);
                                graphics.setToY(toy);
                            }
                            if (entailmentValue.equalsIgnoreCase("toy2")) {
                                int toy2 = Integer.parseInt(entailmentTextValue);
                                graphics.setToY2(toy2);
                            }
                            if (entailmentValue.equalsIgnoreCase("x")) {
                                int x = Integer.parseInt(entailmentTextValue);
                                graphics.setX(x);
                            }
                            if (entailmentValue.equalsIgnoreCase("y")) {
                                int y = Integer.parseInt(entailmentTextValue);
                                graphics.setY(y);
                            }
                            orEntailment.setGraphicalProperties(graphics);
                        }
                        for (int a = 0; a < nodesOfOrEntailment.getLength(); a++) {
                            if (nodesOfOrEntailment.item(a).getNodeType() == Node.ELEMENT_NODE) {

                                Element elementOfEntailment = (Element) nodesOfOrEntailment.item(a);
                                NodeList entailmentNodes = nodesOfOrEntailment.item(a).getChildNodes();
                                String entailmentValue = elementOfEntailment.getNodeName();

                                if (entailmentValue.equalsIgnoreCase("twin")) {

                                    GSnodeGraphics g = new GSnodeGraphics();
                                    NodeList nodesOfTwin = elementOfEntailment.getChildNodes();

                                    for (int n = 0; n < nodesOfTwin.getLength(); n++) {
                                        if (nodesOfTwin.item(n).getNodeType() == Node.ATTRIBUTE_NODE) {

                                            Attr twinAttribute = (Attr) nodesOfTwin.item(n);
                                            NodeList twinNodes = twinAttribute.getChildNodes();
                                            String twinValue = twinAttribute.getNodeName();
                                            String twinTextValue = "";

                                            for (int m = 0; m < entailmentNodes.getLength(); m++) {
                                                if (twinNodes.item(m).getNodeType() == Node.TEXT_NODE) {
                                                    twinTextValue = entailmentNodes.item(0).getTextContent().trim();
                                                }
                                            }

                                            if (twinValue.equalsIgnoreCase("height")) {
                                                int height = Integer.parseInt(twinTextValue);
                                                g.setHeight(height);
                                            }
                                            if (twinValue.equalsIgnoreCase("width")) {
                                                int width = Integer.parseInt(twinTextValue);
                                                g.setWidth(width);
                                            }
                                            if (twinValue.equalsIgnoreCase("x")) {
                                                int x = Integer.parseInt(twinTextValue);
                                                g.setX(x);
                                            }
                                            if (twinValue.equalsIgnoreCase("y")) {
                                                int y = Integer.parseInt(twinTextValue);
                                                g.setY(y);
                                            }

                                        } else if (nodesOfTwin.item(n).getNodeType() == Node.ELEMENT_NODE) {
                                            Element twinElement = (Element) nodesOfTwin.item(n);
                                            NodeList twinNodes = twinElement.getChildNodes();
                                            String twinValue = twinElement.getNodeName();
                                            String twinTextValue = "";

                                            for (int m = 0; m < entailmentNodes.getLength(); m++) {
                                                if (twinNodes.item(m).getNodeType() == Node.TEXT_NODE) {
                                                    twinTextValue = entailmentNodes.item(0).getTextContent().trim();
                                                }
                                            }

                                            if (twinValue.equalsIgnoreCase("id")) {
                                                for (Goal gl : goals) {
                                                    if (gl.getId().equalsIgnoreCase(twinTextValue)) {
                                                        Twin tg = new Twin(gl);
                                                        tg.setGraphicalProperties(g);
                                                        gl.addChild(tg);
                                                        orEntailment.addChild(tg);
                                                    }
                                                }
                                            }
                                        }

                                    }

                                } else if (entailmentValue.equalsIgnoreCase("goal")) {
                                    orEntailment.addChild(getNode(elementOfEntailment));
                                }
                                goal.addChild(orEntailment);
                            } else if (element.getNodeName().equals("operationalizingproducts")) {

                                NodeList nodesOfOpzNodeElement = element.getChildNodes();
                                OperationalizingProducts ops = new OperationalizingProducts();
                                GSnodeGraphics g = new GSnodeGraphics();

                                for (int n = 0; n < nodesOfOpzNodeElement.getLength(); n++) {
                                    if (nodesOfOpzNodeElement.item(n).getNodeType() == Node.ATTRIBUTE_NODE) {

                                        Attr attribute = (Attr) nodesOfOpzNodeElement.item(n);
                                        NodeList operationalizerTextNodes = attribute.getChildNodes();
                                        String operationalizerValue = attribute.getNodeName();
                                        String operationalizerTextValue = "";

                                        for (int j = 0; j < operationalizerTextNodes.getLength(); j++) {
                                            if (operationalizerTextNodes.item(j).getNodeType() == Node.TEXT_NODE) {
                                                operationalizerTextValue = operationalizerTextNodes.item(0).getTextContent().trim();
                                            }
                                        }
                                        if (operationalizerValue.equalsIgnoreCase("height")) {
                                            int height = Integer.parseInt(operationalizerTextValue);
                                            g.setHeight(height);
                                        }
                                        if (operationalizerValue.equalsIgnoreCase("width")) {
                                            int width = Integer.parseInt(operationalizerTextValue);
                                            g.setWidth(width);
                                        }
                                        if (operationalizerValue.equalsIgnoreCase("x")) {
                                            int x = Integer.parseInt(operationalizerTextValue);
                                            g.setX(x);
                                        }
                                        if (operationalizerValue.equalsIgnoreCase("y")) {
                                            int y = Integer.parseInt(operationalizerTextValue);
                                            g.setY(y);
                                        }
                                        ops.setGraphicalProperties(g);

                                    } else {
                                        Element operationalizingProductsElement = (Element) nodesOfOpzNodeElement.item(n);
                                        NodeList operationalizingProductsTextNodes = operationalizingProductsElement.getChildNodes();
                                        String operationalizingProductsValue = element.getNodeName();
                                        String operationalizingProductsTextValue = "";

                                        for (int j = 0; j < textNodes.getLength(); j++) {
                                            if (operationalizingProductsTextNodes.item(j).getNodeType() == Node.TEXT_NODE) {
                                                operationalizingProductsTextValue = textNodes.item(0).getTextContent().trim();
                                            }
                                        }

                                        if (operationalizingProductsValue.equalsIgnoreCase("Operationalizer")) {
                                            ops.addProduct(operationalizingProductsTextValue);
                                        }

                                    }
                                }
                                goal.addChild(ops);

                            } else if (element.getNodeName().equals("assumptiontermination")) {

                                NodeList nodesOfAstmnNodeElement = element.getChildNodes();
                                AssumptionTermination at = new AssumptionTermination();
                                GSnodeGraphics g = new GSnodeGraphics();

                                for (int n = 0; n < nodesOfAstmnNodeElement.getLength(); n++) {
                                    if (nodesOfAstmnNodeElement.item(n).getNodeType() == Node.ATTRIBUTE_NODE) {

                                        Attr attribute = (Attr) nodesOfAstmnNodeElement.item(n);
                                        NodeList AssumptionTerminationTextNodes = attribute.getChildNodes();
                                        String AssumptionTerminationValue = attribute.getNodeName();
                                        String AssumptionTerminationTextValue = "";

                                        for (int j = 0; j < AssumptionTerminationTextNodes.getLength(); j++) {
                                            if (AssumptionTerminationTextNodes.item(j).getNodeType() == Node.TEXT_NODE) {
                                                AssumptionTerminationTextValue = AssumptionTerminationTextNodes.item(0).getTextContent().trim();
                                            }
                                        }
                                        if (AssumptionTerminationValue.equalsIgnoreCase("height")) {
                                            int height = Integer.parseInt(AssumptionTerminationTextValue);
                                            g.setHeight(height);
                                        }
                                        if (AssumptionTerminationValue.equalsIgnoreCase("width")) {
                                            int width = Integer.parseInt(AssumptionTerminationTextValue);
                                            g.setWidth(width);
                                        }
                                        if (AssumptionTerminationValue.equalsIgnoreCase("x")) {
                                            int x = Integer.parseInt(AssumptionTerminationTextValue);
                                            g.setX(x);
                                        }
                                        if (AssumptionTerminationValue.equalsIgnoreCase("y")) {
                                            int y = Integer.parseInt(AssumptionTerminationTextValue);
                                            g.setY(y);
                                        }
                                        at.setGraphicalProperties(g);

                                    }
                                }
                                goal.addChild(at);
                            }

                        }

                    }
                }
            }

        }
        goals.add(goal);
        return goal;
    }

}
