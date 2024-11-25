package com.example.movieonline_api.retrofit2;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class YouTubeRetrofitClient {

    // Base URL cho YouTube API
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson để chuyển đổi JSON
                    .build();
        }
        return retrofit;
    }
}
