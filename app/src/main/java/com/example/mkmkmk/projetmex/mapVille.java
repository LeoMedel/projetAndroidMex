package com.example.mkmkmk.projetmex;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mkmkmk.projetmex.model.GetNearbyPlacesData;
import com.example.mkmkmk.projetmex.model.Villes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**@author Leonel MEDEL et Emanuel YAH
 *
 */
public class mapVille extends FragmentActivity implements OnMapReadyCallback {

    //Attributs de la classe
    private GoogleMap mMap;
    private Marker markerUbication;
    private TextView txt_Titre;
    private TextView txt_Info;
    private Button btn_Restaurant;
    private Button btn_Musee;
    private Button btn_Hospital;
    private Button btn_Hotel;
    private Button btn_Ubication;
    private ImageView imge;
    private Bitmap loadedImage;
    private Villes vil;
    private double latitude = 0.0;
    private double longitude =0.0;
    String[] ville;

    /**Methode qui crée l'interface
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_ville);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //nous creons un intent pour recevoiir l'info
        Intent getIntent = getIntent();
        //on ajoute l'info dans un array
        ville = getIntent.getStringArrayExtra("ville");

        //on fait la relation entre les éléments de la vue
        txt_Titre = (TextView) findViewById(R.id.txtTitre);
        txt_Info = (TextView) findViewById(R.id.txtInfo);
        btn_Restaurant = (Button) findViewById(R.id.btnRestaurant);
        btn_Musee = (Button) findViewById(R.id.btnMusee);
        btn_Hospital = (Button) findViewById(R.id.btnHospital);
        btn_Hotel = (Button) findViewById(R.id.btnHotel);
        btn_Ubication = (Button) findViewById(R.id.btnUbication);
        imge = (ImageView) findViewById(R.id.villeIMG);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //on affiche la carte de type hibride
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //on appelle cette methode pour affiche la carte avec notre ubication
        monUbication();
        //on appelle cette methode pour montrer la ville selectoné
        afficherVille(ville[0], ville[1]);
    }

    /**
     * Methode qui permet afficher notre Ubication
     */
    private void monUbication() {

        //Permission de l'aplication pour pouvoir utiliser l'ubication du dispositif
        //apres de l'activer et autoriser dans Configuration du dispositif
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        updateUbication(location);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        //S'il y a un change d'ubication il va faire un mis a jour
        @Override
        public void onLocationChanged(Location location) {
            updateUbication(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    /**
     * Methode qui fait seulement le marker de notre ubication
     * @param lat notre latitude
     * @param lng notre longitude
     */
    private void ajouterMarker(double lat, double lng) {
        LatLng coordonees = new LatLng(lat, lng);

        if (markerUbication != null) {
            markerUbication.remove();
        }
        markerUbication = mMap.addMarker(new MarkerOptions()
                .position(coordonees)
                .title("Vous êtes ici")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.taquito)));
    }

    /**
     * Methode qui permet faire mis ajour notre ubication
     */
    private void updateUbication(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //on appelle le methode pour afficher notre nouvelle ubication
            ajouterMarker(latitude, longitude);
        }
    }

