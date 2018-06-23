package com.madfooat.task.ui.adapters.countries;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madfooat.task.R;
import com.madfooat.task.helpers.listeners.OnCountryItemClickListener;
import com.madfooat.task.modelLayer.models.CountriesModel;

import java.util.ArrayList;

/**
 * Created by MaysAtari on 6/23/2018.
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountriesViewHolder> {

    private ArrayList<CountriesModel> mCountriesList;
    private OnCountryItemClickListener onCountryItemClickListener;

    public CountriesAdapter(ArrayList<CountriesModel> mCountriesList,
                            OnCountryItemClickListener onCountryItemClickListener) {
        this.mCountriesList = new ArrayList<>(mCountriesList);
        this.onCountryItemClickListener = onCountryItemClickListener;
    }


    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_country_city,
                parent, false);

        final CountriesViewHolder countriesViewHolder = new CountriesViewHolder(view);

        view.setOnClickListener(v -> onCountryItemClickListener.onClick(v,
                countriesViewHolder.getAdapterPosition(),
                mCountriesList.get(countriesViewHolder.getAdapterPosition())));

        return countriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        CountriesModel countriesModel = mCountriesList.get(position);
        holder.populateData(countriesModel);
    }

    @Override
    public int getItemCount() {
        return mCountriesList.size();
    }

    public void animateTo(ArrayList<CountriesModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(ArrayList<CountriesModel> newModels) {
        for (int i = mCountriesList.size() - 1; i >= 0; i--) {
            final CountriesModel model = mCountriesList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<CountriesModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final CountriesModel model = newModels.get(i);
            if (!mCountriesList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<CountriesModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final CountriesModel model = newModels.get(toPosition);
            final int fromPosition = mCountriesList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private CountriesModel removeItem(int position) {
        final CountriesModel model = mCountriesList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    private void addItem(int position, CountriesModel model) {
        mCountriesList.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final CountriesModel model = mCountriesList.remove(fromPosition);
        mCountriesList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}
