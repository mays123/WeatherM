package com.madfooat.task.modelLayer.network;

import com.madfooat.task.modelLayer.models.cities.CitiesModel;
import com.madfooat.task.modelLayer.models.CountriesModel;
import com.madfooat.task.modelLayer.models.weather.WeatherModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public interface APIs {

    @GET
    Call<List<CountriesModel>> getAllCountries(@Url String url);

    @GET
    Call<CitiesModel> getCitiesByCountryCode(@Url String url, @Query("country") String countryCode);

    @GET
    Call<WeatherModel> getWeatherByCityId(@Url String url, @Query("id") int cityId,
                                          @Query("units") String unitType);

}
