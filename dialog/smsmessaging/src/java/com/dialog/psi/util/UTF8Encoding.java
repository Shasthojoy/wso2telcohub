package com.dialog.psi.util;

import java.io.UnsupportedEncodingException;

import ie.omk.smpp.util.AlphabetEncoding;

public class UTF8Encoding extends AlphabetEncoding {

	private static final String ENCODING="UTF-8";
	private static final int DCS=8;
	
	public UTF8Encoding() throws UnsupportedEncodingException  {
		super(DCS);
		setCharset(ENCODING);
		//set
	}
	
	protected UTF8Encoding(int dcs) {
		super(dcs);
		//set
	}
}
