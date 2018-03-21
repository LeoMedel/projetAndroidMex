package com.example.mkmkmk.projetmex.model;

import android.os.AsyncTask;

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

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    String lieu="";

    @Override
    protected String doInBackground(Object... objects) {

        //on initialise les variables
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        lieu = (String) objects[2];

        //variable de type DownloadUrl
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            //on appelle au methode readUrl de la classe DownloadUrl
            googlePlacesData = downloadUrl.readUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //ajouter les nouveau valeur dans la variable
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList = null;

        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }

    /**
     * Methode pour creer les markers de chaque lieu trouvé dans la carte
     * @param nearbyPlaceList
     */
    private void  showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
    {
        for (int i = 0; i < nearbyPlaceList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlPlace = nearbyPlaceList.get(i);

            String placeName = googlPlace.get("place_name");
            String vicinity = googlPlace.get("vicinity");
            double lat = Double.parseDouble(googlPlace.get("lat"));
            double lng = Double.parseDouble(googlPlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName+" "+vicinity);

            if (lieu == "Restaurant")
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            }
            else if (lieu == "Hospital")
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }
            else if (lieu == "Museo")
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }
            else if (lieu == "Hotel")
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            }
            else
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            }

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }
    }




}
