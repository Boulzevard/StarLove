package fr.wcs.starlove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Description extends AppCompatActivity {

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mMyRefProfil = mDatabase.getReference("Profils");
    DatabaseReference mMyRefdescription = mDatabase.getReference("description");
    String mIntro;
    String mName;
    String mGender;
    String mHomeworld;
    String mImage;
    String mSpecies;
    String mUId;
    Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();

        ModelProfil profil = intent.getParcelableExtra("profil");
        mUId = intent.getStringExtra("mUid");

        mName = profil.getName();
        mGender = profil.getGender();
        mHomeworld = profil.getHomeworld();
        mImage = profil.getImage();
        mSpecies = profil.getSpecies();

        final TextView description  = findViewById(R.id.text_description);
        ImageView imageProfil = findViewById(R.id.image_profil);
        Glide.with(this).load(mImage).apply(RequestOptions.circleCropTransform()).into(imageProfil);


        mMyRefdescription.child("intro").child("human").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot descrip : dataSnapshot.getChildren())
                    //int i2 = mRandom.nextInt(4);
                mIntro = dataSnapshot.child("1").getValue(String.class);
                description.setText(mIntro + "je  " +mName + " " + mGender + " " + mHomeworld + " " + mSpecies +" .");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageView itineraire = findViewById(R.id.image_itinarary);
        itineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Description.this, MapItineraryActivity.class);
                intent1.putExtra("destination", mHomeworld);
                intent1.putExtra("mUId", mUId);
                intent1.putExtra("name", mName);
                startActivity(intent1);
            }
        });







    }

}
