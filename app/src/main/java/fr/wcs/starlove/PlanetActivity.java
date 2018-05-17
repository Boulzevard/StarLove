package fr.wcs.starlove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlanetActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mMyRef = mDatabase.getReference("Profils");

    private ListView mListView;
    private ArrayList<ModelProfil> mArrayList;
    private AdapterPlanet mAdapter;
    private ModelProfil mModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);

        Intent intent = getIntent();
        final String planet = intent.getStringExtra("planet");

        mListView = findViewById(R.id.listview_planet);
        mArrayList = new ArrayList<>();
        mAdapter = new AdapterPlanet(this, mArrayList);

        mMyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot result : dataSnapshot.getChildren()){

                    mModel = result.getValue(ModelProfil.class);
                    if(mModel.getHomeworld() != null) {
                        if (mModel.getHomeworld().equals(planet)) {
                            mArrayList.add(mModel);
                        }

                    }


                }
                mAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mListView.setAdapter(mAdapter);


    }
}
