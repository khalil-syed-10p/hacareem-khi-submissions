package com.hacareem.finaldestination.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hacareem.finaldestination.R;
import com.hacareem.finaldestination.entities.base.AddressableEntity;
import com.hacareem.finaldestination.viewholders.LocationViewHolder;

import java.util.List;

/**
 * Created on 4/30/17.
 */

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    List<? extends AddressableEntity> list;
    Context context;

    public LocationRecyclerAdapter(List<? extends AddressableEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_location_view_holder, parent,false);
        return new LocationViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        if((position < 0) && (position >= list.size())) {
            return;
        }
        AddressableEntity entity= list.get(position);
        holder.onBind(entity);
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }
}
