package com.example.features.dashboard.model;

import com.example.model.Shot;
import com.example.model.api.ShotResponse;
import com.example.util.mvp.base.Mapper;

public class ShotMapper implements Mapper<ShotResponse, Shot> {

    @Override
    public Shot map(ShotResponse shotResponse) {

        return Shot.builder()
                .setTitle(shotResponse.getTitle())
                .setUrl(shotResponse.getImagesData().getTeaserImageUrl())
                .build();
    }

}
