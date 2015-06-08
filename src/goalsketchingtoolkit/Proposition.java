/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * This class describes general and assumption type goal oriented propositions.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class Proposition {

    private String prefix;
    private String statement;
    private String context;
    //private Annotation annotation;
    private boolean assumption;
    
    public Proposition() {
        
    }
    
    /**
     * Constructs a proposition.
     *
     * @param prefix the prefix of the proposition: GPr::= // | APr::= /a/
     * @param statement the statement (any alphanumeric string up to 255
     * characters.
     * @param assumption set to true if the proposition is of the assumption
     * type.
     */
    public Proposition(String prefix, String statement, boolean assumption) {
        
        this.prefix = prefix;
        this.statement = statement;
        this.assumption = assumption;
        
    }

    /**
     * Sets the prefix value.
     *
     * @param prefix the prefix to set.
     */
    public void setPrefix(String prefix) {
        
        this.prefix = prefix;

    }
    
    /**
     * Gets the prefix value.
     *
     * @return the prefix of this proposition.
     */
    public String getPrefix() {
        
        return prefix;

    }
    
    /**
     * Sets the statement value.
     *
     * @param statement the statement to set.
     */
    public void setStatement(String statement) {
        
        this.statement = statement;
        
    }    
    
    /**
     * Gets the statement value.
     *
     * @return the statement of this proposition.
     */
    public String getStatement() {
        
        return statement;

    }    
    
    /**
     * Sets a context value.
     *
     * @param context the statement to set.
     */
    public void setContext(String context) {
        
        this.context = context;
        
    }
    
    /**
     * Gets the context value.
     *
     * @return the context of this proposition.
     */
    public String getContext() {
        
        return context;

    }
    
     /**
     * Adds an annotation object to this proposition.
     *
     * @param annotation the statement to set.
     */
    //private void setAnnotation(Annotation annotation) {
        
       // this.annotation = annotation;
        
    //}
    
    /**
     * Gets the annotation value.
     *
     * @return the annotation of this proposition.
     */
    //private Annotation getAnnotation() {
        
     //   return annotation;

   // }
    
    public void setIsAssumption(boolean assumption) {
        
        this.assumption = assumption;
        
    }
    
    /**  
     * @return true if proposition is assumption, false otherwise
     */
    public boolean isAssumption() {
        
        return assumption;
        
    }
    
}
