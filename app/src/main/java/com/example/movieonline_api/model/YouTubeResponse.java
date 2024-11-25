package com.example.movieonline_api.model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class YouTubeResponse {

    @SerializedName("items")
    private ArrayList<Item> items;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public static class Item {
        private Snippet snippet;
        private Id id;

        public Snippet getSnippet() {
            return snippet;
        }

        public void setSnippet(Snippet snippet) {
            this.snippet = snippet;
        }

        public Id getId() {
            return id;
        }

        public void setId(Id id) {
            this.id = id;
        }

        public static class Snippet {
            private String title;
            private String description;
            private Thumbnails thumbnails;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Thumbnails getThumbnails() {
                return thumbnails;
            }

            public void setThumbnails(Thumbnails thumbnails) {
                this.thumbnails = thumbnails;
            }
        }

        public static class Thumbnails {
            private Default medium;

            public Default getMedium() {
                return medium;
            }

            public void setMedium(Default medium) {
                this.medium = medium;
            }

            public static class Default {
                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class Id {
            private String videoId;

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }
        }
    }
}
