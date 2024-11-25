package com.example.movieonline_api.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.movieonline_api.model.VideoHistory;

import java.util.List;

public class VideoHistoryAdapter extends RecyclerView.Adapter<VideoHistoryAdapter.ViewHolder> {

    private Context context;
    private List<VideoHistory> historyList;

    public VideoHistoryAdapter(Context context, List<VideoHistory> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoHistory history = historyList.get(position);
        holder.tvVideoTitle.setText(history.getVideoTitle());
        holder.tvTimestamp.setText(history.getTimestamp());
        Glide.with(context)
                .load(history.getThumbnail_url())
                .into(holder.imgVideoThumbnail);

        // Xử lý sự kiện khi nhấn vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("VIDEO_ID", history.getVideoId());
            intent.putExtra("VIDEO_TITLE", history.getVideoTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvVideoTitle, tvTimestamp;
        ImageView imgVideoThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVideoTitle = itemView.findViewById(R.id.tv_video_title);
            tvTimestamp = itemView.findViewById(R.id.tv_timestamp);
            imgVideoThumbnail = itemView.findViewById(R.id.img_video_thumbnail);

        }
    }
}