
package com.example.model.api;

import com.example.util.testing.ForTestingPurposes;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings({"PMD.TooManyFields", "PMD.UnusedPrivateField"})
public class ShotResponse {

    @Nullable
    @SerializedName("id")
    private Integer id;

    @Nullable
    @SerializedName("title")
    private final String title;

    @Nullable
    @SerializedName("description")
    private String description;

    @Nullable
    @SerializedName("width")
    private Integer width;

    @Nullable
    @SerializedName("height")
    private Integer height;

    @Nullable
    @SerializedName("images")
    private final ImagesData imagesData;

    @Nullable
    @SerializedName("views_count")
    private Integer viewsCount;

    @Nullable
    @SerializedName("likes_count")
    private Integer likesCount;

    @Nullable
    @SerializedName("comments_count")
    private Integer commentsCount;

    @Nullable
    @SerializedName("attachments_count")
    private Integer attachmentsCount;

    @Nullable
    @SerializedName("rebounds_count")
    private Integer reboundsCount;

    @Nullable
    @SerializedName("buckets_count")
    private Integer bucketsCount;

    @Nullable
    @SerializedName("created_at")
    private String createdAt;

    @Nullable
    @SerializedName("updated_at")
    private String updatedAt;

    @Nullable
    @SerializedName("html_url")
    private String htmlUrl;

    @Nullable
    @SerializedName("attachments_url")
    private String attachmentsUrl;

    @Nullable
    @SerializedName("buckets_url")
    private String bucketsUrl;

    @Nullable
    @SerializedName("comments_url")
    private String commentsUrl;

    @Nullable
    @SerializedName("likes_url")
    private String likesUrl;

    @Nullable
    @SerializedName("projects_url")
    private String projectsUrl;

    @Nullable
    @SerializedName("rebounds_url")
    private String reboundsUrl;

    @Nullable
    @SerializedName("animated")
    private Boolean animated;

    @Nullable
    @SerializedName("tags")
    private List<String> tags;

    public ImagesData getImagesData() {
        return imagesData;
    }

    public String getTitle() {
        return title;
    }

    @ForTestingPurposes
    public ShotResponse(String title, ImagesData imagesData) {
        this.title = title;
        this.imagesData = imagesData;
    }

}
