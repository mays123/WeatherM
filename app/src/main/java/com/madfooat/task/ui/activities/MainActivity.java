package com.madfooat.task.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.madfooat.task.R;
import com.madfooat.task.helpers.Constants;
import com.madfooat.task.helpers.CustomDialogHelper;
import com.madfooat.task.helpers.CustomProgressDialog;
import com.madfooat.task.helpers.HandlerManager;
import com.madfooat.task.helpers.SystemHelper;
import com.madfooat.task.modelLayer.models.CountriesModel;
import com.madfooat.task.modelLayer.models.cities.CitiesModel;
import com.madfooat.task.modelLayer.models.cities.GeoName;
import com.madfooat.task.modelLayer.network.WebServicesManager;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends BaseActivity {

    private TextView mCountryTxt;
    private TextView mCityTxt;
    private ArrayList<GeoName> mCitiesList = new ArrayList<>();
    private ArrayList<CountriesModel> mCountriesList = new ArrayList<>();
    private int selectedCityId = -1;
    private String selectedCountryCode = "";


    @Override
    protected void initUI() {
        getCountriesData();
    }

    private void getCountriesData() {
        if (!SystemHelper.isNetworkAvailable(MainActivity.this)) {
            CustomDialogHelper.createDialog(MainActivity.this, getString(R.string.no_net),
                    getString(R.string.try_again_question), getString(R.string.yes),
                    getString(R.string.no), (dialog, which) -> getCountriesData(),
                    (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    }, false);
            return;
        }

        CustomProgressDialog mProgressDialog = new CustomProgressDialog(MainActivity.this);
        mProgressDialog.showCustomDialog();

        Handler successHandler = new HandlerManager(msg -> {
            if (msg != null && msg.what == WebServicesManager.COUNTRIES_TAG) {
                mProgressDialog.dismissCustomDialog();

                ArrayList<CountriesModel> mTempList = msg.getData().getParcelableArrayList(Constants.DATA);

                if (mTempList == null) return;

                mCountriesList.addAll(mTempList);
            }
        }).getHandler();

        Handler failureHandler = new HandlerManager(msg -> {
            if (msg != null && msg.what == WebServicesManager.COUNTRIES_TAG) {
                mProgressDialog.dismissCustomDialog();

                String error = msg.getData().getString(Constants.ERROR);
                if (error != null && !error.equals(""))
                    showErrorDialog(error);
                else
                    showErrorDialog(getString(R.string.error_msg));
            }
        }).getHandler();

        WebServicesManager.getCountriesData(successHandler, failureHandler);
    }

    private void showErrorDialog(String message) {
        CustomDialogHelper.createDialog(MainActivity.this, getString(R.string.error),
                message, getString(R.string.done), "", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                },
                null, false);
    }

    @Override
    protected void initChildViews() {
        Button mSubmitBtn = findViewById(R.id.bMainSubmit);
        mCountryTxt = findViewById(R.id.tvCountry);
        mCityTxt = findViewById(R.id.tvCity);

        mCountryTxt.setOnTouchListener(this::showCountriesDialog);
        mCityTxt.setOnTouchListener(this::showCitiesDialog);
        mSubmitBtn.setOnClickListener(v -> openWeatherScreen());
    }

    private boolean showCountriesDialog(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (mCountriesList.size() > 0) {
                CustomDialogHelper.showCountriesDialog(getSupportFragmentManager(),
                        mCountriesList, countryModel -> {
                            selectedCountryCode = countryModel.getCode();
                            mCountryTxt.setText(countryModel.getName());

                            selectedCityId = -1;
                            mCitiesList.clear();
                            mCityTxt.setText(getString(R.string.city));
                            getCitiesByCountry(selectedCountryCode);
                        });
                return true;
            }
        }
        return false;
    }

    private boolean showCitiesDialog(View view, MotionEvent motionEvent) {
        if (selectedCountryCode.equals("")) {
            Toast.makeText(MainActivity.this, R.string.select_country_msg, Toast.LENGTH_SHORT).show();
        } else {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (mCitiesList.size() > 0) {
                    CustomDialogHelper.showCitiesDialog(getSupportFragmentManager(),
                            mCitiesList, cityModel -> {
                                mCityTxt.setText(cityModel.getName());
                                selectedCityId = cityModel.getGeonameId();
                            });
                    return true;
                }
            }
        }
        return false;
    }

    private void getCitiesByCountry(String countryCode) {
        if (!SystemHelper.isNetworkAvailable(MainActivity.this)) {
            CustomDialogHelper.createDialog(MainActivity.this, getString(R.string.no_net),
                    getString(R.string.try_again_question), getString(R.string.yes),
                    getString(R.string.no), (dialog, which) -> getCitiesByCountry(countryCode),
                    (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    }, false);
            return;
        }

        CustomProgressDialog mProgressDialog = new CustomProgressDialog(MainActivity.this);
        mProgressDialog.showCustomDialog();

        Handler successHandler = new HandlerManager(msg -> {
            if (msg != null && msg.what == WebServicesManager.CITIES_TAG) {
                mProgressDialog.dismissCustomDialog();

                CitiesModel mTempModel = msg.getData().getParcelable(Constants.DATA);

                if (mTempModel == null) return;

                mCitiesList.addAll(mTempModel.getGeoNameList());

                Collections.sort(mCitiesList);//ordering alphabetically
            }
        }).getHandler();

        Handler failureHandler = new HandlerManager(msg -> {
            if (msg != null && msg.what == WebServicesManager.CITIES_TAG) {
                mProgressDialog.dismissCustomDialog();

                String error = msg.getData().getString(Constants.ERROR);
                if (error != null && !error.equals(""))
                    showErrorDialog(error);
                else
                    showErrorDialog(getString(R.string.error_msg));
            }
        }).getHandler();

        WebServicesManager.getCitiesByCountryCode(countryCode, successHandler, failureHandler);
    }


    private void openWeatherScreen() {
        if (selectedCountryCode.equals("") || selectedCityId == -1) {
            Toast.makeText(MainActivity.this, getString(R.string.please_select_your_country_and_city),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CITY_ID, selectedCityId);

        Intent weatherIntent = new Intent(MainActivity.this, WeatherActivity.class);
        weatherIntent.putExtras(bundle);
        startActivity(weatherIntent);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.app_name);
    }

}
