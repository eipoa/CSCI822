package com.bugtrack.exception;
/**
 * @author Baoxing Li
 * @version 1.0.0
 * A self-define class for error message
 * @see GlobalControllerExceptionHandler
 */
public class CustomErrorInfo {

    public static final Integer OK = 0;
    public static final Integer ERROR = 100;

    private Integer code;
    private String message;
    private String url;
    private String data;

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
    		this.message = "no message";
    	}else{
    		this.message = message;
    	}
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    
}