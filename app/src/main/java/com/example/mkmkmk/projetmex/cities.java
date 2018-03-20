package com.example.mkmkmk.projetmex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.mkmkmk.projetmex.model.cityModel;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cities extends AppCompatActivity {
    DatabaseReference myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        Intent intent = getIntent();
        int getZone = intent.getIntExtra("zone",0);

        Toast.makeText(getApplicationContext(),getZone,Toast.LENGTH_SHORT).show();

/*        myDB = FirebaseDatabase.getInstance("vivamexico-936a4").getReference("villes");
        myDB.child("centre").setValue(new city( "JAJAJAJJAA", "tugfa.png", 123131,324324));
        Toast.makeText(getApplicationContext(),"jqjqjqs",Toast.LENGTH_SHORT).show();*/
    }
}
