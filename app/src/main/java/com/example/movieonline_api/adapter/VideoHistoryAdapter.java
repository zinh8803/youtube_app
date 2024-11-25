package com.example.movieonline_api.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.movieonline_api.model.VideoHistory;
import com.example.movieonline_api.retrofit2.DataClient;
import com.example.movieonline_api.retrofit2.retrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;


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
        holder.itemView.setOnLongClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Xóa Video")
                    .setMessage("Bạn có chắc chắn muốn xóa video này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        deleteHistoryItem(history, position); // Gọi hàm xóa video
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                    .show();
            return true;
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
    private void deleteHistoryItem(VideoHistory history, int position) {
        DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
        Call<ResponseBody> call = dataClient.deleteHistory(history.getUserId(), history.getVideoId());

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Video đã được xóa", Toast.LENGTH_SHORT).show();
                    historyList.remove(position); // Xóa khỏi danh sách cục bộ
                    notifyItemRemoved(position); // Cập nhật RecyclerView
                } else {
                    Toast.makeText(context, "Không thể xóa video", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}