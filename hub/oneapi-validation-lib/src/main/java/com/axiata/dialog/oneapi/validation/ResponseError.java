package com.axiata.dialog.oneapi.validation;

public class ResponseError {

    private RequestError requestError;

    public RequestError getRequestError() {
        return requestError;
    }

    public void setRequestError(RequestError requestError) {
        this.requestError = requestError;
    }
}
