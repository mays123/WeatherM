package com.madfooat.task.modelLayer.models.cities;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.madfooat.task.modelLayer.models.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaysAtari on 6/21/2018.
 */

public class CitiesModel extends BaseModel{

    @SerializedName("totalResultsCount")
    @Expose
    private Integer totalResultsCount;
    @SerializedName("geonames")
    @Expose
    private List<GeoName> geoNameList = new ArrayList<>();

    public Integer getTotalResultsCount() {
        return totalResultsCount;
    }

    public void setTotalResultsCount(Integer totalResultsCount) {
        this.totalResultsCount = totalResultsCount;
    }

    public List<GeoName> getGeoNameList() {
        return geoNameList;
    }

    public void setGeoNameList(List<GeoName> geoNameList) {
        this.geoNameList = geoNameList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.totalResultsCount);
        dest.writeList(this.geoNameList);
    }

    public CitiesModel() {
    }

    protected CitiesModel(Parcel in) {
        this.totalResultsCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.geoNameList = new ArrayList<GeoName>();
        in.readList(this.geoNameList, GeoName.class.getClassLoader());
    }

    public static final Creator<CitiesModel> CREATOR = new Creator<CitiesModel>() {
        @Override
        public CitiesModel createFromParcel(Parcel source) {
            return new CitiesModel(source);
        }

        @Override
        public CitiesModel[] newArray(int size) {
            return new CitiesModel[size];
        }
    };
}
