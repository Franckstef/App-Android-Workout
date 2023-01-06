package com.franckstefani.e22labo3.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailsExercice implements Parcelable {
    private int id;
    private String image;
    private String description;
    private String cible;
    private String execution;
    private String respiration;
    private String consigne;
    private String variante;
    private String conseil;
    private int _idFK;
    private String video;

    public DetailsExercice(String image, String description, String cible, String execution, String respiration, String consigne, String variante, String conseil, int _idFK, String video) {
        this.image = image;
        this.description = description;
        this.cible = cible;
        this.execution = execution;
        this.respiration = respiration;
        this.consigne = consigne;
        this.variante = variante;
        this.conseil = conseil;
        this._idFK = _idFK;
        this.video = video;
    }
    public DetailsExercice(int id, String image, String description, String cible, String execution, String respiration, String consigne, String variante, String conseil, int _idFK, String video) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.cible = cible;
        this.execution = execution;
        this.respiration = respiration;
        this.consigne = consigne;
        this.variante = variante;
        this.conseil = conseil;
        this._idFK = _idFK;
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.image = description;
    }

    public String getCible() {
        return cible;
    }

    public void setCible(String cible) {
        this.cible = cible;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getRespiration() {
        return respiration;
    }

    public void setRespiration(String respiration) {
        this.respiration = respiration;
    }

    public String getConsigne() {
        return consigne;
    }

    public void setConsigne(String consigne) {
        this.consigne = consigne;
    }

    public String getVariante() {
        return variante;
    }

    public void setVariante(String variante) {
        this.variante = variante;
    }

    public String getConseil() {
        return conseil;
    }

    public void setConseil(String conseil) {
        this.variante = conseil;
    }

    public int get_idFK() {
        return _idFK;
    }

    public void set_idFK(int _idFK) {
        this._idFK = _idFK;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private DetailsExercice(Parcel in) {
        id = in.readInt();
        image = in.readString();
        description = in.readString();
        cible = in.readString();
        execution = in.readString();
        respiration = in.readString();
        consigne = in.readString();
        variante = in.readString();
        conseil = in.readString();
        _idFK = in.readInt();
        video = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(image);
        out.writeString(description);
        out.writeString(cible);
        out.writeString(execution);
        out.writeString(respiration);
        out.writeString(consigne);
        out.writeString(variante);
        out.writeString(conseil);
        out.writeInt(_idFK);
        out.writeString(video);
    }

    public static final Parcelable.Creator<DetailsExercice> CREATOR = new Parcelable.Creator<DetailsExercice>() {
        public DetailsExercice createFromParcel(Parcel in) {
            return new DetailsExercice(in);
        }
        public DetailsExercice[] newArray(int size) { return new DetailsExercice[size]; }
    };
}
