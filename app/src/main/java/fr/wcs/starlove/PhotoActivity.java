package fr.wcs.starlove;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class PhotoActivity extends AppCompatActivity {

    private ImageView mImagePhoto;
    private String mIDUser;
    static final int CAM_REQUEST = 0;
    static final int SELECT_IMAGE = 1;
    private Uri mSelectedImage = null;
    private Bitmap mBitmap = null;
    private StorageReference mStorageRef;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;
    private DrawerLayout mDrawerLayout;
    private ImageView mImgViewUserHeader;
    private Intent mGoToMainActivity;
    private static final int REQUEST_TAKE_PHOTO = 11;

    private Uri mUrlImage;
    private Uri mPhotoURI;
    private String mCurrentPhotoPath, mUserKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mGoToMainActivity = new Intent(PhotoActivity.this,ListeActivity.class);

        mDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mIDUser = mCurrentUser.getUid();

        mRef = mDatabase.getReference("Profils");
        mUserKey = getIntent().getStringExtra("userKey");

        mImagePhoto = (ImageView) findViewById(R.id.iv_photo_photo);

        mImagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickImageDialog();
            }
        });


    }

    private void showPickImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(PhotoActivity.this);
        builderSingle.setTitle(R.string.choisissez);

        final String [] items = new String[] {"Gallerie", "Appareil photo"};
        final Integer[] icons = new Integer[] {R.drawable.icongallery2, R.drawable.iconphoto2};
        ListAdapter adapter = new ArrayAdapterWithIcon(PhotoActivity.this, items, icons);

        builderSingle.setNegativeButton(
                R.string.annuler,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, SELECT_IMAGE);
                                break;

                            case 1:
                                dispatchTakePictureIntent();
                                break;
                        }

                    }
                });
        builderSingle.show();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mPhotoURI = FileProvider.getUriForFile(this,
                        "fr.wildcodeschool.rateeverything.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_" + mUserKey + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_TAKE_PHOTO:
                if(resultCode == RESULT_OK) {
                    mImagePhoto.setBackgroundResource(android.R.color.transparent);
                    Glide.with(PhotoActivity.this).load(mPhotoURI).into(mImagePhoto);
                }
                break;
            case SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    mSelectedImage = data.getData();
                    mPhotoURI = mSelectedImage;
                    Glide.with(PhotoActivity.this).load(mPhotoURI).into(mImagePhoto);
                }
                break;
        }

        final Button addImage = findViewById(R.id.bt_valid_photo);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StorageReference riverRef = mStorageRef.child("Image").child(mPhotoURI.getLastPathSegment());
                addImage.setEnabled(false);

                riverRef.putFile(mPhotoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String url = downloadUrl.toString();

                        mRef.child(mUserKey).child("image").setValue(url);
                        startActivity(mGoToMainActivity);
                        addImage.setEnabled(true);
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PhotoActivity.this, R.string.fail, Toast.LENGTH_SHORT).show();
                                addImage.setEnabled(true);
                            }
                        });

            }
        });
    }
}
