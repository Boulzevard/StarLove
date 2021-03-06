package fr.wcs.starlove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();

        ModelProfil profil = intent.getParcelableExtra("profil");

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
                mIntro = dataSnapshot.child("2").getValue(String.class);
                description.setText(mIntro + " " +mName + " " + mGender + " " + mHomeworld + " " + mSpecies +" .");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }

}
