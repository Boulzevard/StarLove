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


public class ListeActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<ModelProfil> mArrayList;
    private AdapterList mAdapter;
    private ModelProfil mModel;

    FirebaseDatabase mDatabase;
    FirebaseUser mUser;
    DatabaseReference myRef;

    private String mUserID;
    private String mUserKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserID = mUser.getUid();
        myRef = mDatabase.getReference("Profils");
        mUserKey = getIntent().getStringExtra("key");


        mListView = findViewById(R.id.listviewprincipal);
        mArrayList = new ArrayList<>();
        mAdapter = new AdapterList(this, mArrayList);

        mListView.setAdapter(mAdapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAdapter.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String currentKey = ds.child("key").getValue().toString();
                    if (!currentKey.equals(mUserKey)){
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

    }
}
