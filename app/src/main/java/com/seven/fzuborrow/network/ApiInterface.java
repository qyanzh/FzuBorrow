package com.seven.fzuborrow.network;

import com.seven.fzuborrow.network.response.ApplyResponse;
import com.seven.fzuborrow.network.response.FindAllGoodsResponse;
import com.seven.fzuborrow.network.response.FindGoodResponse;
import com.seven.fzuborrow.network.response.LoginResponse;
import com.seven.fzuborrow.network.response.RegisterResponse;
import com.seven.fzuborrow.network.response.UserUpdateResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("user/register")
    @FormUrlEncoded
    Observable<RegisterResponse> register(@Field("username") String username,
                                          @Field("password") String password,
                                          @Field("schoolid") String schoolid,
                                          @Field("schoolpw") String schoolpw,
                                          @Field("address") String address);

    @POST("login")
    @FormUrlEncoded
    Observable<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String password);

    @POST("user/update")
    @FormUrlEncoded
    Observable<UserUpdateResponse> userUpdate(@Field("username") String username,
                                              @Field("password") String password,
                                              @Field("address") String address);

    @GET("good/findGood")
    Observable<FindGoodResponse> findGood(@Query("gid") long id);

    @GET("good/findAllGoods")
    Observable<FindAllGoodsResponse> findAllGood(@Header("token")String token,@Query("type") String type);

    @POST("request/apply")
    @FormUrlEncoded
    Observable<ApplyResponse> applyGood(@Field("gid") String username,
                                        @Field("pid") String password,
                                        @Field("schoolid") String schoolid,
                                        @Field("schoolpw") String schoolpw,
                                        @Field("address") String address);

}
