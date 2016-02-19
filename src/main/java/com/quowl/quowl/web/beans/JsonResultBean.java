package com.quowl.quowl.web.beans;


import com.quowl.quowl.utils.ExecutionStatus;

import java.io.Serializable;

public class JsonResultBean implements Serializable {

    private boolean result = true;
    private Object data;
    private String error;

    public JsonResultBean() {
    }

    public JsonResultBean(ExecutionStatus status) {
        if(status != ExecutionStatus.OK) {
            result = false;
            error = status.toString();
        }
    }

    public static JsonResultBean success() {
        return new JsonResultBean();
    }

    public static JsonResultBean success(Object data) {
        JsonResultBean bean = new JsonResultBean();
        bean.data = data;
        return bean;
    }

    public static JsonResultBean failure(String msg) {
        msg = msg.replaceAll("_", " ");
        JsonResultBean bean = new JsonResultBean();
        bean.result =  false;
        bean.error = msg;
        return bean;
    }

    public static JsonResultBean failure(String msg, Object data) {
        JsonResultBean bean = new JsonResultBean();
        bean.result =  false;
        bean.error = msg;
        bean.data = data;
        return bean;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}
