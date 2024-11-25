    package com.example.movieonline_api;
    
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.MotionEvent;
    import android.view.WindowManager;
    import android.widget.TextView;
    import android.widget.Toast;
    
    import androidx.activity.EdgeToEdge;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.movieonline_api.adapter.VideoHistoryAdapter;
    import com.example.movieonline_api.model.History;
    import com.example.movieonline_api.model.VideoHistory;
    import com.example.movieonline_api.model.Youtubedata;
    import com.example.movieonline_api.retrofit2.DataClient;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.example.movieonline_api.retrofit2.retrofitClient;

    import java.util.ArrayList;
    import java.util.List;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;

    public class VideoPlayerActivity extends AppCompatActivity {
        private YouTubePlayerView youTubePlayerView;
        private String videoId;
        private int userId;
       private String videoTitle,videoDescription;
        private boolean isPlaying = true;
        YouTubePlayerListener listener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_video_player);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
         TextView video_Title = findViewById(R.id.video_title);
          TextView video_Description = findViewById(R.id.description);
            Intent intent = getIntent();
            videoId = intent.getStringExtra("VIDEO_ID");

            videoDescription = intent.getStringExtra("description");
            videoTitle = intent.getStringExtra("VIDEO_TITLE");

         //   userId = intent.getIntExtra("USER_ID", -1);
            video_Title.setText(videoTitle);
            video_Description.setText(videoDescription);
            youTubePlayerView = findViewById(R.id.youtube_player_view);
    
            // Khởi tạo YouTubePlayerView
            getLifecycle().addObserver(youTubePlayerView);
            setupHistory();





            // Kiểm tra video ID và khởi tạo YouTubePlayer
            if (videoId != null) {
                youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
                    private YouTubePlayer currentPlayer;
    
                    @Override
                    public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String s) {
    
                    }
    
                    @Override
                    public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float v) {
    
                    }
    
                    @Override
                    public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float v) {
    
                    }
    
                    @Override
                    public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float second) {

                    }
    
                    @Override
                    public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError playerError) {
    
                    }
    
                    @Override
                    public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {
    
                    }
    
                    @Override
                    public void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackQuality playbackQuality) {
    
                    }
    
                    @Override
                    public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState playerState) {
                   //     saveWatchHistory(userId, videoId, videoTitle);
                    }
                   
                
    
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        if (currentPlayer != null) {
                            currentPlayer.pause();
                        }
    
                        // Phát video mới


                        DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);
                        youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());

                        currentPlayer = youTubePlayer;
                        youTubePlayer.loadVideo(videoId, 0);

                        setupHistory();
                    }
    
                    @Override
                    public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {
    
                    }


                });
            } else {
                Toast.makeText(this, "Không có video ID", Toast.LENGTH_SHORT).show();
            }
        }

        private void setupHistory() {
            RecyclerView recyclerView = findViewById(R.id.rv_history);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<VideoHistory> historyList = new ArrayList<>();
            VideoHistoryAdapter adapter = new VideoHistoryAdapter(this, historyList);
            recyclerView.setAdapter(adapter);

            fetchHistory(historyList, adapter);
        }
        private void fetchHistory(List<VideoHistory> historyList, VideoHistoryAdapter adapter) {
            // Lấy User ID từ SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("USER_ID", -1);

            if (userId == -1) {
                Toast.makeText(this, "Không thể lấy User ID!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi API lấy danh sách lịch sử
            DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
            Call<List<VideoHistory>> call = dataClient.getHistory(userId);

            call.enqueue(new Callback<List<VideoHistory>>() {
                @Override
                public void onResponse(Call<List<VideoHistory>> call, Response<List<VideoHistory>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        historyList.clear();
                        historyList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(VideoPlayerActivity.this, "Không có lịch sử xem!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<VideoHistory>> call, Throwable t) {
                    Toast.makeText(VideoPlayerActivity.this, "Lỗi khi tải lịch sử!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }