package com.example.tools.images;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import timber.log.Timber;

/**
 * Class works as our image loading library wrapper. Allows for easier testing and replacement of the image loading library.
 */
public class ImageLoader {

    private final Context context;

    public ImageLoader(Context context) {

        this.context = context;
    }

    public void loadImage(String imageUri, ImageView viewToLoadImageIn) {

        Timber.d("Fetching image from uri: %s", imageUri);
        Uri uri = Uri.parse(imageUri);
        Glide.with(context)
                .load(uri)
                .fitCenter()
                .into(viewToLoadImageIn);
    }

}
