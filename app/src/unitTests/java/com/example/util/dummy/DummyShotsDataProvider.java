package com.example.util.dummy;

import com.example.model.Shot;
import com.example.model.api.ImagesData;
import com.example.model.api.ShotResponse;

import java.util.ArrayList;
import java.util.List;

public class DummyShotsDataProvider {

    public List<Shot> getShotList() {
        List<Shot> shots = new ArrayList<>();
        shots.add(Shot.builder().setTitle("title 1").setUrl("url 1").build());
        shots.add(Shot.builder().setTitle("title 2").setUrl("url 2").build());
        return shots;
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public List<Shot> getShotList(int listSize) {
        List<Shot> shots = new ArrayList<>();
        for (int i = 1; i <= listSize; i++) {
            shots.add(Shot.builder().setTitle("title " + i).setUrl("url " + i).build());
        }
        return shots;
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public List<ShotResponse> getShotResponsesList(int listSize) {
        List<ShotResponse> shotResponses = new ArrayList<>();
        for (int i = 1; i <= listSize; i++) {
            ImagesData imagesData = new ImagesData("url " + i);
            String title = "title " + i;
            ShotResponse shotResponse = new ShotResponse(title, imagesData);
            shotResponses.add(shotResponse);
        }
        return shotResponses;
    }

    public ShotResponse getShotsResponse(String title, String url) {
        ImagesData imagesData = new ImagesData(url);
        return new ShotResponse(title, imagesData);
    }

}
