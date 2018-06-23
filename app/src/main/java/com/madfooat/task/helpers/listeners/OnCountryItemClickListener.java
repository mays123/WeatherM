package com.madfooat.task.helpers.listeners;

import android.view.View;

import com.madfooat.task.modelLayer.models.CountriesModel;

/**
 * Created by MaysAtari on 6/23/2018.
 */

public interface OnCountryItemClickListener {
    void onClick(View view, int position, CountriesModel countryModel);
}
