package com.hacareem.finaldestination.viewholders;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hacareem.finaldestination.R;
import com.hacareem.finaldestination.entities.LocationDetails;
import com.hacareem.finaldestination.entities.base.AddressableEntity;

/**
 * Created on 4/30/17.
 */

public class LocationViewHolder extends RecyclerView.ViewHolder {

    TextView txtAddress;

    public LocationViewHolder(View itemView) {
        super(itemView);
        txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
    }

    public void onBind(AddressableEntity addressableEntity) {
        String address = addressableEntity.getAddress();
        if(TextUtils.isEmpty(address)) {
            address = itemView.getContext().getString(R.string.error_address);
        }
        txtAddress.setText(address);
    }
}
