package com.example.movieonline_api.model;

public class VideoHistory {
    private int id;
    private int user_id;
    private String video_id;
    private String video_title;
    private String timestamp;
    private String thumbnail_url;
    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getVideoId() {
        return video_id;
    }

    public void setVideoId(String video_id) {
        this.video_id = video_id;
    }

    public String getVideoTitle() {
        return video_title;
    }

    public void setVideoTitle(String video_title) {
        this.video_title = video_title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}