    /**
     * Methode qui permet afficher la distance entre la ville et notre ubication
     * @param view
     */
    public void setUbication(View view)
    {
        //on efface tous les markers dans la carte
        mMap.clear();

        //on affiche notre ubication
        monUbication();

        //on va faire un demande a la BD pour recuperer les coordonnees de la ville
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference villesRef = firebaseDatabase.getReference("Mexico");

        villesRef.child("Zone").child(ville[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot inf = dataSnapshot.child(ville[1]);
                Villes villeChoisi = inf.getValue(Villes.class);

                vil = new Villes(villeChoisi.getImg(), villeChoisi.getInfo(), villeChoisi.getLat(), villeChoisi.getLng(), villeChoisi.getVille());
                //on fait le marker de la ville
                LatLng markerVille = new LatLng(vil.getLat(), vil.getLng());
                mMap.addMarker(new MarkerOptions().position(markerVille).title("Ici c'est "+vil.getVille()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ubicamex)));

                //variables de type Location avec les coordonnes correspondant
                Location l = new Location("Location 1");
                l.setLatitude(vil.getLat());
                l.setLongitude(vil.getLng());
                Location l2 = new Location("Location 2");
                l2.setLatitude(latitude);
                l2.setLongitude(longitude);

                //on obtiens la distance
                double distanceM = l.distanceTo(l2);
                double distanceK = distanceM/1000;

                //finalement, on va desiner la ligne dans la carte
                Polyline line = mMap.addPolyline( new PolylineOptions()
                        .add(new LatLng(vil.getLat(),vil.getLng()), new LatLng(latitude,longitude))
                        .width(5)
                        .color(Color.RED));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(1.0f ));
                Toast.makeText(getApplicationContext(), "Il y a une distance de "+distanceK+" Km ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    /**
     * Methodes qui permettent d'afficher les Restaurant ("setResturant()"), Musees("setMusee()"),
     * Hopitals("setHopital()") et les Hotels("setHotel()")
     * @param view
     */

    public void setRestaurant(View view)
    {
        //on efface tous les markers dans la carte
        mMap.clear();
        //on fait la conexion avec la base de donnes de Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference villesRef = firebaseDatabase.getReference("Mexico");

        villesRef.child("Zone").child(ville[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot inf = dataSnapshot.child(ville[1]);
                Villes villeChoisi = inf.getValue(Villes.class);

                vil = new Villes(villeChoisi.getImg(), villeChoisi.getInfo(), villeChoisi.getLat(), villeChoisi.getLng(), villeChoisi.getVille());

                //on fait une variable de type GetNearbyPlacesData.
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                //on fait une variable Object qui va garder l'info qu'on a besion pour pouuvoir montrer les lieu correct
                Object dataTransfer[] = new Object[3];

                String restaurant = "restaurant";
                //on cree l'url avec laquelle on va faire la requete ave une API de google Places
                String url = getUrl(vil.getLat(), vil.getLng(), restaurant);
                //on ajouet la carte
                dataTransfer[0] = mMap;
                //on ajouter l'url généré
                dataTransfer[1] = url;
                //finalement, on ajoute le Mot-clé
                dataTransfer[2] = restaurant;

                //on appelle a la methode de la clase GetNearbyPlacesData.
                getNearbyPlacesData.execute(dataTransfer);

                Toast.makeText(getApplicationContext(), "Show Restaurant", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //C'est un methode different avec la meme instruction qui affiche Musees
    public void setMusee(View view)
    {
        mMap.clear();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference villesRef = firebaseDatabase.getReference("Mexico");

        villesRef.child("Zone").child(ville[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot inf = dataSnapshot.child(ville[1]);
                Villes villeChoisi = inf.getValue(Villes.class);

                vil = new Villes(villeChoisi.getImg(), villeChoisi.getInfo(), villeChoisi.getLat(), villeChoisi.getLng(), villeChoisi.getVille());

                GetNearbyPlacesData getNerabyPlacesData = new GetNearbyPlacesData();
                Object dataTransfer[] = new Object[3];

                String musee = "museum";
                String url = getUrl(vil.getLat(), vil.getLng(), musee);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = musee;

                getNerabyPlacesData.execute(dataTransfer);
                Toast.makeText(getApplicationContext(), "Show Museum", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //C'est un methode different avec la meme instruction qui affiche Hopitals
    public void setHopital(View view)
    {
        mMap.clear();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference villesRef = firebaseDatabase.getReference("Mexico");

        villesRef.child("Zone").child(ville[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot inf = dataSnapshot.child(ville[1]);
                Villes villeChoisi = inf.getValue(Villes.class);

                vil = new Villes(villeChoisi.getImg(), villeChoisi.getInfo(), villeChoisi.getLat(), villeChoisi.getLng(), villeChoisi.getVille());

                GetNearbyPlacesData getNerabyPlacesData = new GetNearbyPlacesData();
                Object dataTransfer[] = new Object[3];

                String hospital = "hospital";
                String url = getUrl(vil.getLat(), vil.getLng(), hospital);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = hospital;

                getNerabyPlacesData.execute(dataTransfer);
                Toast.makeText(getApplicationContext(), "Show Hospital", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //C'est un methode different avec la meme instruction qui affiche Hotel
    public void setHotel(View view)
    {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference villesRef = firebaseDatabase.getReference("Mexico");

        villesRef.child("Zone").child(ville[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot inf = dataSnapshot.child(ville[1]);
                Villes villeChoisi = inf.getValue(Villes.class);

                vil = new Villes(villeChoisi.getImg(), villeChoisi.getInfo(), villeChoisi.getLat(), villeChoisi.getLng(), villeChoisi.getVille());

                GetNearbyPlacesData getNerabyPlacesData = new GetNearbyPlacesData();
                Object dataTransfer[] = new Object[3];

                mMap.clear();
                String hotel = "hotel";
                String url = getUrl(vil.getLat(), vil.getLng(), hotel);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = hotel;

                getNerabyPlacesData.execute(dataTransfer);
                Toast.makeText(getApplicationContext(), "Show Hotel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Methode qui permet de afficher la ubication de la ville selectioné
     * @param zone de la carte
     * @param index de la ville selectioné
     */

    public void afficherVille(String zone, String index)
    {
        final String ind = index;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference villesRef = firebaseDatabase.getReference("Mexico");

        villesRef.child("Zone").child(zone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot inf = dataSnapshot.child(ind);
                Villes villeChoisi = inf.getValue(Villes.class);

                vil = new Villes(villeChoisi.getImg(), villeChoisi.getInfo(), villeChoisi.getLat(), villeChoisi.getLng(), villeChoisi.getVille());

                txt_Info.setText(vil.getInfo());
                txt_Titre.setText(vil.getVille());
                btn_Ubication.setText(vil.getVille()+" et Vous");

                LatLng markerVille = new LatLng(vil.getLat(), vil.getLng());
                mMap.addMarker(new MarkerOptions().position(markerVille).title("Ici c'est "+vil.getVille()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ubicamex)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(markerVille));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerVille, 5.50f));
                Picasso.with(getApplicationContext())
                        .load(vil.getImg())
                        .error(R.drawable.iconmexico)
                        .fit()
                        .centerInside()
                        .into(imge);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * Methode qui permet de creer l'url a utiliser
     * @param latitude des lieux
     * @param longitude "" ""
     * @param nearbyPlace
     * @returnl'url pour afficher les lieu correspondant
     */
    private String getUrl(double latitude, double longitude, String nearbyPlace)
    {
        //on commence avec une variable qui va ajouter les direfents parties qui vont compposer l'url de demande a Google place
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        //on ajoute les coordonnes de la ville
        googlePlaceUrl.append("location="+latitude+","+longitude);
        //on ajoute un rayon pour afficher les lieux les plus proches
        googlePlaceUrl.append("&radius=100000");
        //Le type des lieux qu'on va montrer, par exemple les restaurant
        googlePlaceUrl.append("&type="+nearbyPlace);
        //c'est optional, on l'utilise pour nous asurer dans la fonctionalités de l'affisage
        googlePlaceUrl.append("&sensor=true");
        //notre KEY de google Place API
        googlePlaceUrl.append("&key="+"AIzaSyCSNe_m24he283amwNn5nFchHw7qDPGVcI");

        Log.i("DEBUG CreateURL", googlePlaceUrl.toString());
        //URL final pour realiser la requete a Google Place API
        return googlePlaceUrl.toString();
    }

}
