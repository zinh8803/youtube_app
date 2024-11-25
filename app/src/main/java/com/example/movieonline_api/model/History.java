package com.example.movieonline_api.model;

public class History {
    private int userId;
    private String videoId;
    private String videoTitle;
    private float watchedTime;

    // Constructor, Getter và Setter
    public History(int userId, String videoId, String videoTitle, float watchedTime) {
        this.userId = userId;
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.watchedTime = watchedTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public float getWatchedTime() {
        return watchedTime;
    }
}
