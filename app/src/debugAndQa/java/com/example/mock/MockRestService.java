package com.example.mock;

import android.support.annotation.DrawableRes;
import com.example.BuildConfig;
import com.example.R;
import com.example.model.api.ImagesData;
import com.example.model.api.ShotResponse;
import com.example.networking.RestService;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.List;

/**
 * Retrofit 2 mock mode. See https://youtu.be/t34AQlblSeE?t=53m32s for details or the u2020 project (https://github.com/JakeWharton/u2020).
 */
public class MockRestService implements RestService {

    private final BehaviorDelegate<RestService> delegate;

    public MockRestService(MockRetrofit mockRetrofit) {

        this.delegate = mockRetrofit.create(RestService.class);
    }

    @Override
    public Single<List<ShotResponse>> getShots() {

        return delegate.returningResponse(getShotResponsesList()).getShots();
    }

    @SuppressWarnings("checkstyle:magicnumber")
    private List<ShotResponse> getShotResponsesList() {

        List<ShotResponse> shotResponses = new ArrayList<>();
        ImagesData imagesData = new ImagesData(getLocalImageUri(R.drawable.dummy_shot_image));
        ShotResponse shotResponse = new ShotResponse("title", imagesData);
        for (int i = 0; i < 50; i++) {
            shotResponses.add(shotResponse);
        }
        return shotResponses;
    }

    private String getLocalImageUri(@DrawableRes int drawableResourceId) {

        return "android.resource://" + BuildConfig.APPLICATION_ID + "/" + drawableResourceId;
    }

}
