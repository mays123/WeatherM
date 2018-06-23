package com.madfooat.task.helpers.listeners;

import android.view.View;

import com.madfooat.task.modelLayer.models.cities.GeoName;

/**
 * Created by MaysAtari on 6/23/2018.
 */

public interface OnCityItemClickListener {
    void onClick(View view, int position, GeoName cityModel);
}
