package com.example.movieonline_api;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieonline_api.model.user;
import com.example.movieonline_api.retrofit2.DataClient;
import com.example.movieonline_api.retrofit2.retrofitClient;
import com.google.gson.JsonObject;
//import com.example.movieonline_api.repositories.userRepository;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvdk;
    EditText edttk,edtmk;
    Button btndn;
   // private userRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    init();
    register();
    loginUser();


    }
    private void init(){
        edttk = findViewById(R.id.edtTK);
        edtmk = findViewById(R.id.edtMK);

        btndn = findViewById(R.id.btnDN);
        tvdk = findViewById(R.id.tvdk);

    }
    private void register(){
        tvdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentdk = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentdk);
            }
        });
    }
    private void loginUser() {
        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edttk.getText().toString().trim();
                String password = edtmk.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
                Call<JsonObject> call = dataClient.Login(username, password);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            JsonObject jsonObject = response.body();
                            int status = jsonObject.get("status").getAsInt();

                            if (status == 1) {
                                // Lấy user_id từ phản hồi
                                int userId = jsonObject.get("user_id").getAsInt();

                                // Lưu user_id vào Intent để truyền đến màn hình tiếp theo
                                Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                Intent intentMovie = new Intent(MainActivity.this, MovieActivity.class);
                                intentMovie.putExtra("USER_ID", userId); // Gửi user_id qua Intent
                                SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("USER_ID", userId); // Lưu user_id
                                editor.apply();

                                startActivity(intentMovie);
                                finish();
                            } else if (status == 2) {
                                Toast.makeText(MainActivity.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Không đủ thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}