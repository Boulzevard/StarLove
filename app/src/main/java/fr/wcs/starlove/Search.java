package fr.wcs.starlove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class Search extends AppCompatActivity {

    String mSpecies;
    String mGender;
    String mSystem;
    String mHair;
    String mEye;

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



        toFilteredList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSpecies = spinner_species.getSelectedItem().toString().toLowerCase();
                mSystem = spinner_system.getSelectedItem().toString().toLowerCase();
                mGender = spinner_gender.getSelectedItem().toString().toLowerCase();
                mHair = spinner_hair.getSelectedItem().toString().toLowerCase();
                mEye = spinner_eye.getSelectedItem().toString().toLowerCase();

            }
        });


    }
}
