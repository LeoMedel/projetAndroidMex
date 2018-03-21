package com.example.mkmkmk.projetmex.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mkmkmk on 20/03/2018.
 */

public class DownloadUrl {

    /**
     * Methode qui permet de lire l'url
     * @param myUrl
     * @return
     * @throws IOException
     */
    public String readUrl(String myUrl) throws IOException
    {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer sb = new StringBuffer();
            String line = "";

            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            inputStream.close();
            urlConnection.disconnect();
        }

        Log.i("DEBUG downloadURL", data);
        return data;
    }


}
