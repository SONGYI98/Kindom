package com.example.kindom.helpMe;

import com.example.kindom.utils.FirebaseHandler;

import java.io.Serializable;
import java.util.Date;

/**
 * Represent a Help Me post
 */
public class HelpMePost implements Serializable {

    private long timeCreated;
    private String userUid;
    private String user;
    private String category;
    private String title;
    private String blkNo;
    private String date;
    private String time;
    private String description;

    public HelpMePost() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public HelpMePost(String category, String title, String blkNo, String date, String time, String description) {
        this.timeCreated = new Date().getTime();
        this.userUid = FirebaseHandler.getCurrentUserUid();
        this.user = FirebaseHandler.getCurrentUser().getDisplayName();
        this.category = category;
        this.title = title;
        this.blkNo = blkNo;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlkNo() {
        return blkNo;
    }

    public void setBlkNo(String blkNo) {
        this.blkNo = blkNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}