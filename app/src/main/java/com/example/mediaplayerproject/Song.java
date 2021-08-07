package com.example.mediaplayerproject;

import android.media.Image;
import android.widget.ImageView;

import java.io.Serializable;

public class Song implements Serializable {

    private String name;
    private String artist;
    private String Duration;
    private String link;
    private String pictureLink;

    public Song(String name, String artist, String duration, String link, String pictureLink) {
        this.name = name;
        this.artist = artist;
        Duration = duration;
        this.link = link;
        this.pictureLink = pictureLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }
}
