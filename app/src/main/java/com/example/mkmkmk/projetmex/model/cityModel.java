package com.example.mkmkmk.projetmex.model;

/**
 * Created by Emmanuel on 14/03/2018.
 */

public class cityModel {
    public String info;
    public String img;
    public float lat;
    public float lng;

    public cityModel (String info, String img, float lat, float lng) {
        this.info = info;
        this.img = img;
        this.lat = lat;
        this.lng = lng;
    }

    public cityModel() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
