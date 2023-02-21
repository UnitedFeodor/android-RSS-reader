package com.example.rss_reader.common;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HTTPDataHandler {
    static String stream = null;

    public HTTPDataHandler() {
    }

    public String GetHTTPData(String urlString) {

        try {
            URL url = new URL(urlString);
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            try (AutoCloseable conc = () -> urlConnection.disconnect()) {
                if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                    try (InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                         BufferedReader r = new BufferedReader(new InputStreamReader(in))) {


                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null)
                            sb.append(line);
                        stream = sb.toString();

                        //urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);

            }
        } catch(MalformedURLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return stream;


    }
}