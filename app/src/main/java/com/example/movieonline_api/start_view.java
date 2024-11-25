package com.example.movieonline_api;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class start_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_view);

        // Tìm logo và text
        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView2);

        // Hiệu ứng fade-in cho logo
        ObjectAnimator moveDown = ObjectAnimator.ofFloat(imageView, "translationY", -200f, 0f);
        moveDown.setDuration(1500); // Thời gian hiệu ứng: 1.5 giây
        moveDown.start();

        // Hiệu ứng mờ dần cho text
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
        fadeIn.setDuration(1500); // Thời gian hiệu ứng: 1.5 giây
        fadeIn.start();


        new Handler().postDelayed(() -> {
            // Chuyển đến màn hình chính (MainActivity)
            Intent intent = new Intent(start_view.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng SplashActivity
        }, 3000); // 3000ms = 3 giây
    }
}