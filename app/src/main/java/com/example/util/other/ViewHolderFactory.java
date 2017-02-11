package com.example.util.other;

import android.view.View;

public interface ViewHolderFactory<VIEW_HOLDER> {

    VIEW_HOLDER createViewHolder(View view);

}
