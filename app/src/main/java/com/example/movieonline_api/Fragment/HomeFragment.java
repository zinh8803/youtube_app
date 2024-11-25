package com.example.movieonline_api.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieonline_api.R;

import com.example.movieonline_api.adapter.VideoAdapter;
import com.example.movieonline_api.model.YouTubeResponse;
import com.example.movieonline_api.model.Youtubedata;

import com.example.movieonline_api.retrofit2.YouTubeApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
 // private static final String API_KEY = "AIzaSyCE0pwgwulU8C1eq7AnS8OcH_3JeJ9sFJI";
   // String API_KEY = getString(R.string.api_key);


    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private ArrayList<Youtubedata> videoList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        videoList = new ArrayList<>();
        adapter = new VideoAdapter(getContext(), videoList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fetchYouTubeVideos();

        return view;
    }

    private void fetchYouTubeVideos() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        String API_KEY = sharedPreferences.getString("API_KEY", "default_value");
        if(API_KEY == null || API_KEY.isEmpty()) {
            Toast.makeText(getContext(), "API Key không được cấu hình", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YouTubeApiService apiService = retrofit.create(YouTubeApiService.class);

        // Sử dụng tham số 'regionCode' để lấy video thịnh hành của Việt Nam
        apiService.getVideos("snippet", "date", 20, API_KEY, "VN")
                .enqueue(new Callback<YouTubeResponse>() {
                    @Override
                    public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            for (YouTubeResponse.Item item : response.body().getItems()) {
                                String videoId = item.getId().getVideoId();
                                String title = item.getSnippet().getTitle();
                                String description = item.getSnippet().getDescription();
                                String thumbnailUrl = item.getSnippet().getThumbnails().getMedium().getUrl();


                                videoList.add(new Youtubedata(videoId, title, description, thumbnailUrl));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<YouTubeResponse> call, Throwable t) {

                        t.printStackTrace();
                    }
                });
    }


}
