//package com.example.movieonline_api.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.movieonline_api.R;
//import com.example.movieonline_api.VideoPlayerActivity;
//import com.example.movieonline_api.model.History;
//
//import java.util.List;
//
//public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
//
//    private Context context;
//    private List<History> historyList;
//
//    public HistoryAdapter(Context context, List<History> historyList) {
//        this.context = context;
//        this.historyList = historyList;
//    }
//
//    @NonNull
//    @Override
//    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
//        return new HistoryViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
//        History history = historyList.get(position);
//        holder.tvVideoTitle.setText(history.getVideoTitle());
//
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, VideoPlayerActivity.class);
//            intent.putExtra("VIDEO_ID", history.getVideoId());
//            intent.putExtra("USER_ID", history.getUserId());
//            intent.putExtra("VIDEO_TITLE", history.getVideoTitle());
//            context.startActivity(intent);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return historyList.size();
//    }
//
//    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
//        TextView tvVideoTitle;
//
//        public HistoryViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvVideoTitle = itemView.findViewById(R.id.tv_video_title);
//        }
//    }
//}