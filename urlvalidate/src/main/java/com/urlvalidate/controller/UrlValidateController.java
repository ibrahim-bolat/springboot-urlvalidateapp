package com.urlvalidate.controller;

import com.urlvalidate.dto.UrlValidateReguestDTO;
import com.urlvalidate.dto.UrlValidateResponseDTO;
import com.urlvalidate.model.UrlValidate;
import com.urlvalidate.service.UrlValidateService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"/", "/index"})
@CrossOrigin(origins = "*",  allowedHeaders = "*")
public class UrlValidateController {

    private final UrlValidateService urlValidateService;
    private List<UrlValidate> urls = new ArrayList<>();
    public UrlValidateController(UrlValidateService urlValidateService) {
        this.urlValidateService = urlValidateService;
    }

    @GetMapping
    public String index() {
        return "index";
    }
    @PostMapping("/urlvalidate")
    public ResponseEntity<UrlValidateResponseDTO> UrlValidate(@RequestBody UrlValidateReguestDTO urlValidateReguestDTO) {

        return ResponseEntity.ok(this.urlValidateService.getUrls(urlValidateReguestDTO));
    }
}
