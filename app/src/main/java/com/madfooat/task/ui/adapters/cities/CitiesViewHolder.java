package com.madfooat.task.ui.adapters.cities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.madfooat.task.R;
import com.madfooat.task.modelLayer.models.cities.GeoName;

/**
 * Created by MaysAtari on 6/23/2018.
 */

public class CitiesViewHolder extends RecyclerView.ViewHolder {

    private TextView mCityNameTxt;

    public CitiesViewHolder(View itemView) {
        super(itemView);

        mCityNameTxt = itemView.findViewById(R.id.tvName);
    }


    void populateData(GeoName cityModel) {
        mCityNameTxt.setText(cityModel.getName());
    }

}
