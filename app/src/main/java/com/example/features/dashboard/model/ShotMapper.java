package com.example.features.dashboard.model;

import com.example.model.Shot;
import com.example.model.api.ShotResponse;
import com.example.util.mvp.base.Mapper;

import javax.inject.Inject;

public class ShotMapper implements Mapper<ShotResponse, Shot> {

    @Inject
    @SuppressWarnings("PMD.UnnecessaryConstructor")
    public ShotMapper() {
        // used by dagger
    }

    @Override
    public Shot map(ShotResponse shotResponse) {
        return Shot.builder()
                .setTitle(shotResponse.getTitle())
                .setUrl(shotResponse.getImagesData().getTeaserImageUrl())
                .build();
    }

}
