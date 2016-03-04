package com.idea.ussd.test;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.idea.ussd.api.UssdAPI;
import com.idea.ussd.dto.VXMLFactory;
import com.idea.ussd.dto.Vxml;
import com.idea.ussd.dto.Vxml.Form;
import com.idea.ussd.dto.Vxml.Form.Block;
import com.idea.ussd.dto.Vxml.Form.Field;
import com.idea.ussd.dto.Vxml.Form.Filled;
import com.idea.ussd.dto.Vxml.Form.Filled.Assign;
import com.idea.ussd.dto.Vxml.Form.Filled.Goto;

public class Test1 {

	public static void main(String[] args) {
		new Test1();

		try {
			Properties settings = new Properties();
			settings.loadFromXML(UssdAPI.class.getResourceAsStream("settings.xml"));
			String pattern = settings.getProperty("ussd_ni_match");

			String input = "mtinit:123:42";
			boolean matched = input.matches("^(mtinit|mtcont|moinit|mocont|mtfin)[:][\\d]{3}[:][\\d]+$");
			System.out.println(matched);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//System.out.println(input.split(":"));
		
		/*String input = "mAs name 15";
		boolean matched = input.matches("^(?i)(MAS)(?i)[\\s][a-zA-Z0-9_-]{1,}[\\s](1[0-5])$");
		System.out.println(matched);*/
	}
	
	public Test1() {
		String msisdn = "94777335365";
		
		VXMLFactory factory = new VXMLFactory();
		Vxml vxml = factory.createVxml();
		
		//<field name="oc_message">
		//	<prompt>Hello 94777335365!\n1. Continue\n2. Exit</prompt>
		//</field>
		Field field = factory.createVxmlFormField();
		field.setName("oc_message");
		field.setPrompt("Hello "+msisdn+"!\n1. Continue\n2. Exit");
		
		//<filled>
		//	<assign name="vResponse" expr="oc_message"/>
		//	<goto next="#responseMsg"/>
		//</filled>
		Assign assign = factory.createVxmlFormFilledAssign();
		assign.setName("vResponse");
		assign.setExpr("oc_message");
		Goto gotoElem = factory.createVxmlFormFilledGoto();
		gotoElem.setNext("#responseMsg");			
		Filled filled = factory.createVxmlFormFilled();
		filled.setAssign(assign);
		filled.setGoto(gotoElem);
		
		//<form id="MenuID Here" name="Menu Name Here">
		//	<field/>
		//	<filled/>
		//</form>
		Form form1 = factory.createVxmlForm();
		form1.setId("MainMenu");
		form1.setName("Main Menu");
		form1.setField(field);
		form1.setFilled(filled);
		
		//<block name="oc_ActionUrl">
		//	<goto next="http://172.22.163.88:8080/ussd/mo-cont/94777335365?resp=%vResponse%" />
		//</block>
		com.idea.ussd.dto.Vxml.Form.Block.Goto blockGoto = factory.createVxmlFormBlockGoto();
		blockGoto.setNext("http://172.22.163.88:8080/ussd/mo-cont/"+msisdn+"?resp=%vResponse%&"+System.nanoTime());
		Block block = factory.createVxmlFormBlock();
		block.setName("oc_ActionUrl");
		block.setGoto(blockGoto);
		
		//<form id="responseMsg" name="responseMsg">
		//<block/>
		//</form>
		Form form2 = factory.createVxmlForm();
		form2.setBlock(block);
		
		List<Form> formList = vxml.getForm();
		formList.add(0, form1);
		formList.add(1, form2);
		
		try {
			JAXBContext context = JAXBContext.newInstance(Vxml.class);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

			StringWriter writer = new StringWriter();
			m.marshal(vxml, writer);
			
			System.out.println(writer.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
