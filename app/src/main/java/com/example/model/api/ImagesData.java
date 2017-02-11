package com.example.model.api;

import android.support.annotation.VisibleForTesting;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("PMD.UnusedPrivateField")
public class ImagesData {

    @SerializedName("hidpi")
    private String hidpiUrl;

    @SerializedName("normal")
    private String normalUrl;

    @SerializedName("teaser")
    private final String teaserUrl;

    public String getTeaserImageUrl() {

        return teaserUrl;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public ImagesData(String teaserUrl) {

        this.teaserUrl = teaserUrl;
    }

}
