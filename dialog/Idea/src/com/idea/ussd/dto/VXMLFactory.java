package com.idea.ussd.dto;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class VXMLFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public VXMLFactory() {
    }

    /**
     * Create an instance of {@link Vxml }
     * 
     */
    public Vxml createVxml() {
        return new Vxml();
    }

    /**
     * Create an instance of {@link Vxml.Form }
     * 
     */
    public Vxml.Form createVxmlForm() {
        return new Vxml.Form();
    }

    /**
     * Create an instance of {@link Vxml.Form.Block }
     * 
     */
    public Vxml.Form.Block createVxmlFormBlock() {
        return new Vxml.Form.Block();
    }

    /**
     * Create an instance of {@link Vxml.Form.Filled }
     * 
     */
    public Vxml.Form.Filled createVxmlFormFilled() {
        return new Vxml.Form.Filled();
    }

    /**
     * Create an instance of {@link Vxml.Form.Field }
     * 
     */
    public Vxml.Form.Field createVxmlFormField() {
        return new Vxml.Form.Field();
    }

    /**
     * Create an instance of {@link Vxml.Form.Block.Goto }
     * 
     */
    public Vxml.Form.Block.Goto createVxmlFormBlockGoto() {
        return new Vxml.Form.Block.Goto();
    }

    /**
     * Create an instance of {@link Vxml.Form.Filled.Assign }
     * 
     */
    public Vxml.Form.Filled.Assign createVxmlFormFilledAssign() {
        return new Vxml.Form.Filled.Assign();
    }

    /**
     * Create an instance of {@link Vxml.Form.Filled.Goto }
     * 
     */
    public Vxml.Form.Filled.Goto createVxmlFormFilledGoto() {
        return new Vxml.Form.Filled.Goto();
    }

}
