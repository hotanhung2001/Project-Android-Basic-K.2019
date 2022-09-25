package com.example.radioonline_hotanhung_ngothithanhngan;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Objects;

public class Channel implements Serializable {


    public String getUrlChannel() {
        return urlChannel;
    }

    public void setUrlChannel(String urlChannel) {
        this.urlChannel = urlChannel;
    }

    public Channel(String name, int image, int channelID, String url, int id) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(int idChannel) {
        this.idChannel = idChannel;
    }


    String name;
    Bitmap image;
    int idChannel;
    String urlChannel;

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    String linkImage;
    public Channel() {
        this.urlChannel = urlChannel;
    }

    public Channel(String name, int channelID, String url, int image, int idChannel) {
        this.idChannel = idChannel;
    }

    public Channel(String name, int i, Bitmap image, String url) {
        this.name = name;
        this.image = image;
    }

    public Channel(String name, Bitmap image, int idChannel) {
        this.name = name;
        this.image = image;
        this.idChannel = idChannel;
    }

    public Channel(String name, Bitmap image, String urlChannel) {
        this.name = name;
        this.image = image;
        this.urlChannel = urlChannel;


    }

    public Channel(String name, Bitmap image, int idChannel, String urlChannel) {
        this.name = name;
        this.image = image;
        this.idChannel = idChannel;
        this.urlChannel = urlChannel;
    }

    public Channel(String name, int idChannel, String urlChannel) {
        this.name = name;
        this.idChannel = idChannel;
        this.urlChannel = urlChannel;
    }
}
