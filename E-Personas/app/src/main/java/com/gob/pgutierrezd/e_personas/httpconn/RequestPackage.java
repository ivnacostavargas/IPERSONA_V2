package com.gob.pgutierrezd.e_personas.httpconn;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pgutierrezd on 12/09/2016.
 */
public class RequestPackage {

    private String uri;
    private String method = "GET";
    private Map<String,String> params = new HashMap<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(String key, String value) {
        this.params.put(key,value);
    }

    public String getEncodeParams(){
        StringBuilder sb = new StringBuilder();
        for (String key: params.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
            if(sb.length() > 0){
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }


}
