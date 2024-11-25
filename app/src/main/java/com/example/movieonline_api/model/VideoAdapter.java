package com.example.movieonline_api.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieonline_api.R;
import com.example.movieonline_api.VideoPlayerActivity;
import com.example.movieonline_api.retrofit2.DataClient;
import com.example.movieonline_api.retrofit2.retrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private ArrayList<Youtubedata> videoList;

    public VideoAdapter(Context context, ArrayList<Youtubedata> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Youtubedata video = videoList.get(position);
        holder.titleTextView.setText(video.getTitle());
        holder.descriptionTextView.setText(video.getDescription());



        // Sử dụng Glide để tải ảnh thumbnail
        Glide.with(context)
                .load(video.getThumbnailUrl())
                .into(holder.thumbnailImageView);

        // Xử lý sự kiện nhấn vào item để xem video
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("VIDEO_ID", video.getVideoId());  // Gửi video ID cho Activity
            context.startActivity(intent);
        });
        holder.itemView.setOnClickListener(v -> {
            // Gửi user_id từ SharedPreferences hoặc Intent
            int userId = getUserId(); // Lấy user_id từ SharedPreferences hoặc nơi bạn lưu

            // Lưu lịch sử vào database
            saveVideoHistory(userId, video.getVideoId(), video.getTitle(),video.getThumbnailUrl());

            // Chuyển sang VideoPlayerActivity
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("VIDEO_ID", video.getVideoId());
            intent.putExtra("VIDEO_TITLE", video.getTitle());
            intent.putExtra("description", video.getDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView thumbnailImageView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.video_title);
            descriptionTextView = itemView.findViewById(R.id.video_description);
            thumbnailImageView = itemView.findViewById(R.id.video_thumbnail);

        }
    }
    private void saveVideoHistory(int userId, String videoId, String videoTitle,String thumbnailUrl) {
        if (userId == -1) {
            Toast.makeText(context, "Không thể lưu lịch sử: User ID không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
        Call<Void> call = dataClient.saveHistory(userId, videoId, videoTitle,thumbnailUrl);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Lưu lịch sử thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi khi lưu lịch sử!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Không thể kết nối tới server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm lấy user_id từ SharedPreferences (hoặc Intent, nếu cần)
    private int getUserId() {
        // Ví dụ: Lấy user_id từ SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("USER_ID", -1); // Trả về -1 nếu không có user_id
    }

}