package com.urlvalidate.model;

import java.util.List;

public class UrlValidate {
    private int id;
    private String urlString;
    private boolean isvalid;
    private List<String> errors;

    public UrlValidate() {

    }

    public UrlValidate(int id, String urlString, boolean isvalid, List<String> errors) {
        this.id = id;
        this.urlString = urlString;
        this.isvalid = isvalid;
        this.errors = errors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "UrlValidate{" +
                "id=" + id +
                ", urlString='" + urlString + '\'' +
                ", isvalid=" + isvalid +
                ", errors=" + errors +
                '}';
    }
}
