package com.madfooat.task.helpers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.madfooat.task.R;
import com.madfooat.task.helpers.listeners.OnCitiesDialogListener;
import com.madfooat.task.helpers.listeners.OnCountriesDialogListener;
import com.madfooat.task.modelLayer.models.cities.GeoName;
import com.madfooat.task.modelLayer.models.CountriesModel;
import com.madfooat.task.ui.dialogs.CitiesDialog;
import com.madfooat.task.ui.dialogs.CountriesDialog;

import java.util.ArrayList;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class CustomDialogHelper {

    public static void createDialog(final Context ctx, String Title, String msg,
                                    String positiveBtn, String negativeBtn,
                                    DialogInterface.OnClickListener pos,
                                    DialogInterface.OnClickListener neg,
                                    boolean isCancelable) {


        final AlertDialog alertdialog = new AlertDialog.Builder(ctx, R.style.AlertDialogTheme)
                .setTitle(Title)
                .setMessage(msg)
                .setPositiveButton(positiveBtn, pos)
                .setNegativeButton(negativeBtn, neg)
                .setCancelable(isCancelable)
                .create();

        alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertdialog.getButton(Dialog.BUTTON_POSITIVE);
                alertdialog.getButton(Dialog.BUTTON_NEGATIVE);
            }
        });

        try {
            alertdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static CountriesDialog showCountriesDialog(FragmentManager manager,
                                                      ArrayList<CountriesModel> countriesList,
                                                      OnCountriesDialogListener onCountriesDialogListener){

        CountriesDialog customDialog = new CountriesDialog();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.COUNTRIES_LIST, countriesList);

        customDialog.setArguments(bundle);
        customDialog.setCallBack(onCountriesDialogListener);

        customDialog.show(manager, "CountriesDialog");
        return customDialog;
    }

    public static CitiesDialog showCitiesDialog(FragmentManager manager,
                                                   ArrayList<GeoName> citiesList,
                                                   OnCitiesDialogListener onCitiesDialogListener){

        CitiesDialog customDialog = new CitiesDialog();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.CITIES_LIST, citiesList);

        customDialog.setArguments(bundle);
        customDialog.setCallBack(onCitiesDialogListener);

        customDialog.show(manager, "CitiesDialog");
        return customDialog;
    }
}
