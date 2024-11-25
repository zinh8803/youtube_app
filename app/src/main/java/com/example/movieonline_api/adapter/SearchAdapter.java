package com.example.movieonline_api.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieonline_api.R;
import com.example.movieonline_api.VideoPlayerActivity;
import com.example.movieonline_api.model.YouTubeResponse;
import com.example.movieonline_api.retrofit2.DataClient;
import com.example.movieonline_api.retrofit2.retrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VideoViewHolder> {
    private Context context;
    private List<YouTubeResponse.Item> videoList;

    public SearchAdapter(Context context, List<YouTubeResponse.Item> videoList) {
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
        YouTubeResponse.Item video = videoList.get(position);
        holder.titleTextView.setText(video.getSnippet().getTitle());
        holder.descriptionTextView.setText(video.getSnippet().getDescription());

        // Sử dụng Glide để tải ảnh thumbnail
        Glide.with(context)
                .load(video.getSnippet().getThumbnails().getMedium().getUrl())
                .into(holder.thumbnailImageView);

        // Xử lý sự kiện nhấn vào item để xem video
        holder.itemView.setOnClickListener(v -> {
            String videoId = video.getId().getVideoId();
            String videoTitle = video.getSnippet().getTitle();
            String thumbnailUrl = video.getSnippet().getThumbnails().getMedium().getUrl();

            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("VIDEO_ID", video.getId().getVideoId());
            intent.putExtra("VIDEO_TITLE", video.getSnippet().getTitle());
            intent.putExtra("description", video.getSnippet().getDescription());// Gửi video ID cho Activity
            saveVideoToHistory(getUserIdFromSharedPreferences(), videoId, videoTitle, thumbnailUrl);
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
    private void saveVideoToHistory(int userId, String videoId, String videoTitle, String thumbnailUrl) {
        if (userId == -1) {
            Log.e("SearchAdapter", "User ID không hợp lệ!");
            return;
        }

        DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
        Call<Void> call = dataClient.saveHistory(userId, videoId, videoTitle, thumbnailUrl);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("SearchAdapter", "Lưu lịch sử thành công!");
                } else {
                    Log.e("SearchAdapter", "Lỗi khi lưu lịch sử!");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("SearchAdapter", "Không thể kết nối tới server: " + t.getMessage());
            }
        });
    }

    // Lấy user_id từ SharedPreferences
    private int getUserIdFromSharedPreferences() {
        return context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
                .getInt("USER_ID", -1);
    }
}