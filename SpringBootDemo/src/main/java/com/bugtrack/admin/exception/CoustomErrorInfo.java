package com.bugtrack.admin.exception;
/**
 * @author Baoxing Li
 * @version 1.0.0
 * A self-define class for error message
 * @see GlobalControllerExceptionHandler
 */
public class CoustomErrorInfo<T> {

    public static final Integer OK = 0;
    public static final Integer ERROR = 100;

    private Integer code;
    private String message;
    private String url;
    private T data;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Integer getOK() {
        return OK;
    }

    public static Integer getERROR() {
        return ERROR;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
    	if(message==null){
    		this.message = "xxx";
    	}else{
    		this.message = message;
    	}
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
}