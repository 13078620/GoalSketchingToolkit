/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Chris
 */
public class GraphParserTest extends TestCase {
    
    private GraphParser parser = new GraphParser();
    
    ArrayList<GSnode> gsNodes = new ArrayList();
    
    
    
    public GraphParserTest(String testName) {
        super(testName);
    }
    

    /**
     * Test of getGoal method, of class GraphParser.
     */
    public void testGetGoal() throws Exception {
        System.out.println("getGoal");
         try {

            String file = "compositeTestXMLOutput.xml";
            Document xmlDoc = parser.getDocument(file);
            Element theRoot = xmlDoc.getDocumentElement();
            NodeList nodes = theRoot.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodes.item(i);

                    Goal root = parser.getGoal(element);
                    
                    
                    

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void drawGraphFromRoot(Goal g) throws Exception {

        System.out.println("ID: "+g.getId());
         System.out.println("prefix: "+g.getProposition().getPrefix());
         System.out.println("statement: "+g.getProposition().getStatement());
         ArrayList<GSnode> as = g.getProposition().getChildren();
         
         for(GSnode n: as) {
             Annotation an = (Annotation) n;
             if(an.getJudgement().getClass().toString().contains("GoalJudgement")) {
                 GoalJudgement gj = (GoalJudgement) an.getJudgement();
                 System.out.print(gj.getEngageConfidenceFactorRating().getKey()+ "  ");
                 System.out.println(gj.getEngageConfidenceFactorRating().getValue());
             }
             
         }
             
         
         System.out.println();
        
        if (g.isEntailed()) {

            ArrayList<GSnode> children = g.getEntailment().getChildren();
            gsNodes.add(g);

            for (int i = 0; i < children.size(); i++) {
                
                if(children.get(i).getClass().toString().contains("Goal")) {
                    
                    Goal goal = (Goal) children.get(i);
                    
                    drawGraphFromRoot(goal);
                }

                gsNodes.add(children.get(i));
                

            }
        }
    }
    
}
