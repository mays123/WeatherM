package com.madfooat.task.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;

import com.madfooat.task.R;
import com.madfooat.task.helpers.Constants;
import com.madfooat.task.helpers.listeners.OnCitiesDialogListener;
import com.madfooat.task.modelLayer.models.cities.GeoName;
import com.madfooat.task.ui.adapters.cities.CitiesAdapter;

import java.util.ArrayList;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class CitiesDialog extends DialogFragment {

    private SearchView mSearchView;
    private RecyclerView mCitiesRecyclerView;
    private CitiesAdapter mCitiesAdapter;
    private Bundle bundle;
    private ArrayList<GeoName> mCitiesList = new ArrayList<>();
    private OnCitiesDialogListener onCitiesDialogListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityNonNull());
        bundle = getArguments();
        View view = View.inflate(getActivityNonNull(), R.layout.dialog_countries_cities, null);

        initVals(view);
        getBundleData();
        setUpSearchView();
        setUpCitiesRecyclerView();

        builder.setView(view);
        Dialog dialog = builder.create();
        if (dialog.getWindow() != null)
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return builder.create();
    }

    private void setUpSearchView() {
        mSearchView.setIconifiedByDefault(false);//expanded by default
        mSearchView.setFocusable(false);//hide keyboard
        AutoCompleteTextView searchText = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setHintTextColor(ContextCompat.getColor(getActivityNonNull(), R.color.colorSearchViewHint));
    }

    private void setUpCitiesRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivityNonNull(),
                LinearLayoutManager.VERTICAL, false);
        mCitiesRecyclerView.setLayoutManager(linearLayoutManager);
        mCitiesAdapter = new CitiesAdapter(mCitiesList, (view, position, cityModel) ->
                handleCityItemSelection(position, cityModel));
        mCitiesRecyclerView.setAdapter(mCitiesAdapter);
    }

    private void handleCityItemSelection(int position, GeoName cityModel) {
        onCitiesDialogListener.onCallBack(cityModel);
        this.dismiss();
    }

    private void initVals(View view) {
        mSearchView = view.findViewById(R.id.svCountriesCities);
        mCitiesRecyclerView = view.findViewById(R.id.rvCountriesCities);

        mSearchView.setOnQueryTextListener(searchQueryListener);
    }

    private void getBundleData() {
        mCitiesList = bundle.getParcelableArrayList(Constants.CITIES_LIST);
    }

    private SearchView.OnQueryTextListener searchQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String newText) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            final ArrayList<GeoName> filteredModelList = new ArrayList<>();

            newText = newText.trim();

            if (newText.equals(" ")) {
                filteredModelList.addAll(mCitiesList);
            } else {
                newText = newText.toLowerCase();
                for (GeoName model : mCitiesList) {
                    final String text = model.toString().toLowerCase();
                    if (text.startsWith(newText)) {
                        filteredModelList.add(model);
                    }
                }
            }

            mCitiesAdapter.animateTo(filteredModelList);
            mCitiesRecyclerView.scrollToPosition(0);
            return true;
        }
    };

    public void setCallBack(OnCitiesDialogListener onCitiesDialogListener) {
        this.onCitiesDialogListener = onCitiesDialogListener;
    }

    protected Activity getActivityNonNull() {
        if (super.getActivity() != null) {
            return super.getActivity();
        } else {
            throw new RuntimeException("null returned from getActivity()");
        }
    }

}
