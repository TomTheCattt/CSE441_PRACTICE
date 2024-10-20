package com.example.ex22;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLParser {
    public String getXmlFromUrl(String urlString) {
        String xml = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);  // 10 seconds timeout
            connection.setReadTimeout(10000);

            connection.connect();
            InputStream inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            xml = stringBuilder.toString();

            reader.close();
            inputStream.close();
            connection.disconnect();
        } catch (Exception e) {
            Log.e("Error", "Exception: " + e.getMessage());
            return null;
        }
        return xml;
    }
}

