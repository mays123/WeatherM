package com.madfooat.task.modelLayer.models.weather;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.madfooat.task.modelLayer.models.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class WeatherModel extends BaseModel {

    @SerializedName("coord")
    @Expose
    private Coordinators coordinators;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = new ArrayList<>();
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private Long dt;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Integer cod;
    @SerializedName("message")
    @Expose
    private String message;

    public Coordinators getCoordinators() {
        return coordinators;
    }

    public void setCoordinators(Coordinators coordinators) {
        this.coordinators = coordinators;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.coordinators, flags);
        dest.writeTypedList(this.weather);
        dest.writeString(this.base);
        dest.writeParcelable(this.main, flags);
        dest.writeValue(this.visibility);
        dest.writeParcelable(this.wind, flags);
        dest.writeParcelable(this.clouds, flags);
        dest.writeValue(this.dt);
        dest.writeParcelable(this.sys, flags);
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeValue(this.cod);
        dest.writeString(this.message);
    }

    public WeatherModel() {
    }

    protected WeatherModel(Parcel in) {
        this.coordinators = in.readParcelable(Coordinators.class.getClassLoader());
        this.weather = in.createTypedArrayList(Weather.CREATOR);
        this.base = in.readString();
        this.main = in.readParcelable(Main.class.getClassLoader());
        this.visibility = (Integer) in.readValue(Integer.class.getClassLoader());
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.clouds = in.readParcelable(Clouds.class.getClassLoader());
        this.dt = (Long) in.readValue(Long.class.getClassLoader());
        this.sys = in.readParcelable(Sys.class.getClassLoader());
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.cod = (Integer) in.readValue(Integer.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Creator<WeatherModel> CREATOR = new Creator<WeatherModel>() {
        @Override
        public WeatherModel createFromParcel(Parcel source) {
            return new WeatherModel(source);
        }

        @Override
        public WeatherModel[] newArray(int size) {
            return new WeatherModel[size];
        }
    };
}
