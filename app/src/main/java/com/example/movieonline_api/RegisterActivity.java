package com.example.movieonline_api;

import android.content.Intent;
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
//import com.example.movieonline_api.repositories.userRepository;
import com.example.movieonline_api.retrofit2.DataClient;
import com.example.movieonline_api.retrofit2.retrofitClient;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText edttk1,edtemail1,edtmk1;
   private Button btndk1,btndn1;
   private TextView tvdn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        registerUser();
        login();
    }

    private void login() {
        tvdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentdn = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intentdn);
            }
        });
    }

    private void init(){
        edttk1 = findViewById(R.id.edtTK1);
        edtemail1 =findViewById(R.id.edtemail1);
        edtmk1 =findViewById(R.id.edtMK1);
        btndk1 =findViewById(R.id.btndk1);
        tvdn = findViewById(R.id.tvdn);

    }  private void registerUser() {
        btndk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edttk1.getText().toString().trim();
                String email = edtemail1.getText().toString().trim();
                String password = edtmk1.getText().toString().trim();

                // Kiểm tra điều kiện đầu vào
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gọi API với Retrofit
                DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
                Call<ResponseBody> call = dataClient.InsertData(username, email, password);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful() && response.body() != null) {
                                String responseBody = response.body().string(); // Phản hồi từ API
                                switch (responseBody) {
                                    case "1":
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish(); // Đóng RegisterActivity sau khi đăng ký thành công
                                        break;
                                    case "0":
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "2":
                                        Toast.makeText(RegisterActivity.this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "3":
                                        Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(RegisterActivity.this, "Lỗi không xác định!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}