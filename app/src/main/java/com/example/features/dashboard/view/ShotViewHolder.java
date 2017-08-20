package com.example.features.dashboard.view;

import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import com.example.R;
import com.example.tools.images.ImageLoader;
import com.example.util.view.InjectedViewHolder;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

@AutoFactory(allowSubclasses = true)
public class ShotViewHolder extends InjectedViewHolder {

    @BindView(R.id.shot_adapter_item_image_view) ImageView shotImageView;

    private final ImageLoader imageLoader;

    public ShotViewHolder(View itemView, @Provided ImageLoader imageLoader) {
        super(itemView);
        this.imageLoader = imageLoader;
    }

    public void bindImage(String imageUrl, String title) {
        shotImageView.setContentDescription("Image with title: " + title);
        imageLoader.loadImage(imageUrl, shotImageView);
    }

}
