package fr.wcs.starlove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Search extends AppCompatActivity {

    String mSpecies;
    String mGender;
    String mSystem;
    String mHair;
    String mEye;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Spinner spinner_species = findViewById(R.id.sp_species);
        final Spinner spinner_system = findViewById(R.id.sp_system);
        final Spinner spinner_gender = findViewById(R.id.sp_gender);
        final Spinner spinner_hair = findViewById(R.id.sp_hair);
        final Spinner spinner_eye = findViewById(R.id.sp_eye);
        Button toFilteredList = findViewById(R.id.bt_find);
        Button ajoutCompte = findViewById(R.id.bt_ajout);

        // test état :
        status = 1;
        String etat = getIntent().getStringExtra("status");
        if (etat.equals("2")){
            status = Integer.valueOf(etat);
        }


        // On filtre la liste principale :

        if (status == 1){
            toFilteredList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mSpecies = spinner_species.getSelectedItem().toString().toLowerCase();
                    mSystem = spinner_system.getSelectedItem().toString().toLowerCase();
                    mGender = spinner_gender.getSelectedItem().toString().toLowerCase();
                    mHair = spinner_hair.getSelectedItem().toString().toLowerCase();
                    mEye = spinner_eye.getSelectedItem().toString().toLowerCase();

                    Intent intent = new Intent(Search.this, FilterListActivity.class);
                    intent.putExtra("species", mSpecies);
                    intent.putExtra("system", mSystem);
                    intent.putExtra("gender", mGender);
                    intent.putExtra("hair", mHair);
                    intent.putExtra("eye", mEye);
                    startActivity(intent);

                }
            });

        }


        // On crée son profil :

        else if (status == 2){

            toFilteredList.setVisibility(View.GONE);
            ajoutCompte.setVisibility(View.VISIBLE);
            TextView title = findViewById(R.id.tv_tittle_search);
            title.setText("How are you ?");
            TextView info = findViewById(R.id.textView4);
            info.setText("Please fill all cases");
            ajoutCompte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userKey = getIntent().getStringExtra("userKey");

                    mSpecies = spinner_species.getSelectedItem().toString().toLowerCase();
                    mSystem = spinner_system.getSelectedItem().toString().toLowerCase();
                    mGender = spinner_gender.getSelectedItem().toString().toLowerCase();
                    mHair = spinner_hair.getSelectedItem().toString().toLowerCase();
                    mEye = spinner_eye.getSelectedItem().toString().toLowerCase();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Profils");

                    myRef.child(userKey).child("species").setValue(mSpecies);
                    myRef.child(userKey).child("homeworld").setValue(mSystem);
                    myRef.child(userKey).child("gender").setValue(mGender);
                    myRef.child(userKey).child("hairColor").setValue(mHair);
                    myRef.child(userKey).child("eyeColor").setValue(mEye);

                    startActivity(new Intent(Search.this, ListeActivity.class));



                }
            });

        }


    }
}
