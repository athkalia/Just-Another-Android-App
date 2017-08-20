package com.example.features.dashboard.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.R;
import com.example.model.Shot;
import com.example.util.testing.ForTestingPurposes;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ShotsAdapter extends RecyclerView.Adapter<ShotViewHolder> {

    private List<Shot> shots;
    private final ShotViewHolderFactory shotViewHolderFactory;

    @Inject
    public ShotsAdapter(ShotViewHolderFactory shotViewHolderFactory) {
        this.shots = new ArrayList<>();
        this.shotViewHolderFactory = shotViewHolderFactory;
    }

    public void setData(List<Shot> shots) {
        Timber.v("Updating adapter with shots");
        this.shots = shots;
        notifyDataSetChanged();
    }

    @Override
    public ShotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shot_adapter_row_item, parent, false);
        return shotViewHolderFactory.create(view);
    }

    @Override
    public void onBindViewHolder(ShotViewHolder holder, int position) {
        Shot shot = shots.get(position);
        holder.bindImage(shot.getUrl(), shot.getTitle());
    }

    @Override
    public int getItemCount() {
        return shots.size();
    }

    @ForTestingPurposes
    List<Shot> getData() {
        return shots;
    }

}
