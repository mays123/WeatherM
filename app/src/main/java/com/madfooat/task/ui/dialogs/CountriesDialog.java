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
import com.madfooat.task.helpers.listeners.OnCountriesDialogListener;
import com.madfooat.task.modelLayer.models.CountriesModel;
import com.madfooat.task.ui.adapters.countries.CountriesAdapter;

import java.util.ArrayList;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class CountriesDialog extends DialogFragment {

    private SearchView mSearchView;
    private RecyclerView mCountriesRecyclerView;
    private CountriesAdapter mCountriesAdapter;
    private Bundle bundle;
    private ArrayList<CountriesModel> mCountriesList = new ArrayList<>();
    private OnCountriesDialogListener onCountriesDialogListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityNonNull());
        bundle = getArguments();
        View view = View.inflate(getActivityNonNull(), R.layout.dialog_countries_cities, null);

        initVals(view);
        getBundleData();
        setUpSearchView();
        setUpCountriesRecyclerView();

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

    private void setUpCountriesRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivityNonNull(),
                LinearLayoutManager.VERTICAL, false);
        mCountriesRecyclerView.setLayoutManager(linearLayoutManager);
        mCountriesAdapter = new CountriesAdapter(mCountriesList, (view, position, countryModel) ->
                handleCountryItemSelection(position, countryModel));
        mCountriesRecyclerView.setAdapter(mCountriesAdapter);
    }

    private void handleCountryItemSelection(int position, CountriesModel countryModel) {
        onCountriesDialogListener.onCallBack(countryModel);
        this.dismiss();
    }

    private void initVals(View view) {
        mSearchView = view.findViewById(R.id.svCountriesCities);
        mCountriesRecyclerView = view.findViewById(R.id.rvCountriesCities);

        mSearchView.setOnQueryTextListener(searchQueryListener);
    }

    private void getBundleData() {
        mCountriesList = bundle.getParcelableArrayList(Constants.COUNTRIES_LIST);
    }

    private SearchView.OnQueryTextListener searchQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String newText) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            final ArrayList<CountriesModel> filteredModelList = new ArrayList<>();

            newText = newText.trim();

            if (newText.equals(" ")) {
                filteredModelList.addAll(mCountriesList);
            } else {
                newText = newText.toLowerCase();
                for (CountriesModel model : mCountriesList) {
                    final String text = model.toString().toLowerCase();
                    if (text.startsWith(newText)) {
                        filteredModelList.add(model);
                    }
                }
            }

            mCountriesAdapter.animateTo(filteredModelList);
            mCountriesRecyclerView.scrollToPosition(0);
            return true;
        }
    };

    public void setCallBack(OnCountriesDialogListener onCountriesDialogListener) {
        this.onCountriesDialogListener = onCountriesDialogListener;
    }


    protected Activity getActivityNonNull() {
        if (super.getActivity() != null) {
            return super.getActivity();
        } else {
            throw new RuntimeException("null returned from getActivity()");
        }
    }

}
