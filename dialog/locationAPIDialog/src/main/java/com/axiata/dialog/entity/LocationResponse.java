/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tharanga_07219
 */
@XmlRootElement(name = "terminalLocationList")
public class LocationResponse {
    
    private TerminalLocationList terminalLocationList;
    
    public LocationResponse() {
    }


    public TerminalLocationList getOutboundSMSMessageRequest() {
            return terminalLocationList;
    }


    public void setTerminalLocationList(TerminalLocationList terminalLocationList) {
            this.terminalLocationList = terminalLocationList;
    }
    
}
