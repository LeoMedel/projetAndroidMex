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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class mapVille extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker markerUbicacion;

    private TextView txt_Titre;
    private TextView txt_Info;
    private Button btn_Restaurant;
    private Button btn_Museo;
    private Button btn_Hospital;
    private Button btn_Hotel;
    private  Button btn_Ubicacion;
    private ImageView imge;
    private Bitmap loadedImage;
    private Villes vil;
    private double latitude = 0.0;
    private double longitude =0.0;
    int PROXIMITY_RADIUS = 100000;
    String[] ville;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_ville);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent getIntent = getIntent();
        ville = getIntent.getStringArrayExtra("ville");

        txt_Titre = (TextView) findViewById(R.id.txtTitre);
        txt_Info = (TextView) findViewById(R.id.txtInfo);
        btn_Restaurant = (Button) findViewById(R.id.btnRestaurant);
        btn_Museo = (Button) findViewById(R.id.btnMuseo);
        btn_Hospital = (Button) findViewById(R.id.btnHospital);
        btn_Hotel = (Button) findViewById(R.id.btnHotel);
        btn_Ubicacion = (Button) findViewById(R.id.btnUbicacion);
        imge = (ImageView) findViewById(R.id.villeIMG);
    }

    PolylineOptions chemin;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        miUbicacion();
        createVille(ville[0], ville[1]);




    }

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        actualizarUbicacion(location);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
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

    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        //CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 5);

        if (markerUbicacion != null) {
            markerUbicacion.remove();
        }
        markerUbicacion = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Je suis ici")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        //mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            agregarMarcador(latitude, longitude);
        }
    }



    public void setUbicacion(View view)
    {
        mMap.clear();
        miUbicacion();
        createVille(ville[0], ville[1]);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference villesRef = firebaseDatabase.getReference("Mexico");

        villesRef.child("Zone").child(ville[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot inf = dataSnapshot.child(ville[1]);
                Villes villeChoisi = inf.getValue(Villes.class);

                vil = new Villes(villeChoisi.getImg(), villeChoisi.getInfo(), villeChoisi.getLat(), villeChoisi.getLng(), villeChoisi.getVille());
                Location l = new Location("Location 1");
                l.setLatitude(vil.getLat());
                l.setLongitude(vil.getLng());
                Location l2 = new Location("Location 2");
                l2.setLatitude(latitude);
                l2.setLongitude(longitude);
                double distanceM = l.distanceTo(l2);
                double distanceK = distanceM/1000;

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





    public void setRestaurant(View view)
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
                Object dataTransfer[] = new Object[2];

                mMap.clear();
                String restaurant = "restaurant";
                String url = getUrl(vil.getLat(), vil.getLng(), restaurant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNerabyPlacesData.execute(dataTransfer);
                Toast.makeText(getApplicationContext(), "Show Restaurant", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setMuseo(View view)
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
                Object dataTransfer[] = new Object[2];

                mMap.clear();
                String restaurant = "Museo";
                String url = getUrl(vil.getLat(), vil.getLng(), restaurant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNerabyPlacesData.execute(dataTransfer);
                Toast.makeText(getApplicationContext(), "Show Museum", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void setHosptital(View view)
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
                Object dataTransfer[] = new Object[2];

                mMap.clear();
                String restaurant = "Hospital";
                String url = getUrl(vil.getLat(), vil.getLng(), restaurant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNerabyPlacesData.execute(dataTransfer);
                Toast.makeText(getApplicationContext(), "Show Hospital", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

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
                Object dataTransfer[] = new Object[2];

                mMap.clear();
                String restaurant = "Hotel";
                String url = getUrl(vil.getLat(), vil.getLng(), restaurant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNerabyPlacesData.execute(dataTransfer);
                Toast.makeText(getApplicationContext(), "Show Hotel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public void createVille(String zone, String index)
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
                btn_Ubicacion.setText(vil.getVille()+" et Vous");
                //Toast.makeText(getApplicationContext(),vil.getImg(),Toast.LENGTH_LONG).show();

                LatLng markerVille = new LatLng(vil.getLat(), vil.getLng());
                mMap.addMarker(new MarkerOptions().position(markerVille).title("Ici c'est "+vil.getVille()).icon(BitmapDescriptorFactory.fromResource(R.drawable.taco)));
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



    private String getUrl(double latitude, double longitude, String nearbyPlace)
    {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyCSNe_m24he283amwNn5nFchHw7qDPGVcI");

        Log.i("DEBUG CreateURL", googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }





}
