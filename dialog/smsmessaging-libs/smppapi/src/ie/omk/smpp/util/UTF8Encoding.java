package ie.omk.smpp.util;

import java.io.UnsupportedEncodingException;

public class UTF8Encoding extends ie.omk.smpp.util.AlphabetEncoding {
    private static final int DCS = 8;

    /**
     * Construct a new UTF8 encoding.
     * 
     */
    public UTF8Encoding() throws UnsupportedEncodingException {
        super(DCS);
        setCharset("UTF-8");
    }
}

