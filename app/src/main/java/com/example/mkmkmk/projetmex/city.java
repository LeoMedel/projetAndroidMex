package com.example.mkmkmk.projetmex;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mkmkmk.projetmex.model.Villes;
import com.example.mkmkmk.projetmex.model.cityModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**@author Leonel MEDEL et Emmanuel YAH
 * @version 21/03/2018
 */
public class city extends AppCompatActivity {

    //Attributs de la classe
    List<String> villes =  new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listV;
    Intent intent;

    /**
     * Methode qui crée l'interface
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        //on affiche l'interface en LANDSCAPE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Ici, on va recevoir les donnees de la derniere interface et savoir qu'on va affiche
        Intent getIntent = getIntent();
        final String getZone = getIntent.getExtras().getString("zone");
        listV = (ListView) findViewById(R.id.listeVille);
        intent = new Intent(this,mapVille.class);

        //Nous implementons une basse de donnees Firebase
        //on utilise un variable Firebase qui a l'instance de la basse de donnees
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //on fait la reference avec la BD
        final DatabaseReference villesRef = firebaseDatabase.getReference("Mexico");
        //avec cette function de Firebase on peut affiche les donnes
        villesRef.child("Zone").child(getZone).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //on fait un boucle pour ajouter les donnees de la BD de Firebase dans une liste
                for (int i = 1; i<=dataSnapshot.getChildrenCount();i++){
                    //Ici, on fait la reference de l'information qu'on a besoin d'afficher
                    DataSnapshot G = dataSnapshot.child(Integer.toString(i));
                    //on ajoute l'information dans une classe déjà defini
                    Villes vil = G.getValue(Villes.class);
                    //on fqit une condition pour eviter la duplication d'un donnee
                    if(!villes.contains(vil.getVille())){
                        //on ajoute les donnees dans une liste
                        villes.add(vil.getVille());

                    }
                }
                //on fait un Adapter pour montrer la liste dans l'interface
                adapter = new ArrayAdapter(getApplicationContext(),R.layout.my_cutom_layout, villes);
                //finalement, on ajoute l'adaptador dans l'element de l'interface
                listV.setAdapter(adapter);

                //a chaque élément de la liste, on les ajoute un evenement Click
                listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //on crée une array qu'on envoie avec l'information pour la suivant vue
                        String [] villeData = {getZone,""+(position+1) ,villes.get(position)};
                        //on ajoute l'array
                        intent.putExtra("ville", villeData);
                        //on commence la suivant interface
                        startActivity(intent);

                    }
                });
            }//Fermeture de methode

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//fermeture de onCreate
}//fermeture de la classe
