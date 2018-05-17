package fr.wcs.starlove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Profils");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://cdn.rawgit.com/akabab/starwars-api/0.2.1/api/all.json";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    try {

                        for (int j = 0; j < response.length(); j++) {
                            JSONObject profilJSonObjet = (JSONObject) response.getJSONObject(j);

                            ModelProfil profilObjet = new ModelProfil();

                            profilObjet.setId(profilJSonObjet.getInt("id"));
                            profilObjet.setName(profilJSonObjet.getString("name"));
                            profilObjet.setHeight(profilJSonObjet.getLong("height"));
                            profilObjet.setMass(profilJSonObjet.getInt("mass"));
                            profilObjet.setGender(profilJSonObjet.getString("gender"));
                            profilObjet.setHomeworld(profilJSonObjet.getString("homeworld"));
                            profilObjet.setWiki(profilJSonObjet.getString("wiki"));
                            profilObjet.setImage(profilJSonObjet.getString("image"));
                            profilObjet.setAge(profilJSonObjet.getInt("age"));
                            profilObjet.setSpecies(profilJSonObjet.getString("species"));



                            myRef.push().setValue(profilObjet);






                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Afficher l'erreur
                    Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                }
            }
        );

        requestQueue.add(jsonObjectRequest);



    }
}
