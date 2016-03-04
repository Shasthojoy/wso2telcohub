/**
 * 
 */
package com.dialog.airtel.ussd.test;

/**
 * @author Charith_02380
 *
 */
public class Test {

	/**
	 * 
	 */
	public Test() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String msisdn = "777335365";
		String xml = 
				"<vxml>"+
				"	<form id=\"message\" name=\"message\">"+
				"	    <field name=\"oc_message\">"+
				"	      <prompt>Hello "+msisdn+"!\n1. Continue\n2. Exit</prompt>"+
				"	    </field>"+
				"	    <filled>"+	     
				"		  <assign name=\"vResponse\" expr=\"oc_message\"/>"+
				"	      <goto next=\"#responseMsg\"/>"+
				"	    </filled>"+
				"	    <catch event=\"nomatch\">"+
				"	      <prompt>Incorrect response</prompt>"+
				"	      <goto next=\"#message\"/>"+
				"	    </catch>"+
				"	    <catch event=\"noinput\">"+
				"			<prompt>Incorrect response</prompt>"+
				"			<goto next=\"#message\"/>"+
				"		</catch>"+
				"	    <property name=\"oc_bHasBack\" value=\"0\"/>"+
				" 	</form>"+  
				"  	<form id=\"responseMsg\" name=\"responseMsg\">"+
				"		<block name=\"oc_ActionUrl\">"+
				"			<goto next=\"http://172.22.163.88:8080/ussd/mo-cont/"+msisdn+"?resp=%vResponse%\"/>"+
				"		</block>"+
				"	</form>"+
				"</vxml>";
		
		System.out.println(xml);
	}

}
