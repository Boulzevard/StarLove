package fr.wcs.starlove;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;
    private StorageReference mStorageRef;

    private Intent mGoToMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mRef = mFirebaseDatabase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mGoToMainActivity = new Intent(MainActivity.this, MainActivity.class);

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
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
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
                final TextView tvPseudo = findViewById(R.id.tv_pseudo);
                final EditText etPseudo = findViewById(R.id.et_pseudo);
                tvPseudo.setVisibility(View.VISIBLE);
                etPseudo.setVisibility(View.VISIBLE);
                boutonRegister.setText("Retour");
                boutonConnection.setText("Créer compte");




                boutonRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvPseudo.setVisibility(View.VISIBLE);
                        etPseudo.setVisibility(View.VISIBLE);
                        boutonRegister.setText("Signin");
                        boutonConnection.setText("Login");
                    }
                });
            }
        });


    }
}
