package com.axiata.dialog.mss;

import com.axiata.dialog.model.MSSRequest;
import com.axiata.dialog.model.MSSResponse;

/**
 * Created by nilan on 11/27/14.
 */
public interface MSSSoapWebService {

  public MSSResponse signatureService(MSSRequest mssRequest);

}
