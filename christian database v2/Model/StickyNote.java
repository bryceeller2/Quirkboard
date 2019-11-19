package com.example.ckraft.myapplication.Model;

/**
 * Created by theckraft on 8/24/16.
 */
public class StickyNote extends BaseClass{

    private String userID;
    private String text;
    private long latitude;
    private long longitude;
    private int stickiness;

    @Override
    public String getURLName() {
        return "StickyNote";
    }

    public StickyNote(){

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public int getStickiness() {
        return stickiness;
    }

    public void setStickiness(int stickiness) {
        this.stickiness = stickiness;
    }
}
