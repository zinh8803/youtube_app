package com.example.movieonline_api.model;

public class Video {
    private Id id;
    private Snippet snippet;

    public Id getId() {
        return id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public static class Id {
        private String videoId;

        public String getVideoId() {
            return videoId;
        }
    }

    public static class Snippet {
        private String title;
        private Thumbnails thumbnails;

        public String getTitle() {
            return title;
        }

        public Thumbnails getThumbnails() {
            return thumbnails;
        }
    }

    public static class Thumbnails {
        private Medium medium;

        public Medium getMedium() {
            return medium;
        }
    }

    public static class Medium {
        private String url;

        public String getUrl() {
            return url;
        }
    }
}

