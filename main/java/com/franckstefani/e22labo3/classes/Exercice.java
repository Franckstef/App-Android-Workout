package com.franckstefani.e22labo3.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercice implements Parcelable {
    private int _id;
    private String titre;
    private String image;
    private String description;
    private int _idFK;

    public Exercice(int _id, String titre, String image, String description, int _idFK) {
        this._id = _id;
        this.titre = titre;
        this.image = image;
        this.description = description;
        this._idFK = _idFK;
    }

    public Exercice(int _id, String titre, String image, String description) {
        this._id = _id;
        this.titre = titre;
        this.image = image;
        this.description = description;
    }

    public Exercice(String titre, String description, String image, int categorie) {
        this.titre = titre;
        this.image = image;
        this.description = description;
        this._idFK = categorie;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int get_idFK() { return _idFK; }

    public void set_idFK(int _idFK) { this._idFK = _idFK; }

    @Override
    public int describeContents() {
        return 0;
    }

    private Exercice(Parcel in) {
        _id = in.readInt();
        titre = in.readString();
        image = in.readString();
        description = in.readString();
        _idFK = in.readInt();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(_id);
        out.writeString(titre);
        out.writeString(image);
        out.writeString(description);
        out.writeInt(_idFK);
    }

    public static final Parcelable.Creator<Exercice> CREATOR = new Parcelable.Creator<Exercice>() {
        public Exercice createFromParcel(Parcel in) {
            return new Exercice(in);
        }
        public Exercice[] newArray(int size) { return new Exercice[size]; }
    };
}
