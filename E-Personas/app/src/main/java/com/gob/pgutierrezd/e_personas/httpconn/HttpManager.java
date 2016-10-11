package com.gob.pgutierrezd.e_personas.httpconn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pgutierrezd on 12/09/2016.
 */
public class HttpManager {

    public static String getData(RequestPackage requestPackage){
        BufferedReader bufferedReader = null;
        String link = requestPackage.getUri();
        if(requestPackage.getMethod().equals("GET")){
            link += "?" + requestPackage.getEncodeParams();
        }

        try {
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestPackage.getMethod());
            if(requestPackage.getMethod().equals("POST")){
                con.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
                outputStreamWriter.write(requestPackage.getEncodeParams());
                outputStreamWriter.flush();
            }
            StringBuilder stringBuilder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line+"\n");
            }
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            return stringBuilder.toString();
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

}
