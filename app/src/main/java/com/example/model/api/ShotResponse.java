
package com.example.model.api;

import com.example.util.testing.ForTestingPurposes;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings({"PMD.TooManyFields", "PMD.UnusedPrivateField"})
public class ShotResponse {

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private final String title;

    @SerializedName("description")
    private String description;

    @SerializedName("width")
    private Integer width;

    @SerializedName("height")
    private Integer height;

    @SerializedName("images")
    private final ImagesData imagesData;

    @SerializedName("views_count")
    private Integer viewsCount;

    @SerializedName("likes_count")
    private Integer likesCount;

    @SerializedName("comments_count")
    private Integer commentsCount;

    @SerializedName("attachments_count")
    private Integer attachmentsCount;

    @SerializedName("rebounds_count")
    private Integer reboundsCount;

    @SerializedName("buckets_count")
    private Integer bucketsCount;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("attachments_url")
    private String attachmentsUrl;

    @SerializedName("buckets_url")
    private String bucketsUrl;

    @SerializedName("comments_url")
    private String commentsUrl;

    @SerializedName("likes_url")
    private String likesUrl;

    @SerializedName("projects_url")
    private String projectsUrl;

    @SerializedName("rebounds_url")
    private String reboundsUrl;

    @SerializedName("animated")
    private Boolean animated;

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
