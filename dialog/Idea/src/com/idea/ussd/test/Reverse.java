package com.idea.ussd.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import com.idea.ussd.dto.Vxml;

public class Reverse {

	public Reverse() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Vxml.class);
			SchemaOutputResolver sor = new MySchemaOutputResolver();
			jaxbContext.generateSchema(sor);
			
			Result res = sor.createOutput(null, "E:/workspace/vxml.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class MySchemaOutputResolver extends SchemaOutputResolver {

	    public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
	        File file = new File(suggestedFileName);
	        StreamResult result = new StreamResult(file);
	        //result.setSystemId(file.toURI().toURL().toString());
	        return result;
	    }

	}
	
	public static void main(String[] args) {
		new Reverse();
	}
}
