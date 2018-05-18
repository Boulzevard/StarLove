package fr.wcs.starlove;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterList extends ArrayAdapter<ModelProfil> {
    public AdapterList(Context context, ArrayList<ModelProfil> lesProfils) {
        super(context, 0, lesProfils);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelProfil profil = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_liste, parent, false);
        }

        if (profil != null) {

            if (profil.getImage() != null) {
                if (profil.getImage().isEmpty()) {

                } else {
                    ImageView photo = (ImageView) convertView.findViewById(R.id.iv_photo);
                    Glide.with(parent.getContext()).load(profil.getImage().toString()).apply(RequestOptions.circleCropTransform()).into(photo);
                }
            }

            if (profil.getName().toString() != null) {
                TextView textName = (TextView) convertView.findViewById(R.id.tv_name);
                textName.setText(profil.getName().toString());
            }

            if (profil.getAge() != 0) {
                TextView tvAge = (TextView) convertView.findViewById(R.id.tv_age);
                float age = 0;
                if (profil.getAge() < 0){
                    age = -profil.getAge();
                }
                else {
                    age = profil.getAge();
                }
                tvAge.setText(age + "");
            }

            if (profil.getHomeworld() != null) {
                TextView homeworld = (TextView) convertView.findViewById(R.id.tv_planet);
                homeworld.setText(profil.getHomeworld() + "");
            }

            if (profil.getSpecies() != null) {
                TextView species = (TextView) convertView.findViewById(R.id.tv_espece);
                species.setText(profil.getSpecies() + "");
            }
        }


            return convertView;
        }
    }

/*
public class FollowersAdapter extends ArrayAdapter<FollowersModel> {
    public FollowersAdapter (Context context, ArrayList<FollowersModel> followers) {
        super(context, 0, followers);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        final FollowersModel followers = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_followers, parent, false);
        }

        if (followers != null) {

            if (followers.getPhotouser() != null) {
                if (followers.getPhotouser().equals("1")){
                    ImageView photo = (ImageView) convertView.findViewById(R.id.imageview_photo_followers);
                    Glide.with(parent.getContext()).load(R.drawable.defaultimageuser).into(photo);
                } else {
                    ImageView photo = (ImageView) convertView.findViewById(R.id.imageview_photo_followers);
                    Glide.with(parent.getContext()).load(followers.getPhotouser().toString()).into(photo);
                }
            }
            else if (followers.getPhotouser() == null) {
                ImageView photo = (ImageView) convertView.findViewById(R.id.imageview_photo_followers);
                Glide.with(parent.getContext()).load(R.drawable.defaultimageuser).into(photo);
            }

            if (followers.getUsername().toString() != null) {
                TextView textName = (TextView) convertView.findViewById(R.id.textview_user_name_followers);
                textName.setText(followers.getUsername().toString());
            }

            if (followers.getNbphoto() != 0) {
                TextView textNbPhoto = (TextView) convertView.findViewById(R.id.textview_nb_photo_followers);
                textNbPhoto.setText(followers.getNbphoto() + "");
            }
            else if(followers.getNbphoto() == 0){
                TextView textNbPhoto = (TextView) convertView.findViewById(R.id.textview_nb_photo_followers);
                textNbPhoto.setText(0 + "");
            }

            if (followers.getNbfollowers() != 0) {
                TextView textNbFollowers = (TextView) convertView.findViewById(R.id.textview_nb_followers);
                textNbFollowers.setText(followers.getNbfollowers() + "");
            }
            else if (followers.getNbfollowers() == 0){
                TextView textNbFollowers = (TextView) convertView.findViewById(R.id.textview_nb_followers);
                textNbFollowers.setText(0 + "");
            }
        }

        return convertView;
}


 */