package fr.wcs.starlove;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;
    private StorageReference mStorageRef;
    private long id;

    private Intent mGoToMainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mGoToMainActivity = new Intent(MainActivity.this, MainActivity.class);

        mRef.child("Profils").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id = dataSnapshot.getChildrenCount() + 2;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

     //  -------------------------- Connection : ----------------------------------------------------------------------

        final Button boutonConnection = findViewById(R.id.bt_login);

        boutonConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editMail = findViewById(R.id.et_email);
                String email = editMail.getText().toString();
                final EditText editPass = findViewById(R.id.et_pass);
                final String password = editPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        Toast.makeText(MainActivity.this, "Password to short", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "wrong Password", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(MainActivity.this, ListeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

       //  ---------------------------- Création Compte ----------------------------------------------------------------

        final Button boutonRegister = findViewById(R.id.bt_signin);

            boutonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tvPseudo = findViewById(R.id.tv_pseudo);
                    final EditText etPseudo = findViewById(R.id.et_pseudo);
                    Button boutonCreation = findViewById(R.id.bt_creation);
                    tvPseudo.setVisibility(View.VISIBLE);
                    etPseudo.setVisibility(View.VISIBLE);
                    boutonConnection.setVisibility(View.GONE);
                    boutonCreation.setVisibility(View.VISIBLE);

                    boutonCreation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            EditText editMail = findViewById(R.id.et_email);
                            EditText editPass = findViewById(R.id.et_pass);
                            final String pseudo = etPseudo.getText().toString().trim();
                            String email = editMail.getText().toString().trim();
                            String password = editPass.getText().toString().trim();

                            if (TextUtils.isEmpty(email)) {
                                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (TextUtils.isEmpty(password)) {
                                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (password.length() < 6) {
                                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            //create user
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                                                String userID = mCurrentUser.getUid();
                                                String key = mRef.child("Profils").push().getKey();
                                                mRef.child("Profils").child(key).child("name").setValue(pseudo);
                                                mRef.child("Profils").child(key).child("id").setValue(id);
                                                mRef.child("Profils").child(key).child("userid").setValue(userID);
                                                mRef.child("Profils").child(key).child("key").setValue(key);

                                                Intent intent = new Intent(MainActivity.this, ListeActivity.class);
                                                intent.putExtra("userKey", key);
                                                startActivity(intent);

                                                finish();
                                            }
                                        }
                                    });

                        }
                    });



                }
            });






    }
}
