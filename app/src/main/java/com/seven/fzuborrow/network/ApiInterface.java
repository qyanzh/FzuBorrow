package com.seven.fzuborrow.network;

import com.seven.fzuborrow.network.response.*;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.*;

public interface ApiInterface {

    @POST("user/register")
    @FormUrlEncoded
    Observable<RegisterResponse> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("schoolid") String schoolid,
            @Field("schoolpw") String schoolpw,
            @Field("address") String address
    );//注册

    @POST("login")
    @FormUrlEncoded
    Observable<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );//登录

    @POST("user/update")
    @FormUrlEncoded
    Observable<UserUpdateResponse> userUpdate(
            @Header("Token") String token,
            @Field("username") String username,
            @Field("password") String password,
            @Field("address") String address
    );//更新用户资料，包括用户名密码住址

    @GET("good/findGood")
    Observable<FindGoodResponse> findGood(
            @Header("XW-Token") String token,
            @Query("gid") long goodId
    );//根据id获取物品

    @GET("good/findAllGoods")
    Observable<FindAllGoodsResponse> findAllGood(
            @Header("XW-Token") String token,
            @Query("type") String type
    );//根据类别("good"/"room")获取物品

    @POST("request/apply")
    @FormUrlEncoded
    Observable<ApplyResponse> applyGood(
            @Header("XW-Token") String token,
            @Field("gid") long goodId,
            @Field("pid") long ownerId,
            @Field("reason") String reason,
            @Field("start_time") long startTime,
            @Field("end_time") long endTime
    );//申请租借

    @GET("request/findBeApply")
    Observable<FindBeApplyResponse> findBeApply(
            @Header("XW-Token") String token
    );//获取被申请列表

    @POST("request/handleApply")
    @FormUrlEncoded
    Observable<HandleApplyResponse> handleApply(
            @Header("XW-Token") String token,
            @Field("type") int type,
            @Field("rid") long applyId,
            @Field("gid") long goodId
    );//处理申请，1/0

    @GET("community/findAllDisc")
    Observable<FindAllDiscResponse> findAllDisc(
            @Header("XW-Token") String token
    );//查看所有帖子

    @GET("community/findAllComment")
    Observable<FindAllCommentResponse> findAllComments(
            @Header("XW-Token") String token,
            @Query("did") long discId
    );//获取帖子的回复

    @POST("community/publCommnet")
    @FormUrlEncoded
    Observable<PublishCommentResponse> publishComment(
            @Header("XW-Token") String token,
            @Field("did") long discId,
            @Field("username") String username,
            @Field("content") String content
    );//发帖子回复

    @POST("img/upload")
    @Multipart
    Observable<UploadFileResponse> uploadFile(
            @Header("XW-Token") String token,
            @Part MultipartBody.Part file,
            @Part("type") int type
    );//传文件 1/2/3

}
