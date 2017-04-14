package com.example.util.view;

import android.view.View;

public interface ViewHolderFactory<VIEW_HOLDER> {

    VIEW_HOLDER createViewHolder(View view);

}
