package com.example.mkmkmk.projetmex.model;

import android.os.AsyncTask;

import com.example.mkmkmk.projetmex.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mkmkmk on 20/03/2018.
 */

//Dnas cette Clase va utiliser AsyncTask pour realiser des taches en deuxième plan
public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    //Attributs de la Classe
    String googlePlacesData;
    GoogleMap mMap;
    String url;
    String lieu="";

    //on commence quand la classe est executé et on realise les taches en deuxième plan
    @Override
    protected String doInBackground(Object... objects) {

        //on initialise les variables
        lieu = (String) objects[2];
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];


        //variable de type DownloadUrl
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            //on appelle au methode readUrl de la classe DownloadUrl avec l'url généré
            googlePlacesData = downloadUrl.readUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //on ajoute les nouveau valeur dans la variable avec les resultats
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        //on crée une list qui va obtenir les lieux prêts
        List<HashMap<String, String>> nearbyPlaceList = null;

        //on fait une variable de la Classe DataParser
        DataParser parser = new DataParser();
        //on va parser les donne reçu avec le methode parse() de la classe DataParser avec le variable
        nearbyPlaceList = parser.parse(s);
        //Pour finir et afficher les markers dans la carte on appelle le methode showNearbyPlace()
        showNearbyPlaces(nearbyPlaceList);
    }

    /**
     * Methode qui permet creer les markers de chaque lieu trouvé dans la carte
     * @param nearbyPlaceList
     */
    private void  showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
    {
        //on a besoin de faire un boucle qui va permettre faire tous les markers dans la carte
        for (int i = 0; i < nearbyPlaceList.size(); i++)
        {
            //on cree et initiale une variable MarkerOption qui a les methodes pour faire les markers
            MarkerOptions markerOptions = new MarkerOptions();

            //avec un variable List local, on ajoute la List du parametre avec un element qui correspond avec l'aide du boucle
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            //avec plus des variables locales, on les ajoute ce qu'il y a dans l'élément dans chaque champs
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            //on fait une variable Latlng qui permet de recevoir les coordonnees
            LatLng latLng = new LatLng(lat, lng);

            //avec la variable markerOption on utilise son methode position qui contiens les coordonnes
            markerOptions.position(latLng);
            //maintenant le methode title quand on touche le marker dans la carte
            markerOptions.title(placeName+" "+vicinity);

            //condition pour savoir que icon va etre afficher dans la carte selon le mot-clé
            if (lieu == "restaurant")
            {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant));
            }
            else if (lieu == "hospital")
            {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital));
            }
            else if (lieu == "museum")
            {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.museum));
            }
            else if (lieu == "hotel")
            {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel));
            }
            else
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }

            //Pour concluir on ajoute le marker en cours du boucle a la carte
            mMap.addMarker(markerOptions);
            //on fait un zoom dans la carte pour affiche les resultats
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(9));

        }//fermeture du boucle

        //on efface le mot/clé qu'on a reçu
        lieu ="";
    }




}
