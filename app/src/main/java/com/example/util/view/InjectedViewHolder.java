package com.example.util.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;

/**
 * Convenience class for injecting the views into the view holder. All view holders should extend this.
 */
public class InjectedViewHolder extends RecyclerView.ViewHolder {

    public InjectedViewHolder(View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
