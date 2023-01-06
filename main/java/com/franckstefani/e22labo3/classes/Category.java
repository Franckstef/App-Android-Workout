package com.franckstefani.e22labo3.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private int _id;
    private String titre;
    private String image;

    public Category(int _id, String categorie, String image) {
        this._id = _id;
        this.titre = categorie;
        this.image = image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Category(Parcel in) {
        _id = in.readInt();
        titre = in.readString();
        image = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(_id);
        out.writeString(titre);
        out.writeString(image);
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }
        public Category[] newArray(int size) { return new Category[size]; }
    };
}
