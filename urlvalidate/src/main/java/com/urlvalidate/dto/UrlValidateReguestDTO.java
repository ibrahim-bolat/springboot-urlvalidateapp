package com.urlvalidate.dto;

public class UrlValidateReguestDTO {
    private String urls;

    public UrlValidateReguestDTO() {

    }

    public UrlValidateReguestDTO(String urls) {
        this.urls = urls;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}
