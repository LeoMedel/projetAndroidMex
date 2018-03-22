package com.example.mkmkmk.projetmex;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author Leonel MEDEL et Emmanuel YAH
 * @version 21/03/2018
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //attributs de la classe
    private GoogleMap mMap;
    Intent intent;
    private LatLngBounds Mexico;

    /**Methode qui affiche l'interface
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //on implement cet methode pour afficher la vue en LANDSCAPE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //on inicialice notre variable intent
        intent =  new Intent(this, city.class);
    }//Fermeture de methode

    /** Methode qui nous affiche la carte dans l'element de la vue
     *@param googleMap le methode l'utilise pour creer la carte de Google Maps
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //on affiche la carte avec le type hibride
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //On afficherons la carte qui correspond aux coordonnees du Mexique
        LatLng Mex = new LatLng(23.957186, -102.535321);
        Mexico = new LatLngBounds(Mex,Mex);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Mex, 3));
        mMap.setLatLngBoundsForCameraTarget(Mexico);
        mMap.setMinZoomPreference(4.6f);

        //Quand la carte est pret, on ajoute les points qui vont delimite Mexique en trois zones
        LatLng p1 = new LatLng(22.890342 ,-109.917998);
        LatLng p2 = new LatLng( 23.853434 ,-110.759552);
        LatLng p3 = new LatLng(24.789474,  -112.291023);
        LatLng p4 = new LatLng(26.059494, -112.27784);
        LatLng p5 =  new LatLng( 27.140127,-114.29318);
        LatLng p6 = new LatLng(27.832102,  -115.065519);
        LatLng p7 = new LatLng(28.572772,  -114.134977);
        LatLng p8 = new LatLng(29.7359, -115.678911);
        LatLng p9 =  new LatLng(32.522807,-117.036822);
        LatLng p10 = new LatLng( 32.713478, -114.730108);
        LatLng p11 = new LatLng(32.498303, -114.814153);
        LatLng p12 = new LatLng(31.333001, -111.075679);
        LatLng p13 = new LatLng(31.339735, -108.205104);
        LatLng p14 = new LatLng(31.339735, -108.205104);
        LatLng p15 = new LatLng(31.764931, -108.20193);
        LatLng p16 = new LatLng(31.783611, -106.529773);
        LatLng p17 = new LatLng(30.667898, -105.035606);
        LatLng p18 = new LatLng(29.555995, -104.404991);
        LatLng p19 = new LatLng(28.97136, -103.166832);
        LatLng p20 = new LatLng( 29.807736, -102.317589);
        LatLng p21 = new LatLng( 28.45104, -100.447332);
        LatLng p22 = new LatLng(27.492411, -99.58161);
        LatLng p23 = new LatLng(26.367977, -99.111396);
        LatLng p24 = new LatLng(25.88663, -97.147041);
        LatLng p25 = new LatLng(22.248149, -97.885323);
        LatLng p26 = new LatLng(22.464478, -99.191313);
        LatLng p27 = new LatLng(22.944568, -100.03067);
        LatLng p28 = new LatLng(25.06802,  -102.245516);
        LatLng p29 = new LatLng(25.060058,  -102.654208);
        LatLng p30 = new LatLng(24.473484 , -102.469639);
        LatLng p31 = new LatLng(24.405471, -103.383702);
        LatLng p32 = new LatLng(23.465599, -104.025305);
        LatLng p33 = new LatLng(22.587979, -104.324134);
        LatLng p34 = new LatLng(22.43371, -104.387855);
        LatLng p35 = new LatLng(22.424571, -104.548256);
        LatLng p36 = new LatLng(22.600151, -104.72184);
        LatLng p37 = new LatLng(23.002492, -105.244789);
        LatLng p38 = new LatLng(22.968104, -105.52604);
        LatLng p39 = new LatLng(22.599406, -105.510659);
        LatLng p40 = new LatLng(22.564916, -105.725992);
        LatLng p41 = new LatLng(22.898367 , -109.915079);


        LatLng c1 = new LatLng( 22.373064 , -105.685955);
        LatLng c2 = new LatLng( 22.501012 , -105.406902);
        LatLng c3 = new LatLng( 22.851754 , -105.237713);
        LatLng c4 = new LatLng(22.807201 , -105.035565);
        LatLng c5 = new LatLng(22.610588 , -105.086103);
        LatLng c6 = new LatLng(22.515222 , -105.004804);
        LatLng c7 = new LatLng( 22.239596 , -104.397149);
        LatLng c8 = new LatLng(22.244749 , -104.402232);
        LatLng c9 = new LatLng(22.737657 , -104.069142);
        LatLng c10 = new LatLng(23.524705 , -103.878874);
        LatLng c11= new LatLng(24.356749 , -103.333243);
        LatLng c12 = new LatLng(24.378822 , -102.623263);
        LatLng c13 = new LatLng(24.93146 , -102.555714);
        LatLng c14 = new LatLng(24.927475 ,-101.870168);
        LatLng c15 = new LatLng(24.464336 , -101.404348);
        LatLng c16 = new LatLng(23.611692 , -100.756371);
        LatLng c17 = new LatLng(23.073042 , -100.433373);
        LatLng c18 = new LatLng(22.522078 , -99.813745);
        LatLng c19 = new LatLng(22.284401 , -99.139184);
        LatLng c20 = new LatLng(22.117583 , -98.025171);
        LatLng c21 = new LatLng(19.127919 , -96.104762);
        LatLng c22 = new LatLng(16.421944 , -100.973541);
        LatLng c23 = new LatLng(20.167948 , -106.018464);
        LatLng c24 = new LatLng(22.421818, -105.717466);

        LatLng s1 = new LatLng(16.749165, -99.670824);
        LatLng s2 = new LatLng(19.116473, -96.127735);
        LatLng s3 = new LatLng(21.564842 , -90.532399);
        LatLng s4 = new LatLng(21.577102 , -86.019216);
        LatLng s5 = new LatLng( 18.51436, -88.251639);
        LatLng s6 = new LatLng(17.858889 , -89.187675);
        LatLng s7 = new LatLng(17.829607 , -90.958672);
        LatLng s8 = new LatLng(17.263952, -91.008111);
        LatLng s9 = new LatLng(17.256608 , -91.445367);
        LatLng s10 = new LatLng(16.096439 , -90.481866);
        LatLng s11 = new LatLng(16.090719, -91.736008);
        LatLng s12 = new LatLng(15.27062 ,-92.214768);
        LatLng s13 = new LatLng( 14.438086 ,-92.331224);
        LatLng s14 = new LatLng(16.779509 , -99.772264);

        //nous creons des polygones pour savoir dans quelle zone quelqu'un touche la carte dans l'ecran
        //Ici, nous saurons que l'utilisateur touche le nord du Mexique
        Polygon nordOption = mMap.addPolygon(new PolygonOptions()
                .add(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24,p25,p26,p27,p28,p29,p30,p31,p32,p33,p34,p35,p36,p37,p38,p39,p40,p41)
                .strokeColor(Color.TRANSPARENT));
        //Ici, nous saurons que l'utilisateur touche le centre du Mexique
        Polygon centreOption = mMap.addPolygon(new PolygonOptions()
            .add(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22,c23,c24)
                .strokeColor(Color.TRANSPARENT));

        //Finalement, nous saurons que l'utilisateur touche le sud du Mexique
        Polygon sudOption = mMap.addPolygon(new PolygonOptions()
        .add(s1,s2,s3,s4,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14)
        .strokeColor(Color.TRANSPARENT));

        nordOption.setClickable(true);
        centreOption.setClickable(true);
        sudOption.setClickable(true);

        //dans cette cas, nous saurons que l'utilisateur ne touche pas la carte au Mexique, on affiche une message
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getApplicationContext(),"Cliquez dessus Mexique", Toast.LENGTH_LONG).show();
            }
        });

        //Quand quelqu'un touche la carte au Mexique, nous afficherons la suivant vue qui correspond a la zone touché
        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                String zone = polygon.getId();
                switch (zone){
                    case "pg3":
                        intent.putExtra("zone","Nord");
                        startActivity(intent);
                        break;
                    case "pg0":
                        intent.putExtra("zone","Nord");
                        startActivity(intent);
                        break;
                    case "pg4":
                        intent.putExtra("zone", "Centre");
                        startActivity(intent);
                        break;
                    case "pg1":
                        intent.putExtra("zone","Centre");
                        startActivity(intent);
                        break;
                    case "pg5":
                        intent.putExtra("zone", "sud");
                        startActivity(intent);
                        break;
                    case "pg2":
                        intent.putExtra("zone","sud");
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Veuillez redémarrer l'application. MERCI", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

    }//Fermeture de onMapReady
}//Fermeture de la class
