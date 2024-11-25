package com.example.movieonline_api.retrofit2;

import com.example.movieonline_api.model.YouTubeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApiService {

    @GET("search")
    Call<YouTubeResponse> getVideos(
            @Query("part") String part,
            @Query("order") String order,
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey,
            @Query("regionCode") String regionCode // Thêm tham số regionCode
    );
    @GET("search")
    Call<YouTubeResponse> searchVideos(
            @Query("part") String part,
            @Query("q") String query,  // Từ khóa tìm kiếm
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );
    @GET("search")
    Call<YouTubeResponse> getRelatedVideos(
            @Query("part") String part,
            @Query("relatedToVideoId") String videoId, // ID của video đang xem
            @Query("type") String type, // Loại video (video)
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );
}

