package com.urlvalidate.service;

import com.urlvalidate.dto.UrlValidateReguestDTO;
import com.urlvalidate.dto.UrlValidateResponseDTO;
import com.urlvalidate.model.UrlValidate;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UrlValidateService {

    private List<UrlValidate> urls = new ArrayList<>();

    public UrlValidateResponseDTO getUrls(UrlValidateReguestDTO urlValidateReguestDTO){

        List<String> newUrls = urlSplit(urlValidateReguestDTO.getUrls());
        List<UrlValidate> urls = urlValidate(newUrls);
        UrlValidateResponseDTO urlValidateResponseDTO = new UrlValidateResponseDTO(urls);

       return urlValidateResponseDTO;
    }

    public List<String> urlSplit(String urls) {
        List<String> newUrls = Arrays.asList(urls.split("[\\r\\n]+"));
        List<String> lastNewUrls = new ArrayList<>();
        for(String url:newUrls){
            if(url.trim().length() > 0){
                lastNewUrls.add(url.trim());
            }
        }
        return lastNewUrls;
    }
    public List<UrlValidate> urlValidate(List<String> urls) {
        List<UrlValidate> newUrls = new ArrayList<>();
        if(urls!=null){
            int urlCount = 1;
            for(String url: urls){
                UrlValidate urlValidate = new UrlValidate();
                // String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
                // UrlValidator urlValidator = new UrlValidator(schemes);
                if (isValidUrl(url.trim())) {
                    urlValidate.setId(urlCount);
                    urlValidate.setUrlString(url.trim());
                    urlValidate.setIsvalid(true);
                    urlValidate.setErrors(null);
                } else {
                    urlValidate.setId(urlCount);
                    urlValidate.setUrlString(url.trim());
                    urlValidate.setIsvalid(false);
                    urlValidate.setErrors(errorsControl(url.trim()));
                }
                newUrls.add(urlValidate);
                urlCount++;
            }
            return newUrls;
        }
        return null;

    }
    public boolean isValidUrl(String url) {
        try {

            if(url.startsWith("http://") || url.startsWith("https://")) {
                URL _url = new URL(url);
                _url.toURI();

                String hostPart = _url.getProtocol() + "://" + _url.getAuthority() + "/";
                String pathPart = _url.getFile() == null ? "" : _url.getFile();
                pathPart += _url.getRef() == null ? "" : _url.getRef();

                return new UrlValidator().isValid(hostPart + URLEncoder.encode(pathPart,"UTF-8"));
            }
            else {
                return false;
            }
        } catch (URISyntaxException | MalformedURLException | UnsupportedEncodingException e) {
            return false;
        }
    }
    public boolean isAllValidUrl(String[] urls) {

        boolean valid = false;
        for (String url : urls) {
            if(isValidUrl(url.trim()))
                valid = true;
            else
                return false;
        }
        return valid;
    }
    public List<String> errorsControl(String url){
        List<String> errors = new ArrayList<>();
        String protocolErrors=null,syntaxErrors=null, tldErrors=null;
        protocolErrors = protocolErrorsControl(url);
        if(protocolErrors!=null){
            errors.add(protocolErrors);
        }
        if(protocolErrors==null) {
            syntaxErrors = syntaxErrorsControl(url);
            if (syntaxErrors != null) {
                errors.add(syntaxErrors);
            }
        }
        if(protocolErrors==null && syntaxErrors==null){
            tldErrors = tldErrorsControl(url);
            if(tldErrors!=null){
                errors.add(tldErrors);
            }
        }
        return errors;
    }

    public String protocolErrorsControl(String url){
        if(url.startsWith("http://") || url.startsWith("https://"))
            return null;
        return "Urlde Protokol eksik yada yanlış. Url nin başına http:// yada  https:// ekleyiniz.";
    }

    public String syntaxErrorsControl(String url){
        try {
            URL _url = new URL(url);
            _url.toURI();
        } catch (URISyntaxException e) {
            return "Urlde "+ e.getIndex()+". karakterde ("+ url.charAt(e.getIndex())+") Syntax (SözDizimi) hatası var";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String tldErrorsControl(String url) {
        try {
            DomainValidator domainValidator = DomainValidator.getInstance();
            URL _url = new URL(url);
            _url.toURI();
            System.out.println(_url.getHost());
            String[] domainNameParts =  _url.getHost().split("\\.");
            String tld = domainNameParts[domainNameParts.length-1];
            if(domainNameParts[0].startsWith("www")){
                if(domainNameParts.length>2){
                    if(!domainValidator.isValidTld(tld)){
                        return "Urlde TLD ("+ tld +") Hatalıdır";
                    }
                }
            }
            else {
                if(domainNameParts.length>1){
                    if(!domainValidator.isValidTld(tld)){
                        return "Urlde TLD ("+ tld +") Hatalıdır";
                    }
                }
            }
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
    public String invalidCharactersErrorsControl(String url){
        String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~:/?#[]@!$&'()*+,;=%";
        for(int i=0; i<url.length(); i++){
            if(validCharacters.indexOf(url.charAt(i))==-1){
                return "Urlde "+i+".karakter ("+url.charAt(i)+") hatalı olabilir";
            }
        }
        return null;
    }
    */
    /*
    public String codingErrorsControl(String url){
        String regex = "(?i)%(?![\\da-f]{2})";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        boolean matchFound = matcher.find();
        if(matchFound) {
            int start = matcher.start();
            int end = matcher.end();
            return "Hatalı Karakter Başlangıcı "+start+" Hatalı Karakter Bitişi "+end;
        }
        return null;
    }
   */
}
