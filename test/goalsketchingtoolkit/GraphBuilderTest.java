/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.io.File;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import junit.framework.TestCase;
import org.w3c.dom.Document;

/**
 *
 * @author Chris
 */
public class GraphBuilderTest extends TestCase {

    public GraphBuilderTest(String testName) {
        super(testName);
    }

    /**
     * Test of build method, of class GraphBuilder.
     */
    public void testBuild() throws Exception {
        System.out.println("build()");
        
        Goal root = new Goal();
        root.setIsRootGoal(true);
        GSnodeGraphics gsng = new GSnodeGraphics(25,40,20,80);
        root.setGraphicalProperties(gsng);
        root.setID("GA");
        GoalOrientedProposition gop = new GoalOrientedProposition("Reputation concerns are satisfied.");
        gop.setPrefix(GoalType.MOTIVATION);
        gop.setContext("Scales and User");
        root.addChild(gop);
        root.setFit("T1");
        
        ANDentailment ae = new ANDentailment();
        GSentailmentGraphics aengs = new GSentailmentGraphics(25,40,20,80,60);
        ae.setGraphicalProperties(aengs);
        root.addChild(ae);
        
        Goal child1 = new Goal();
        child1.setGraphicalProperties(gsng);        
        child1.setID("GA1");
        GoalOrientedProposition gop2 = new GoalOrientedProposition("The standard WeighCom quality standard QS1 defines a satisfactory resolution of the reputation concerns.");
        gop2.setPrefix(GoalType.ASSUMPTION);
        AssumptionTermination t = new AssumptionTermination();
        t.setGraphicalProperties(gsng);
        child1.addChild(gop2);  
        child1.addChild(t);
        ae.addChild(child1);
        
        Goal child2 = new Goal();
        child2.setGraphicalProperties(gsng);
        child2.setID("GA2");
        GoalOrientedProposition gop3 = new GoalOrientedProposition("The working status of the Scales is monitored during normal operation.");
        gop3.setPrefix(GoalType.BEHAVIOUR);
        OperationalizingProducts ops = new OperationalizingProducts();
        ops.setGraphicalProperties(gsng);
        ops.addProduct("Scales");
        child2.addChild(gop3);    
        child2.addChild(ops);
        ae.addChild(child2);
        
        ConfidenceFactorRating cfr = new ConfidenceFactorRating(ConfidenceFactor.Refine, GSordinalScale.HIGH);
        ConfidenceFactorRating cfr2 = new ConfidenceFactorRating(ConfidenceFactor.Engage, GSordinalScale.HIGH);
        SignificanceFactorRating sfr = new SignificanceFactorRating(SignificanceFactor.Value, 100);
        GoalJudgement gj = new GoalJudgement(cfr, cfr2, sfr);
        
        ConfidenceFactorRating cfr3 = new ConfidenceFactorRating(ConfidenceFactor.Achieve, GSordinalScale.HIGH);
        SignificanceFactorRating sfr2 = new SignificanceFactorRating(SignificanceFactor.Cost, 100);
        LeafJudgement lj = new LeafJudgement(cfr3, sfr2);
        
        ConfidenceFactorRating cfr4 = new ConfidenceFactorRating(ConfidenceFactor.Assume, GSordinalScale.HIGH);
        AssumptionJudgement aj = new AssumptionJudgement(cfr4);
        
        Annotation a = new Annotation(gj);
        Annotation a2 = new Annotation(lj);
        Annotation a3 = new Annotation(aj);
        
        gop.addChild(a);
        gop2.addChild(a3);
        gop3.addChild(a2);
        
        GraphBuilder gb = new GraphBuilder(root);
        Document doc = gb.build();
        String fileName = "compositeTestXMLOutput.xml";

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
