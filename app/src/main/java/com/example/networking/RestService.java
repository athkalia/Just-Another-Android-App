package com.example.networking;

import com.example.model.api.ShotResponse;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Single;

import java.util.List;

import static com.example.networking.AuthenticationInterceptor.DO_AUTHENTICATION;

public interface RestService {

    @GET("/v1/shots")
    @Headers(DO_AUTHENTICATION)
    Single<List<ShotResponse>> getShots();

}
