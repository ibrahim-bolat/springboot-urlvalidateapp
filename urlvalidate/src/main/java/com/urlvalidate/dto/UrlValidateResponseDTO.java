package com.urlvalidate.dto;

import com.urlvalidate.model.UrlValidate;

import java.util.ArrayList;
import java.util.List;

public class UrlValidateResponseDTO {
    private List<UrlValidate> urlValidates = new ArrayList<>();

    public UrlValidateResponseDTO() {
    }

    public UrlValidateResponseDTO(List<UrlValidate> urlValidates) {
        this.urlValidates = urlValidates;
    }

    public List<UrlValidate> getUrlValidates() {
        return urlValidates;
    }

    public void setUrlValidates(List<UrlValidate> urlValidates) {
        this.urlValidates = urlValidates;
    }
}
