package com.example.movieonline_api.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieonline_api.R;
import com.example.movieonline_api.adapter.SearchAdapter;
import com.example.movieonline_api.model.YouTubeResponse;
import com.example.movieonline_api.retrofit2.YouTubeApiService;
import com.example.movieonline_api.retrofit2.YouTubeRetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    private static final String API_KEY = "AIzaSyCE0pwgwulU8C1eq7AnS8OcH_3JeJ9sFJI";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(searchAdapter);

        return view;
    }

    // Phương thức tìm kiếm video
    public void searchVideos(String query) {
        YouTubeApiService apiService = YouTubeRetrofitClient.getClient().create(YouTubeApiService.class);
        apiService.searchVideos("snippet", query, 15, API_KEY).enqueue(new Callback<YouTubeResponse>() {
            @Override
            public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<YouTubeResponse.Item> videos = response.body().getItems();
                    searchAdapter = new SearchAdapter(getContext(), videos);
                    recyclerView.setAdapter(searchAdapter);
                } else {
                    Toast.makeText(getContext(), "Không có kết quả tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<YouTubeResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });
    }


}