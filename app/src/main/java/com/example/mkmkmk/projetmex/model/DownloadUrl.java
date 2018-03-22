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
        //variables pour realiser la conection
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            //variable URL qui contiens l'url
            URL url = new URL(myUrl);
            //on realise la connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            //on cree le fil avec la connection realisé
            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer sb = new StringBuffer();
            String line = "";

            //boucle qui permet recevoir tout l'information doné tandis qu'il y a dans l'API
            while ((line = br.readLine()) != null)
            {
                //on ajoute tout ce qu'il y a
                sb.append(line);
            }
            //finalment on l'ajoute dans une variable
            data = sb.toString();

            //on ferme le fil avec la connection
            br.close();

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            //Il faut toujour fermer les connection reallisé
            inputStream.close();
            urlConnection.disconnect();
        }

        Log.i("DEBUG downloadURL", data);
        //on envoie l'info
        return data;
    }


}
