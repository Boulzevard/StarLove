package fr.wcs.starlove;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    final DatabaseReference mMyRef = mDatabase.getReference("etoile");
    final DatabaseReference mMyRefPlanet = mDatabase.getReference("planet");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Style de la map, fichier json créé depuis mapstyle
        MapStyleOptions mapFilter = MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.map_style);
        googleMap.setMapStyle(mapFilter);
        mMap = googleMap;


        float minZoomPreference = 15;
        float maxZoomPreference = 4;
        //mMap.setMinZoomPreference(minZoomPreference);
        mMap.setMaxZoomPreference(maxZoomPreference);

        LatLngBounds AUSTRALIA = new LatLngBounds(
            new LatLng(-44, 113), new LatLng(-10, 154));

        // Add a marker in stars and move the camera
        LatLng stars = new LatLng(65.91342517317852, 97.6865230253797);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(stars));
        LatLng one = new LatLng(76.96362432417277, 39.02587325000002);
        LatLng two = new LatLng(22.314791439224802, 153.98681075000002);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //add them to builder
        builder.include(one);
        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.20);

        //set latlong bounds
        mMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stars, 4));



        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);


        mMyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotLatLng : dataSnapshot.getChildren()){
                    Double lng = dataSnapshotLatLng.child("longitude").getValue(Double.class);
                    Double lat = dataSnapshotLatLng.child("latitude").getValue(Double.class);
                    LatLng latLng = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("vile").icon(BitmapDescriptorFactory.fromResource(R.drawable.point_etoile)));                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMyRefPlanet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotLatLng : dataSnapshot.getChildren()){
                    String title = dataSnapshotLatLng.getKey();
                    Double lng = dataSnapshotLatLng.child("longitude").getValue(Double.class);
                    Double lat = dataSnapshotLatLng.child("latitude").getValue(Double.class);
                    LatLng latLng = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.planet_rouge)));                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent intent = new Intent(MapsActivity.this, PlanetActivity.class);
                intent.putExtra("planet", marker.getTitle());
                startActivity(intent);

                return false;
            }
        });

    }
}
