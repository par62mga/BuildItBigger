package com.pkrobertson.jokeserver;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myRequestName;
    private String myResponseData;

    public String getRequestName() {
        return myRequestName;
    }

    public void setrequestName (String name) {
        myRequestName = name;
    }

    public String getResponseData() {
        return myResponseData;
    }

    public void setResponseData(String data) {
        myResponseData = data;
    }
}