package com.example.mkmkmk.projetmex.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Dans cette classe on va parse les resultats de la requete pour creer la liste des lieux
 *
 */
public class DataParser {

    public List<HashMap<String, String>> parse(String jsonData)
    {
        //variables qu'on a besion pour obtenir les donées parsés
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            //on initialise la variable de type JSONObject avec la variable jsonData du parametre pour parser
            jsonObject = new JSONObject(jsonData);

            //on ajoute le données dans le jsonObject avec le type JSONArray avec la propiete "resultats" qui nous donne acces aux lieux
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // nous renvoyons la liste avec le resultat du methode avec la variable avec l'information déjà parsé
        return getPlaces(jsonArray);
    }

    /**
     *  Methode pour créer la liste déjà remplis avec la bonne façon
     * @param jsonArray on a besion pour crée la liste avec la bonne information
     * @return une List avec les lieux et leurs information
     */
    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray)
    {
        // variable avec le numero de lieux qu'on a reçu;
        int count = jsonArray.length();
        //Liste qu'on va renvoyer
        List<HashMap<String, String>> placeList = new ArrayList<>();
        //Variable HashMap
        HashMap<String, String> placeMap = null;

        //boucle pour ajouter l'information bien formé
        for (int i = 0; i < count; i++)
        {
            try {
                //on ajoute a la List le resultat d'appeller le methode getPlace() avec chaque element correspondant dans le boucle
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                //en ajoutant chaque element bien formé finalment a la List qu'on va renvoyer
                placeList.add(placeMap);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Finalement on renvoie la List final qu'on pourrai utiliser
        return placeList;
    }

    /**
     * Methode qui permet de former la List avec l'information reçu de chaque champ
     * @param googlePlaceJson
     * @return une List bien formé
     */
    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
    {
        //variable qu'on va envoyer bien formé et pouvoir acceder mieux a leur information
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        //variable de chaque champs, que selon la Documentation, on sait ce qu'il y a quand on a fait la requete
        String placeName = "-NA-";
        String vicinity ="-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            //condition pour savoir si le champs es vide
            if (!googlePlaceJson.isNull("name"))
            {
                //on ajoute le resultat qu'il y a dans la variable du parametre avec la propiété "name"
                placeName = googlePlaceJson.getString("name");
            }
            //Meme fonction, mais avec la propiété "vicinity"
            if (!googlePlaceJson.isNull("vicinity"))
            {
                vicinity = googlePlaceJson.getString("vicinity");
            }

            //on ajoute le resulta de chaque demande a nos variables
            //on ajoute la latitude
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            //on ajoute la longitud
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            //et on ajoute la reference
            reference = googlePlaceJson.getString("reference");

            //pour finir on ajoute tout le valeur de nos variables déjà remplis a la liste final
            //on ajoute chaque variable avec un mot-clé pour acceder a son valuer
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //on renvoie la liste déjà forme et prêt pour l'implementer
        return googlePlaceMap;
    }
}
