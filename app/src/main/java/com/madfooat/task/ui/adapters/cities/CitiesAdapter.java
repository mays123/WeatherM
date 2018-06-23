package com.madfooat.task.ui.adapters.cities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madfooat.task.R;
import com.madfooat.task.helpers.listeners.OnCityItemClickListener;
import com.madfooat.task.modelLayer.models.cities.GeoName;

import java.util.ArrayList;

/**
 * Created by MaysAtari on 6/23/2018.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesViewHolder> {

    private ArrayList<GeoName> mCitiesList;
    private OnCityItemClickListener onCityItemClickListener;

    public CitiesAdapter(ArrayList<GeoName> mCitiesList,
                         OnCityItemClickListener onCityItemClickListener) {
        this.mCitiesList = new ArrayList<>(mCitiesList);
        this.onCityItemClickListener = onCityItemClickListener;
    }


    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_country_city,
                parent, false);

        final CitiesViewHolder citiesViewHolder = new CitiesViewHolder(view);

        view.setOnClickListener(v -> onCityItemClickListener.onClick(v,
                citiesViewHolder.getAdapterPosition(),
                mCitiesList.get(citiesViewHolder.getAdapterPosition())));

        return citiesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        GeoName cityModel = mCitiesList.get(position);
        holder.populateData(cityModel);
    }

    @Override
    public int getItemCount() {
        return mCitiesList.size();
    }

    public void animateTo(ArrayList<GeoName> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(ArrayList<GeoName> newModels) {
        for (int i = mCitiesList.size() - 1; i >= 0; i--) {
            final GeoName model = mCitiesList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<GeoName> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final GeoName model = newModels.get(i);
            if (!mCitiesList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<GeoName> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final GeoName model = newModels.get(toPosition);
            final int fromPosition = mCitiesList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private GeoName removeItem(int position) {
        final GeoName model = mCitiesList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    private void addItem(int position, GeoName model) {
        mCitiesList.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final GeoName model = mCitiesList.remove(fromPosition);
        mCitiesList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}
