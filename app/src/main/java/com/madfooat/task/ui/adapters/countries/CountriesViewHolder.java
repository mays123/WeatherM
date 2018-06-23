package com.madfooat.task.ui.adapters.countries;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.madfooat.task.R;
import com.madfooat.task.modelLayer.models.CountriesModel;

/**
 * Created by MaysAtari on 6/23/2018.
 */

public class CountriesViewHolder extends RecyclerView.ViewHolder {

    private TextView mCountryNameTxt;

    public CountriesViewHolder(View itemView) {
        super(itemView);

        mCountryNameTxt = itemView.findViewById(R.id.tvName);
    }


    void populateData(CountriesModel countryModel) {
        mCountryNameTxt.setText(countryModel.getName());
    }

}
