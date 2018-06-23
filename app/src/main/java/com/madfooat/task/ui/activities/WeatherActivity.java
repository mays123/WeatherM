package com.madfooat.task.ui.activities;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.madfooat.task.R;
import com.madfooat.task.helpers.Constants;
import com.madfooat.task.helpers.CustomDialogHelper;
import com.madfooat.task.helpers.CustomProgressDialog;
import com.madfooat.task.helpers.HandlerManager;
import com.madfooat.task.helpers.SystemHelper;
import com.madfooat.task.modelLayer.models.weather.WeatherModel;
import com.madfooat.task.modelLayer.network.WebServicesManager;
import com.squareup.picasso.Picasso;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class WeatherActivity extends BaseActivity {

    private TextView mTempDescriptionTxt, mTempValueTxt, mCountryCityTxt, mWindTxt, mHumidityTxt,
            mPressureTxt;
    private ImageView mTempImg;
    private LinearLayout mMainLayout;
    private int cityId;

    @Override
    protected void initUI() {

        getBundleData();
        getWeatherData(Constants.CELSIUS_UNIT);
    }

    private void getBundleData() {
        cityId = getIntent().getIntExtra(Constants.CITY_ID, -1);
    }

    private void getWeatherData(String unit) {
        if (!SystemHelper.isNetworkAvailable(WeatherActivity.this)) {
            CustomDialogHelper.createDialog(WeatherActivity.this, getString(R.string.no_net),
                    getString(R.string.try_again_question), getString(R.string.yes),
                    getString(R.string.no), (dialog, which) -> getWeatherData(unit),
                    (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    }, false);
            return;
        }

        CustomProgressDialog mProgressDialog = new CustomProgressDialog(WeatherActivity.this);
        mProgressDialog.showCustomDialog();

        Handler successHandler = new HandlerManager(msg -> {
            if (msg != null && msg.what == WebServicesManager.WEATHER_TAG) {
                mProgressDialog.dismissCustomDialog();

                WeatherModel mTempModel = msg.getData().getParcelable(Constants.DATA);

                if (mTempModel == null) return;

                if (mTempModel.getCod() == 200) {
                    setWeatherData(mTempModel);
                } else {
                    showErrorDialog(mTempModel.getMessage());
                }
            }
        }).getHandler();

        Handler failureHandler = new HandlerManager(msg -> {
            if (msg != null && msg.what == WebServicesManager.WEATHER_TAG) {
                mProgressDialog.dismissCustomDialog();

                String error = msg.getData().getString(Constants.ERROR);
                if (error != null && !error.equals(""))
                    showErrorDialog(error);
                else
                    showErrorDialog(getString(R.string.error_msg));
            }
        }).getHandler();

        WebServicesManager.getWeatherDetails(cityId, unit, successHandler, failureHandler);
    }

    private void showErrorDialog(String message) {
        CustomDialogHelper.createDialog(WeatherActivity.this, getString(R.string.error),
                message, getString(R.string.done), "", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                },
                null, false);
    }

    private void setWeatherData(WeatherModel weatherModel) {
        mTempDescriptionTxt.setText(weatherModel.getWeather().get(0).getMain());

        mCountryCityTxt.setText(String.format("%s, %s", weatherModel.getName(),
                weatherModel.getSys().getCountry()));

        int tempValue = (int) Math.floor(weatherModel.getMain().getTemp());
        mTempValueTxt.setText(getString(R.string.degree, String.valueOf(tempValue)));

        mWindTxt.setText(getString(R.string.wind_value, String.valueOf(weatherModel.getWind().getSpeed())));

        int humidityValue = (int) Math.floor(weatherModel.getMain().getHumidity());
        mHumidityTxt.setText(String.valueOf(humidityValue + "%"));

        mPressureTxt.setText(getString(R.string.pressure_value,
                String.valueOf(weatherModel.getMain().getPressure())));

        Picasso.with(WeatherActivity.this)
                .load(Constants.WEATHER_IMAGES_BASE_URL
                        + weatherModel.getWeather().get(0).getIcon() + ".png")
                .into(mTempImg);

        setMainLayoutBackgroundColor(weatherModel.getWeather().get(0).getId());
    }

    private void setMainLayoutBackgroundColor(Integer conditionId) {
        if (conditionId >= 200 && conditionId <= 232) {//thunderstorm
            mMainLayout.setBackgroundColor(ContextCompat.getColor(WeatherActivity.this,
                    R.color.ultraLightGrey));
        } else if (conditionId >= 300 && conditionId <= 321) {//drizzle
            mMainLayout.setBackgroundColor(ContextCompat.getColor(WeatherActivity.this,
                    R.color.lightGrey));
        } else if (conditionId >= 500 && conditionId <= 531) {//rain
            mMainLayout.setBackgroundColor(ContextCompat.getColor(WeatherActivity.this,
                    R.color.ultraLightBlue));
        } else if (conditionId >= 600 && conditionId <= 622) {//snow
            mMainLayout.setBackgroundColor(ContextCompat.getColor(WeatherActivity.this,
                    R.color.lightBlue));
        } else if (conditionId >= 701 && conditionId <= 781) {//atmosphere
            mMainLayout.setBackgroundColor(ContextCompat.getColor(WeatherActivity.this,
                    R.color.blueGrey));
        } else if (conditionId == 800) {//clear
            mMainLayout.setBackgroundColor(ContextCompat.getColor(WeatherActivity.this,
                    R.color.lightOrange));
        } else if (conditionId >= 801 && conditionId <= 804) {//clouds
            mMainLayout.setBackgroundColor(ContextCompat.getColor(WeatherActivity.this,
                    R.color.orange));
        }

    }

    @Override
    protected void initChildViews() {
        mTempDescriptionTxt = findViewById(R.id.tvTempDescription);
        mTempValueTxt = findViewById(R.id.tvTempVal);
        mCountryCityTxt = findViewById(R.id.tvWeatherCountryCity);
        mWindTxt = findViewById(R.id.tvWindValue);
        mHumidityTxt = findViewById(R.id.tvHumidityValue);
        mPressureTxt = findViewById(R.id.tvPressureValue);
        mTempImg = findViewById(R.id.ivTemp);
        RadioGroup mUnitsRGroup = findViewById(R.id.rgUnits);
        mMainLayout = findViewById(R.id.llMain);

        mUnitsRGroup.setOnCheckedChangeListener((group, checkedId) -> {
            View radioButton = group.findViewById(checkedId);
            int index = group.indexOfChild(radioButton);

            if (index == 0) {
                getWeatherData(Constants.CELSIUS_UNIT);
            } else if (index == 1) {
                getWeatherData(Constants.FEHRINHEIT_UNIT);
            }

        });
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.app_name);
    }
}
