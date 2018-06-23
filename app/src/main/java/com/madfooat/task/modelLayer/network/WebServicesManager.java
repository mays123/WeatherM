package com.madfooat.task.modelLayer.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.madfooat.task.helpers.Constants;
import com.madfooat.task.modelLayer.models.CountriesModel;
import com.madfooat.task.modelLayer.models.cities.CitiesModel;
import com.madfooat.task.modelLayer.models.weather.WeatherModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class WebServicesManager {

    public static final int COUNTRIES_TAG = 100;
    public static final int CITIES_TAG = 101;
    public static final int WEATHER_TAG = 102;

    public static void getCountriesData(final Handler successHandler, final Handler failureHandler) {
        RetrofitClient.getRetrofitApis()
                .getAllCountries(Constants.COUNTRIES_BASE_URL)
                .enqueue(new Callback<List<CountriesModel>>() {
                             @Override
                             public void onResponse(@NonNull Call<List<CountriesModel>> call,
                                                    @NonNull Response<List<CountriesModel>> response) {

                                 if (response.code() == 200) {
                                     ArrayList<CountriesModel> responseList =
                                             (ArrayList<CountriesModel>) response.body();

                                     Bundle bundle = new Bundle();
                                     Message message = successHandler.obtainMessage();
                                     message.what = COUNTRIES_TAG;
                                     bundle.putParcelableArrayList(Constants.DATA, responseList);
                                     message.setData(bundle);
                                     successHandler.sendMessage(message);
                                 } else {
                                     handleFailureCallBack(failureHandler, response.message(),
                                             COUNTRIES_TAG);
                                 }
                             }

                             @Override
                             public void onFailure(@NonNull Call<List<CountriesModel>> call,
                                                   @NonNull Throwable t) {
                                 handleFailureCallBack(failureHandler, t.getMessage(), COUNTRIES_TAG);
                             }
                         }
                );
    }

    public static void getCitiesByCountryCode(String countryCode, final Handler successHandler,
                                              final Handler failureHandler) {
        RetrofitClient.getRetrofitApis()
                .getCitiesByCountryCode(Constants.CITIES_BASE_URL, countryCode)
                .enqueue(new Callback<CitiesModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CitiesModel> call,
                                           @NonNull Response<CitiesModel> response) {

                        if (response.code() == 200) {
                            Bundle bundle = new Bundle();
                            Message message = successHandler.obtainMessage();
                            message.what = CITIES_TAG;
                            bundle.putParcelable(Constants.DATA, response.body());
                            message.setData(bundle);
                            successHandler.sendMessage(message);
                        } else {
                            handleFailureCallBack(failureHandler, response.message(),
                                    CITIES_TAG);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CitiesModel> call, @NonNull Throwable t) {
                        handleFailureCallBack(failureHandler, t.getMessage(), CITIES_TAG);
                    }
                });
    }

    public static void getWeatherDetails(int cityId, String unit, final Handler successHandler,
                                         final Handler failureHandler) {
        RetrofitClient.getRetrofitApis()
                .getWeatherByCityId(Constants.WEATHER_BASE_URL, cityId, unit)
                .enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherModel> call,
                                           @NonNull Response<WeatherModel> response) {

                        if (response.code() == 200) {
                            Bundle bundle = new Bundle();
                            Message message = successHandler.obtainMessage();
                            message.what = WEATHER_TAG;
                            bundle.putParcelable(Constants.DATA, response.body());
                            message.setData(bundle);
                            successHandler.sendMessage(message);
                        } else {
                            handleFailureCallBack(failureHandler, response.message(),
                                    WEATHER_TAG);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {
                        handleFailureCallBack(failureHandler, t.getMessage(), WEATHER_TAG);
                    }
                });
    }


    private static void handleFailureCallBack(Handler failureHandler, String receivedMessage, int tag) {
        Bundle bundle = new Bundle();
        Message message = failureHandler.obtainMessage();
        message.what = tag;
        bundle.putString(Constants.ERROR, receivedMessage);
        message.setData(bundle);
        failureHandler.sendMessage(message);
    }


}
