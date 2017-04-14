package com.example.features.dashboard.view;

import android.view.View;
import com.example.tools.images.ImageLoader;
import com.example.util.view.ViewHolderFactory;

/**
 * Assisted injections in dagger via factories.
 */
public class ShotViewHolderFactory implements ViewHolderFactory<ShotViewHolder> {

    private final ImageLoader imageLoader;

    public ShotViewHolderFactory(ImageLoader imageLoader) {

        this.imageLoader = imageLoader;
    }

    @Override
    public ShotViewHolder createViewHolder(View view) {

        return new ShotViewHolder(view, imageLoader);
    }

}
