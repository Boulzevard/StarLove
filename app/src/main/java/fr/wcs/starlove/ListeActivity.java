package fr.wcs.starlove;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListeActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<ModelProfil> mArrayList;
    private AdapterList mAdapter;
    private ModelProfil mModel;

    FirebaseDatabase mDatabase;
    FirebaseUser mUser;
    DatabaseReference myRef;

    private String mUserID, currentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        Button  button = findViewById(R.id.button_map);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListeActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserID = mUser.getUid();
        myRef = mDatabase.getReference("Profils");
        currentID = "";


        mListView = findViewById(R.id.listviewprincipal);
        mArrayList = new ArrayList<>();
        mAdapter = new AdapterList(this, mArrayList);

        mListView.setAdapter(mAdapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAdapter.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("userid").exists()){
                        currentID = ds.child("userid").getValue().toString();
                    }

                    if (!currentID.equals(mUserID)){
                            mModel = ds.getValue(ModelProfil.class);
                            mAdapter.add(mModel);

                    }
                }
                mAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int j, long id) {
                Parcelable profil = new ModelProfil(mArrayList.get(j).getName(), mArrayList.get(j).getGender(), mArrayList.get(j).getHomeworld(),
                        mArrayList.get(j).getImage(), mArrayList.get(j).getSpecies());
                Intent intent = new Intent(ListeActivity.this, Description.class);
                intent.putExtra("profil", profil);
                startActivity(intent);
            }
        });

        Button toSearch = findViewById(R.id.bt_preferences);
        toSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListeActivity.this, Search.class);
                startActivity(intent);
            }
        });
    }
}
