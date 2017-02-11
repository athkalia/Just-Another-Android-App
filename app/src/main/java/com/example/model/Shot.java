package com.example.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@AutoValue
public abstract class Shot {

    @Nullable
    public abstract String getTitle();

    public abstract String getUrl();

    public static Builder builder() {

        return new AutoValue_Shot.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setTitle(@Nullable String newTitle);

        public abstract Builder setUrl(String newUrl);

        public abstract Shot build();
    }

}
