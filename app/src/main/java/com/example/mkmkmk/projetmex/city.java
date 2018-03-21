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

public class city extends AppCompatActivity {
    List<String> villes =  new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listV;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent getIntent = getIntent();
        final String getZone = getIntent.getExtras().getString("zone");
        listV = (ListView) findViewById(R.id.listeVille);
        intent = new Intent(this,mapVille.class);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference villesRef = firebaseDatabase.getReference("Mexico");
        villesRef.child("Zone").child(getZone).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (int i = 1; i<=dataSnapshot.getChildrenCount();i++){
                    DataSnapshot G = dataSnapshot.child(Integer.toString(i));
                    Villes vil = G.getValue(Villes.class);
                    if(!villes.contains(vil.getVille())){
                        villes.add(vil.getVille());

                       // Toast.makeText(getApplicationContext(), ""+villes, Toast.LENGTH_SHORT).show();
                    }
                }
                adapter = new ArrayAdapter(getApplicationContext(),R.layout.my_cutom_layout, villes);
                listV.setAdapter(adapter);
                //listV.setCacheColorHint(Color.WHITE);
                //listV.setBackgroundColor(Color.GREEN);
                listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(), "On va à : "+(position+1)+" "+villes.get(position), Toast.LENGTH_SHORT).show();
                        String [] villeData = {getZone,""+(position+1) ,villes.get(position)};
                        intent.putExtra("ville", villeData);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
