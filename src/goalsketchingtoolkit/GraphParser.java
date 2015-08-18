/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
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
 * A graph parser is used for parsing XML documents which contain data
 * pertaining to a goal refinement diagram which may or may not be structurally
 * complete.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GraphParser {

    private ArrayList<Goal> goals = new ArrayList<>();
    private GoalGraphModelInterface model;
    private GoalSketchingView view;

    /**
     * Constructs a graph parser.
     */
    public GraphParser() {
    }

    /**
     * Constructs a graph parser and initialises the model.
     *
     * @param model the model to add parsed nodes to.
     * @param view the view to add parsed graphics objects to.
     */
    public GraphParser(GoalGraphModelInterface model, GoalSketchingView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Parses an input source and returns an XML document.
     *
     * @param fileName the name of the file to parse.
     * @return the XML document to parse.
     * @throws javax.xml.parsers.ParserConfigurationException in the event of a
     * a serious configuration error.
     * @throws org.xml.sax.SAXException if there is an XML parse error or
     * warning.
     * @throws java.io.IOException if an I/O operation has failed or been
     * interrupted.
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
     * @throws ParserConfigurationException in the event of a serious
     * configuration error.
     * @throws SAXException to indicate an XML parse error or warning.
     * @throws IOException if an I/O operation has failed or been interrupted.
     */
    public Goal getGoal(Element gsnode) throws ParserConfigurationException, SAXException, IOException {

        Goal goal = new Goal();
        GSnodeGraphics gs = new GSnodeGraphics();
        Element theRoot = gsnode;
        NodeList nodes = theRoot.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) nodes.item(i);
                NodeList textNodes = element.getChildNodes();
                String value = element.getNodeName();
                String textValue = "";

                for (int j = 0; j < textNodes.getLength(); j++) {
                    if (textNodes.item(j).getNodeType() == Node.TEXT_NODE) {
                        textValue = textNodes.item(0).getTextContent().trim();
                    }
                }

                if (value.equalsIgnoreCase("root")) {
                    goal.setIsRootGoal(true);
                } else if (value.equalsIgnoreCase("x")) {
                    int x = Integer.parseInt(textValue);
                    gs.setX(x);
                    //System.out.println("goal x from parser: "+gs.getX());
                } else if (value.equalsIgnoreCase("y")) {
                    int y = Integer.parseInt(textValue);
                    gs.setY(y);
                } else if (value.equalsIgnoreCase("width")) {
                    int width = Integer.parseInt(textValue);
                    gs.setWidth(width);
                } else if (value.equalsIgnoreCase("height")) {
                    int height = Integer.parseInt(textValue);
                    gs.setHeight(height);
                } else if (value.equalsIgnoreCase("id")) {
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
                                        GSnodeGraphics gs2 = new GSnodeGraphics();

                                        Element annotationElement = (Element) annotationsNodes.item(l);
                                        NodeList annotationElementNodes = annotationsNodes.item(l).getChildNodes();

                                        for (int m = 0; m < annotationElementNodes.getLength(); m++) {
                                            if (annotationElementNodes.item(m).getNodeType() == Node.ELEMENT_NODE) {

                                                Element annotationChildElement = (Element) annotationElementNodes.item(m);
                                                NodeList annotationChildElementTextNodes = annotationElementNodes.item(m).getChildNodes();
                                                String annoElementValue = annotationChildElement.getNodeName();
                                                String annotationTextValue = "";

                                                for (int a = 0; a < annotationChildElementTextNodes.getLength(); a++) {
                                                    if (annotationChildElementTextNodes.item(a).getNodeType() == Node.TEXT_NODE) {
                                                        annotationTextValue = annotationChildElement.getChildNodes().item(0).getTextContent().trim();
                                                    }
                                                }

                                                if (annoElementValue.equalsIgnoreCase("height")) {
                                                    int height = Integer.parseInt(annotationTextValue);
                                                    gs2.setHeight(height);
                                                } else if (annoElementValue.equalsIgnoreCase("width")) {
                                                    int width = Integer.parseInt(annotationTextValue);
                                                    gs2.setWidth(width);
                                                } else if (annoElementValue.equalsIgnoreCase("y")) {
                                                    int y = Integer.parseInt(annotationTextValue);
                                                    gs2.setY(y);
                                                } else if (annoElementValue.equalsIgnoreCase("x")) {
                                                    int x = Integer.parseInt(annotationTextValue);
                                                    gs2.setX(x);

                                                } else if (annoElementValue.equalsIgnoreCase("goaljudgement")) {

                                                    GoalJudgement gj = new GoalJudgement();

                                                    Node goalJudgementElement = (Node) annotationElementNodes.item(m);
                                                    NodeList goalJudgementNodes = goalJudgementElement.getChildNodes();

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

                                                    Node leafJudgementElement = (Node) annotationElementNodes.item(m);
                                                    NodeList leafJudgementNodes = leafJudgementElement.getChildNodes();

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

                                                    Node assumptionJudgementElement = (Node) annotationElementNodes.item(m);
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

                                        }
                                        annotation.setGraphicalProperties(gs2);
                                        gs2.setGSnode(annotation);

                                        gop.addChild(annotation);
                                        view.addDrawable(annotation.getGraphicalProperties());
                                        model.addToGSnodes(annotation);
                                    }

                                }

                            }

                        }
                    }
                    goal.addChild(gop);
                    model.addToGSnodes(gop);
                } else if (value.equalsIgnoreCase("fit")) {
                    goal.setFit(value);
                } else if (value.equalsIgnoreCase("andentailment")) {

                    ANDentailment andEntailment = new ANDentailment();
                    GSentailmentGraphics gs3 = new GSentailmentGraphics();
                    //goal.addChild(andEntailment);
                    NodeList nodesOfAndEntailment = element.getChildNodes();
                    ArrayList<GSnode> c = new ArrayList<>();

                    for (int a = 0; a < nodesOfAndEntailment.getLength(); a++) {

                        if (nodesOfAndEntailment.item(a).getNodeType() == Node.ELEMENT_NODE) {

                            Element elementOfEntailment = (Element) nodesOfAndEntailment.item(a);
                            NodeList entailmentNodes = elementOfEntailment.getChildNodes();
                            String entailmentValue = elementOfEntailment.getNodeName();
                            String entailmentTextValue = "";
                            for (int z = 0; z < entailmentNodes.getLength(); z++) {
                                if (entailmentNodes.item(z).getNodeType() == Node.TEXT_NODE) {
                                    entailmentTextValue = entailmentNodes.item(0).getTextContent().trim();
                                }
                            }

                            if (entailmentValue.equalsIgnoreCase("toY")) {
                                int toy = Integer.parseInt(entailmentTextValue);
                                gs3.setToY(toy);
                            }

                            if (entailmentValue.equalsIgnoreCase("toX")) {
                                int tox = Integer.parseInt(entailmentTextValue);
                                gs3.setToX(tox);
                            }
                            if (entailmentValue.equalsIgnoreCase("y")) {
                                int y = Integer.parseInt(entailmentTextValue);
                                gs3.setY(y);
                            }
                            if (entailmentValue.equalsIgnoreCase("x")) {
                                int x = Integer.parseInt(entailmentTextValue);
                                gs3.setX(x);
                            }

                            if (entailmentValue.equalsIgnoreCase("twin")) {

                                GSnodeGraphics g = new GSnodeGraphics();
                                NodeList nodesOfTwin = elementOfEntailment.getChildNodes();

                                for (int n = 0; n < nodesOfTwin.getLength(); n++) {
                                    if (nodesOfTwin.item(n).getNodeType() == Node.ELEMENT_NODE) {

                                        Element twinElement = (Element) nodesOfTwin.item(n);
                                        NodeList twinNodes = twinElement.getChildNodes();
                                        String twinValue = twinElement.getNodeName();
                                        String twinTextValue = "";

                                        for (int m = 0; m < twinNodes.getLength(); m++) {
                                            if (twinNodes.item(m).getNodeType() == Node.TEXT_NODE) {
                                                twinTextValue = twinNodes.item(0).getTextContent().trim();
                                            }
                                        }
//
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

                                        if (twinValue.equalsIgnoreCase("id")) {
                                            for (Goal gl : goals) {
                                                if (gl.getId().equalsIgnoreCase(twinTextValue)) {
                                                    Twin tg = new Twin(gl);
                                                    tg.setGraphicalProperties(g);
                                                    g.setGSnode(tg);
                                                    gl.addChild(tg);
                                                    andEntailment.addChild(tg);
                                                    view.addDrawable(g);
                                                    model.addToGSnodes(tg);
                                                }
                                            }
                                        }

                                    }
                                }

                            } else if (entailmentValue.equalsIgnoreCase("goal")) {
                                c.add(getGoal(elementOfEntailment));
                            }
                        }
                    }
                    andEntailment.setChildren(c);
                    gs3.setCircle(gs3.getToX());
                    andEntailment.setGraphicalProperties(gs3);
                    gs3.setGSnode(andEntailment);                    
                    goal.addChild(andEntailment);
                    view.addDrawable(andEntailment.getGraphicalProperties());
                    model.addToGSnodes(andEntailment);

                } else if (value.equalsIgnoreCase("orentailment")) {

                    NodeList nodesOfOrEntailment = element.getChildNodes();
                    ORentailment orEntailment = new ORentailment();
                    GSorEntailmentGraphics gs4 = new GSorEntailmentGraphics();

                    for (int a = 0; a < nodesOfOrEntailment.getLength(); a++) {
                        if (nodesOfOrEntailment.item(a).getNodeType() == Node.ELEMENT_NODE) {

                            Element elementOfEntailment = (Element) nodesOfOrEntailment.item(a);
                            NodeList entailmentNodes = nodesOfOrEntailment.item(a).getChildNodes();
                            String entailmentValue = elementOfEntailment.getNodeName();
                            String entailmentTextValue = "";
                            for (int z = 0; z < entailmentNodes.getLength(); z++) {
                                if (entailmentNodes.item(z).getNodeType() == Node.TEXT_NODE) {
                                    entailmentTextValue = elementOfEntailment.getChildNodes().item(0).getTextContent().trim();
                                }
                            }

                            if (entailmentValue.equalsIgnoreCase("tox")) {
                                int tox = Integer.parseInt(entailmentTextValue);
                                gs4.setToX(tox);
                            }
                            if (entailmentValue.equalsIgnoreCase("tox2")) {
                                int tox2 = Integer.parseInt(entailmentTextValue);
                                gs4.setToX2(tox2);
                            }
                            if (entailmentValue.equalsIgnoreCase("toy")) {
                                int toy = Integer.parseInt(entailmentTextValue);
                                gs4.setToY(toy);
                            }
                            if (entailmentValue.equalsIgnoreCase("toy2")) {
                                int toy2 = Integer.parseInt(entailmentTextValue);
                                gs4.setToY2(toy2);
                            }
                            if (entailmentValue.equalsIgnoreCase("x")) {
                                int x = Integer.parseInt(entailmentTextValue);                                
                                gs4.setX(x);
                            }
                            if (entailmentValue.equalsIgnoreCase("y")) {
                                int y = Integer.parseInt(entailmentTextValue);
                                gs4.setY(y);

                            } else if (entailmentValue.equalsIgnoreCase("twin")) {

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
                                orEntailment.addChild(getGoal(elementOfEntailment));
                            }
                        }
                    }
                    gs4.setCircle(gs4.getToX());
                    gs4.setSecondCircle(gs4.getToX2());
                    orEntailment.setGraphicalProperties(gs4);
                    gs4.setGSnode(orEntailment);
                    goal.addChild(orEntailment);
                    view.addDrawable(orEntailment.getGraphicalProperties());
                    model.addToGSnodes(orEntailment);

                } else if (element.getNodeName().equalsIgnoreCase("operationalizingproducts")) {

                    NodeList nodesOfOpzNodeElement = element.getChildNodes();
                    OperationalizingProducts ops = new OperationalizingProducts();
                    GSnodeGraphics g = new GSnodeGraphics();

                    for (int n = 0; n < nodesOfOpzNodeElement.getLength(); n++) {
                        if (nodesOfOpzNodeElement.item(n).getNodeType() == Node.ELEMENT_NODE) {

                            Element operationalizingProductsElement = (Element) nodesOfOpzNodeElement.item(n);
                            NodeList operationalizingProductsTextNodes = operationalizingProductsElement.getChildNodes();
                            String operationalizingProductsValue = operationalizingProductsElement.getNodeName();
                            String operationalizingProductsTextValue = "";
                            for (int j = 0; j < operationalizingProductsTextNodes.getLength(); j++) {
                                if (operationalizingProductsTextNodes.item(j).getNodeType() == Node.TEXT_NODE) {
                                    operationalizingProductsTextValue = operationalizingProductsTextNodes.item(0).getTextContent().trim();
                                }
                            }

                            if (operationalizingProductsValue.equalsIgnoreCase("height")) {
                                int height = Integer.parseInt(operationalizingProductsTextValue);
                                g.setHeight(height);
                            } else if (operationalizingProductsValue.equalsIgnoreCase("width")) {
                                int width = Integer.parseInt(operationalizingProductsTextValue);
                                g.setWidth(width);
                            } else if (operationalizingProductsValue.equalsIgnoreCase("x")) {
                                int x = Integer.parseInt(operationalizingProductsTextValue);
                                g.setX(x);
                            } else if (operationalizingProductsValue.equalsIgnoreCase("y")) {
                                int y = Integer.parseInt(operationalizingProductsTextValue);
                                g.setY(y);
                            } else if (operationalizingProductsValue.equalsIgnoreCase("Operationalizer")) {
                                ops.addProduct(operationalizingProductsTextValue);
                            }

                        }
                    }

                    ops.setGraphicalProperties(g);

                    g.setGSnode(ops);
                    goal.addChild(ops);
                    view.addDrawable(ops.getGraphicalProperties());
                    model.addToGSnodes(ops);

                } else if (element.getNodeName().equalsIgnoreCase("assumptiontermination")) {

                    NodeList nodesOfAstmnNodeElement = element.getChildNodes();
                    AssumptionTermination at = new AssumptionTermination();
                    GSnodeGraphics g = new GSnodeGraphics();

                    for (int n = 0; n < nodesOfAstmnNodeElement.getLength(); n++) {
                        if (nodesOfAstmnNodeElement.item(n).getNodeType() == Node.ELEMENT_NODE) {

                            Element astmnNodeElement = (Element) nodesOfAstmnNodeElement.item(n);
                            NodeList assumptionTerminationTextNodes = astmnNodeElement.getChildNodes();
                            String assumptionTerminationValue = astmnNodeElement.getNodeName();
                            String assumptionTerminationTextValue = "";
                            for (int j = 0; j < assumptionTerminationTextNodes.getLength(); j++) {
                                if (assumptionTerminationTextNodes.item(j).getNodeType() == Node.TEXT_NODE) {
                                    assumptionTerminationTextValue = assumptionTerminationTextNodes.item(0).getTextContent().trim();
                                }
                            }

                            if (assumptionTerminationValue.equalsIgnoreCase("height")) {
                                int height = Integer.parseInt(assumptionTerminationTextValue);
                                g.setHeight(height);
                            } else if (assumptionTerminationValue.equalsIgnoreCase("width")) {
                                int width = Integer.parseInt(assumptionTerminationTextValue);
                                g.setWidth(width);
                            } else if (assumptionTerminationValue.equalsIgnoreCase("x")) {
                                int x = Integer.parseInt(assumptionTerminationTextValue);
                                g.setX(x);
                            } else if (assumptionTerminationValue.equalsIgnoreCase("y")) {
                                int y = Integer.parseInt(assumptionTerminationTextValue);
                                g.setY(y);
                            }

                        }

                    }
                    at.setGraphicalProperties(g);
                    g.setGSnode(at);
                    goal.addChild(at);
                    view.addDrawable(at.getGraphicalProperties());
                    model.addToGSnodes(at);
                }

            }

        }
        goal.setGraphicalProperties(gs);
        gs.setGSnode(goal);

        goals.add(goal);
        view.addDrawable(goal.getGraphicalProperties());
        model.addToGSnodes(goal);
        return goal;

    }

    /**
     * Resets the goal list for this graph parser.
     */
    public void reset() {
        goals = new ArrayList();
    }

}
