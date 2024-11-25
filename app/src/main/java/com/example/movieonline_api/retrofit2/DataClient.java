package com.example.movieonline_api.retrofit2;

import com.example.movieonline_api.model.History;
import com.example.movieonline_api.model.VideoHistory;
import com.example.movieonline_api.model.user;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {

    @FormUrlEncoded
    @POST("insertUser.php")
    Call<ResponseBody> InsertData(@Field("username") String username,
                                  @Field("email") String email,
                                  @Field("password") String password);
    @GET("get_user.php")
    Call<user> getUserDetails(@Query("user_id") int userId);

    @FormUrlEncoded
    @POST("update_user.php")
    Call<ResponseBody> updateUserDetails(
            @Field("user_id") int userId,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("gender") String gender
    );


    @Multipart
    @POST("upload_avatar.php")
    Call<Void> uploadAvatar(
            @Part MultipartBody.Part file,
            @Part("user_id") RequestBody userId
    );

@FormUrlEncoded
@POST("login.php")
Call<JsonObject> Login(
        @Field("username") String username,
        @Field("password") String password
);
    @FormUrlEncoded
    @POST("save_video_history.php")
    Call<Void> saveHistory(
            @Field("user_id") int userId,
            @Field("video_id") String videoId,
            @Field("video_title") String videoTitle,
            @Field("thumbnail_url") String thumbnailUrl
    );

    @FormUrlEncoded
    @POST("get_video_history.php")
    Call<List<VideoHistory>> getHistory(@Field("user_id") int userId);


    @POST("delete_history.php")
    @FormUrlEncoded
    Call<ResponseBody> deleteHistory(
            @Field("user_id") int userId,
            @Field("video_id") String videoId
    );



}
