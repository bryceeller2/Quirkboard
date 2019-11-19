package com.example.ckraft.myapplication.Controller.WebRequests;

import java.util.HashMap;

/**
 * Created by ckraft on 8/29/2016.
 */
public class BaseServiceSettings {

    private String method;
    private String action;
    private HashMap<String, String> headers;
    private String body;

    public BaseServiceSettings() {
        this.headers = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
