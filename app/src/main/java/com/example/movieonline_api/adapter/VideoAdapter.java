//package com.example.movieonline_api.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.movieonline_api.R;
//import com.example.movieonline_api.model.Youtubedata;
//
//import java.util.ArrayList;
//
//public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.YouTubePostHolder> {
//private ArrayList<Youtubedata> data;
//private Context mContext =null;
//    public VideoAdapter(ArrayList<Youtubedata> data, Context mContext) {
//        this.data = data;
//        this.mContext = mContext;
//    }
//
//    @NonNull
//    @Override
//    public YouTubePostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.youtube_layout,parent,false);
//        YouTubePostHolder holder = new YouTubePostHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull YouTubePostHolder holder, int position) {
//    TextView title = holder.title;
//    TextView ViewDes = holder.ViewDes;
//        TextView ViewDate= holder.ViewDate;
//        ImageView imageThumb= holder.imageThumb;
//        Youtubedata item = data.get(position);
//        title.setText(item.getTitle());
//        ViewDes.setText(item.getDescription());
//        ViewDate.setText(item.getPublishedAt());
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public static class YouTubePostHolder extends RecyclerView.ViewHolder {
//        TextView title;
//        TextView ViewDes;
//        TextView ViewDate;
//        ImageView imageThumb;
//
//        public YouTubePostHolder(@NonNull View itemView) {
//            super(itemView);
//            this.title = itemView.findViewById(R.id.title);
//            this.ViewDes = itemView.findViewById(R.id.ViewDes);
//            this.ViewDate = itemView.findViewById(R.id.ViewDate);
//            this.imageThumb = itemView.findViewById(R.id.imageThumb);
//        }
//    }
//}
//
