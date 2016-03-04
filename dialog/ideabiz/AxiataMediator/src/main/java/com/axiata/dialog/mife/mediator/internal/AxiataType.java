/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.mife.mediator.internal;

/**
 *
 * @author roshan_06809
 */
public enum AxiataType {
        SMSSEND("SM"), SMSRETRIVE("RM"), RETRIVSUB("RS"), DELRETSUB("DS"), PAYMENT("PA"), ALERTINBOUND("AI"), LOCREQ("LR");
        private String code;

        private AxiataType(String value) {
                this.code = value;
        }
        public String getCode() {
            return code;
        }
             
};
