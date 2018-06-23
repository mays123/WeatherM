package com.madfooat.task.helpers;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class Constants {

    private static final String COUNTRIES_API_KEY = "5159c51f500af6b1e699a6ec1a9ade96";
    private static final String WEATHER_API_KEY = "46fb4a449eb714d97f2ee87db379bed5";
    private static final String CITIES_USERNAME = "mays123";

    public static final String FEHRINHEIT_UNIT = "imperial";
    public static final String CELSIUS_UNIT = "metric";

    //the default url since it will be changed on every api request
    public static final String DEFAULT_BASE_URL = "http://madfooat.com/";

    public static final String COUNTRIES_BASE_URL
            = "https://battuta.medunes.net/api/country/all/?key=" + COUNTRIES_API_KEY;

    public static final String CITIES_BASE_URL
            = "http://api.geonames.org/searchJSON?username=" + CITIES_USERNAME +
            "&maxRows=1000&style=SHORT&fcode=PPLA&fcode=PPLC";

    public static final String WEATHER_BASE_URL
            = "http://api.openweathermap.org/data/2.5/weather?appid=" + WEATHER_API_KEY;


    public static final String WEATHER_IMAGES_BASE_URL = "http://openweathermap.org/img/w/";


    //bundle
    public static final String CITY_ID = "cityID";
    public static final String DATA = "data";
    public static final String ERROR = "error";
    public static final String COUNTRIES_LIST = "countriesList";
    public static final String CITIES_LIST = "citiesList";
}
