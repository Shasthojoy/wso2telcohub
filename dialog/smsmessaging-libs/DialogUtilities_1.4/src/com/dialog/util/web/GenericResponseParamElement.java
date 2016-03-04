package com.dialog.util.web;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Title		: DialogUtilities_1.4	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Mar 17, 2009
 * @author 		: charith
 * Comments		: 
 */
public class GenericResponseParamElement {
    
    private String m_name         = "";
    private String m_value        = "";
    private Hashtable m_attrs     = null;
    private ArrayList m_children  = null;
    
    public GenericResponseParamElement() {
    }

    /**
     * 
     * @param name
     * @param value
     */
    public GenericResponseParamElement(String name, String value) {
        this.m_name  = name;
        this.m_value = value;
    }
    /**
     * 
     * @param name
     * @param value
     * @param attrTable
     * @param children
     */
    public GenericResponseParamElement(String name, String value, Hashtable attrTable, ArrayList children) {
        this.m_name     = name;
        this.m_value    = value;
        this.m_attrs    = attrTable;
        this.m_children = children;
    }

    /**
     * @return the m_name
     */
    public String getName() {
        return m_name;
    }

    /**
     * @param m_name the m_name to set
     */
    public void setName(String name) {
        this.m_name = name;
    }

    /**
     * @return the m_value
     */
    public String getValue() {
        return m_value;
    }

    /**
     * @param m_value the m_value to set
     */
    public void setValue(String value) {
        this.m_value = value;
    }

    /**
     * @return the m_attrs
     */
    public Hashtable getAttrs() {
        return m_attrs;
    }

    /**
     * @param m_attrs the m_attrs to set
     */
    public void setAttrs(Hashtable attrs) {
        this.m_attrs = attrs;
    }

    /**
     * @return the m_children
     */
    public ArrayList getChildren() {
        return m_children;
    }

    /**
     * @param m_children the m_children to set
     */
    public void setChildren(ArrayList children) {
        this.m_children = children;
    }
    /**
     * 
     */
    public String toString() {
        return m_name+","+m_value+","+(m_attrs==null?"":m_attrs.toString())+","+(m_children==null?"":m_children.toString());
    }    
}

