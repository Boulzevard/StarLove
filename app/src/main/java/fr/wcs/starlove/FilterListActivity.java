package fr.wcs.starlove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FilterListActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<ModelProfil> mArrayList;
    private AdapterList mAdapter;
    private ModelProfil mModel;
    private String mSpecies;
    private String mGender;
    private String mSystem;
    private String mHair;
    private String mEye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_list);

        final String species = getIntent().getStringExtra("species");
        final String gender = getIntent().getStringExtra("gender");
        final String system = getIntent().getStringExtra("system");
        final String hair = getIntent().getStringExtra("hair");
        final String eye = getIntent().getStringExtra("eye");

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("Profils");

        mListView = findViewById(R.id.lv_filter_list);
        mArrayList = new ArrayList<>();
        mAdapter = new AdapterList(this, mArrayList);

        mListView.setAdapter(mAdapter);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAdapter.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {

                    if (ds.hasChild("species") && ds.hasChild("gender") && ds.hasChild("homeworld")
                            /*&& ds.hasChild("hairColor") && ds.hasChild("eyeColor")*/) {


                        mSpecies = ds.child("species").getValue().toString();
                        mGender = ds.child("gender").getValue().toString();
                        mSystem = ds.child("homeworld").getValue().toString();
//                        mHair = ds.child("hairColor").getValue().toString();
//                        mEye = ds.child("eyeColor").getValue().toString();

                            if (mSpecies.contains(species) && mGender.contains(gender) && mSystem.contains(system)
                                 /*&& mHair.contains(hair) && mEye.contains(eye)*/) {
                                mModel = ds.getValue(ModelProfil.class);
                                mAdapter.add(mModel);

                            }
                    }
                }
                    mAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//                    String currentHair = ds.child("hairColor").getValue().toString();
        //                  String currentEye = ds.child("eyeColor").getValue().toString();
    }
}
