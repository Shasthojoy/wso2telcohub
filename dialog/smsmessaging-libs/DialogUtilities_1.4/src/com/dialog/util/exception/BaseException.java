package com.dialog.util.exception;

/**
 * 
 * Title		: DialogUtilities_1.4	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Mar 11, 2009
 * @author 		: charith
 * Comments		:
 */
public class BaseException extends Exception {
    
    private String m_errorCode;
    private String m_source;
    private String m_backURL;
    
    /**
     * 
     * @param errorCode
     * @param e
     */
    public BaseException(String errorCode,Exception e){      
        super(e);
        this.m_errorCode = errorCode;
    }

    /**
     * 
     * @param errorCode
     * @param backURL
     * @param e
     */
    public BaseException(String errorCode, String backURL, Exception e){      
        super(e);
        this.m_errorCode = errorCode;
        this.m_backURL   = backURL;
    }
    
    /**
     * 
     * @param errorCode
     * @param source
     * @param backURL
     * @param e
     */
    public BaseException(String errorCode, String source, String backURL, Exception e){      
        super(e);
        this.m_errorCode = errorCode;
        this.m_backURL   = backURL;
        this.m_source    = source;
    }
    
    public String getMessage() {
        return SystemError.getErrorMessage(m_errorCode);        
    }
    
    public String getErrorCode() {
        return m_errorCode;
    }
    
    public String getSource() {
        return m_source;
    }

    public String getBackURL() {
        return m_backURL;
    }
    /**
     * @return
     * String Parent Exception's Message.
     */
    public String getParentMessage() {      
        return this.getCause() != null ? this.getCause().getMessage() : getMessage();
    }
}

