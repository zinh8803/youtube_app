package com.example.movieonline_api.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieonline_api.MainActivity;

import com.example.movieonline_api.R;
import com.example.movieonline_api.model.FileUtils;
import com.example.movieonline_api.model.user;
import com.example.movieonline_api.retrofit2.DataClient;
import com.example.movieonline_api.retrofit2.retrofitClient;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    private TextView tvUsername, tvEmail;
    private EditText edtPassword, edtPhone;
    private RadioGroup rgGender;
    private ImageView imgAvatar;
    private Button btnSave,btnLogout;
    private Spinner spinnerGender;
    private int userId;
   private static final int PICK_IMAGE_REQUEST = 1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        tvUsername = view.findViewById(R.id.tv_username);
        tvEmail = view.findViewById(R.id.tv_email);
        edtPassword = view.findViewById(R.id.edt_password);
        edtPhone = view.findViewById(R.id.edt_phone);
//        rgGender = view.findViewById(R.id.rg_gender);
        spinnerGender = view.findViewById(R.id.spinner_gender);
        imgAvatar = view.findViewById(R.id.img_avatar);
        btnSave = view.findViewById(R.id.btn_save);
     //   btnChangeAvatar = view.findViewById(R.id.btn_change_avatar);

        btnLogout = view.findViewById(R.id.btn_logout);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("USER_ID", -1);
        Log.d("DEBUG", "User ID: " + userId);
        if (userId != -1) {
            loadUserData();
        } else {
            Toast.makeText(getContext(), "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }

        btnSave.setOnClickListener(v -> saveUserData());
        imgAvatar.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else {

                    openGallery();
                }
            } else {

                openGallery();
            }
        });

        setupGenderSpinner();
        btnLogout.setOnClickListener(v -> logout());
return view;
    }
    private void setupGenderSpinner() {
        String[] genders = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
    }
    private void loadUserData() {
        DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
        Call<user> call = dataClient.getUserDetails(userId);

        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                if (response.isSuccessful() && response.body() != null) {
                    user user = response.body();

                    tvUsername.setText(user.getUsername());
                    tvEmail.setText(user.getEmail());
                    edtPassword.setText(user.getPassword());
                    edtPhone.setText(user.getPhone());
                    String gender = user.getGender();
                    ArrayAdapter adapter = (ArrayAdapter) spinnerGender.getAdapter();
                    int position = adapter.getPosition(gender);
                    spinnerGender.setSelection(position);
//                    String gender = user.getGender();
//                    if (gender.equals("Nam")) ((RadioButton) rgGender.getChildAt(0)).setChecked(true);
//                    else if (gender.equals("Nữ")) ((RadioButton) rgGender.getChildAt(1)).setChecked(true);
//                    else ((RadioButton) rgGender.getChildAt(2)).setChecked(true);

                    String avatarUrl ="http://192.168.1.29/Movie_app/"+ user.getAvatar();
                    Glide.with(requireContext()).load(avatarUrl).circleCrop() .into(imgAvatar);
                } else {
                    Toast.makeText(getContext(), "Không thể tải thông tin", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData() {
        String password = edtPassword.getText().toString();
        String phone = edtPhone.getText().toString();
     //   String gender = ((RadioButton) getView().findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();
        String gender = spinnerGender.getSelectedItem().toString();
        DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
        Call<ResponseBody> call = dataClient.updateUserDetails(userId, password, phone, gender);

        if(password.isEmpty() || password.length() <6){
            Toast.makeText(getContext(), "mật khẩu phải lớn hơn 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi cập nhật thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            uploadAvatar(imageUri);
        }
    }

    private void uploadAvatar(Uri imageUri) {
        String filePath = FileUtils.getPath(getContext(), imageUri);
        if (filePath == null) {
            Toast.makeText(getContext(), "Không thể lấy đường dẫn tệp", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));

        DataClient dataClient = retrofitClient.getdata().create(DataClient.class);
        dataClient.uploadAvatar(body, userIdBody).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật ảnh thành công", Toast.LENGTH_SHORT).show();
                    loadUserData();
                }else {
                    Log.e("UPLOAD_ERROR", "Response code: " + response.code() + ", Error: " + response.errorBody());
                    Toast.makeText(getContext(), "Cập nhật ảnh thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("UPLOAD_DEBUG", "File path: " + filePath + ", User ID: " + userId);

                Log.e("UPLOAD_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Cập nhật ảnh thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (dùng READ_MEDIA_IMAGES)
            if (requireContext().checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 100);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0 đến Android 12
            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Quyền truy cập được cấp!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Quyền truy cập bị từ chối!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void logout() {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                .setNegativeButton("Không", (dialog, which) -> {
                    // Đóng dialog nếu chọn Không
                    dialog.dismiss();
                })
                .setPositiveButton("Có", (dialog, which) -> {
                    // Xóa SharedPreferences
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();

                    // Chuyển về MainActivity
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                })
                .show();
    }

}