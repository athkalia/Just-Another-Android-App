package com.example.model.api;

import com.example.util.testing.ForTestingPurposes;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

@SuppressWarnings("PMD.UnusedPrivateField")
public class ImagesData {

    @Nullable
    @SerializedName("hidpi")
    private String hidpiUrl;

    @Nullable
    @SerializedName("normal")
    private String normalUrl;

    @Nullable
    @SerializedName("teaser")
    private final String teaserUrl;

    @Nullable
    public String getTeaserImageUrl() {
        return teaserUrl;
    }

    @ForTestingPurposes
    public ImagesData(String teaserUrl) {
        this.teaserUrl = teaserUrl;
    }

}
