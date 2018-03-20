package com.example.mkmkmk.projetmex.model;

/**
 * Created by mkmkmk on 19/03/2018.
 */

public class Villes {

    private String img;
    private String info;
    private double lat;
    private double lng;
    private String ville;

    public Villes()
    {

    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
