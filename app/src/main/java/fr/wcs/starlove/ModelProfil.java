package fr.wcs.starlove;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelProfil implements Parcelable {
    int id;
    String name;
    Double height;
    int mass;
    String gender;
    String homeworld;
    String wiki;
    String image;
    int age;
    String species;

    public ModelProfil()  {
    }

    public ModelProfil(int id, String name, Double height, int mass, String gender, String homeworld, String wiki, String image, int age, String species) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.gender = gender;
        this.homeworld = homeworld;
        this.wiki = wiki;
        this.image = image;
        this.age = age;
        this.species = species;
    }

    public ModelProfil(String name, String gender, String homeworld, String image, String species) {
        this.name = name;
        this.gender = gender;
        this.homeworld = homeworld;
        this.image = image;
        this.species = species;
    }

    protected ModelProfil(Parcel in) {
        id = in.readInt();
        name = in.readString();
        if (in.readByte() == 0) {
            height = null;
        } else {
            height = in.readDouble();
        }
        mass = in.readInt();
        gender = in.readString();
        homeworld = in.readString();
        wiki = in.readString();
        image = in.readString();
        age = in.readInt();
        species = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        if (height == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(height);
        }
        dest.writeInt(mass);
        dest.writeString(gender);
        dest.writeString(homeworld);
        dest.writeString(wiki);
        dest.writeString(image);
        dest.writeInt(age);
        dest.writeString(species);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelProfil> CREATOR = new Creator<ModelProfil>() {
        @Override
        public ModelProfil createFromParcel(Parcel in) {
            return new ModelProfil(in);
        }

        @Override
        public ModelProfil[] newArray(int size) {
            return new ModelProfil[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}