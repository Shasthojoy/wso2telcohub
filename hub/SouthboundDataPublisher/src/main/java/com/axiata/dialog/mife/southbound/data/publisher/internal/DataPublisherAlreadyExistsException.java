package com.axiata.dialog.mife.southbound.data.publisher.internal;


public class DataPublisherAlreadyExistsException extends Exception {

    public DataPublisherAlreadyExistsException(String msg) {
        super(msg);
    }

    public DataPublisherAlreadyExistsException(String msg, Throwable e) {
        super(msg, e);
    }

    public DataPublisherAlreadyExistsException(Throwable throwable) {
        super(throwable);
    }
}
