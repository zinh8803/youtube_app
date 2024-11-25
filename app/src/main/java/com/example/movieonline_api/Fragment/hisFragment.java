package com.example.movieonline_api.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieonline_api.R;
//import com.example.movieonline_api.adapter.HistoryAdapter;
//import com.example.movieonline_api.adapter.VideoHistoryAdapter;
import com.example.movieonline_api.adapter.VideoHistoryAdapter;
import com.example.movieonline_api.model.History;
import com.example.movieonline_api.model.VideoHistory;
import com.example.movieonline_api.retrofit2.DataClient;
import com.example.movieonline_api.retrofit2.retrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link hisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class hisFragment extends Fragment {
    private RecyclerView recyclerView;
    private VideoHistoryAdapter adapter;
    private List<VideoHistory> historyList;

    private int userId;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public hisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment hisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static hisFragment newInstance(String param1, String param2) {
        hisFragment fragment = new hisFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout từ fragment_history.xml
        View view = inflater.inflate(R.layout.fragment_his, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("USER_ID", -1); // -1 là giá trị mặc định nếu không tìm thấy

        if (userId == -1) {
            Toast.makeText(getContext(), "Không thể lấy User ID!", Toast.LENGTH_SHORT).show();
            Log.e("HistoryFragment", "User ID không hợp lệ!");
            return view;
        }

        Log.d("HistoryFragment", "User ID: " + userId);
        // Tìm RecyclerView từ View được trả về
        recyclerView = view.findViewById(R.id.rv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách và adapter
        historyList = new ArrayList<>();
        adapter = new VideoHistoryAdapter(getContext(), historyList);
        recyclerView.setAdapter(adapter);

        // Gọi API để lấy danh sách lịch sử
        fetchHistory(userId);
        requireContext().registerReceiver(updateHistoryReceiver, new IntentFilter("UPDATE_HISTORY"));
        return view;
    }
    private void fetchHistory(int userId) {
        DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
        Call<List<VideoHistory>> call = dataClient.getHistory(userId);

        call.enqueue(new Callback<List<VideoHistory>>() {
            @Override
            public void onResponse(Call<List<VideoHistory>> call, Response<List<VideoHistory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    historyList.clear();
                    historyList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                   // Log.d("HistoryFragment", "Received data: " + historyList.size() + " items");
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu lịch sử!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<VideoHistory>> call, Throwable t) {
                Log.e("HistoryFragment", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private final BroadcastReceiver updateHistoryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("HistoryFragment", "Nhận thông báo cập nhật lịch sử!");
            fetchHistory(userId); // Gọi API để cập nhật danh sách
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy đăng ký BroadcastReceiver
        requireContext().unregisterReceiver(updateHistoryReceiver);
    }
    public void refreshData() {
        Log.d("hisFragment", "Tab Lịch sử được chọn, làm mới dữ liệu...");
        fetchHistory(userId);
    }



}