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

public class AdapterPlanet extends ArrayAdapter<ModelProfil> {



        public AdapterPlanet(Context context, ArrayList<ModelProfil> lesProfils) {
            super(context, 0, lesProfils);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ModelProfil profil = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_planet, parent, false);
            }

            if (profil != null) {

                if (profil.getImage() != null) {
                    if (profil.getImage().isEmpty()) {

                    } else {
                        ImageView photo = (ImageView) convertView.findViewById(R.id.image_profil);
                        Glide.with(parent.getContext()).load(profil.getImage().toString()).apply(RequestOptions.circleCropTransform()).into(photo);
                    }
                }

                if (profil.getName().toString() != null) {
                    TextView textName = (TextView) convertView.findViewById(R.id.text_name);
                    textName.setText(profil.getName().toString());
                }

                if (profil.getHeight() != 0) {
                    TextView tvAge = (TextView) convertView.findViewById(R.id.text_height);
                    Double taille = -profil.getHeight();
                    tvAge.setText(taille + "");
                }

                if (profil.getSpecies() != null) {
                    TextView species = (TextView) convertView.findViewById(R.id.text_species);
                    species.setText(profil.getSpecies() + "");
                }

                if (profil.getGender() != null) {
                    TextView gender = (TextView) convertView.findViewById(R.id.text_gender);
                    gender.setText(profil.getGender() + "");
                }


            }


            return convertView;
        }
    }

