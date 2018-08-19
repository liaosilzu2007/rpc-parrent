package com.lzumetal.myrpc.common.bean;

/**
 * @author liaosi
 * @date 2018-08-18
 */
public class RpcResponse {

    private String requestId;

    private Exception exception;

    private Object result;


    public boolean hasException() {
        return this.exception != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
