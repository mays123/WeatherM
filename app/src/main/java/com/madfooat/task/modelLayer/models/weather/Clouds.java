package com.madfooat.task.modelLayer.models.weather;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.madfooat.task.modelLayer.models.BaseModel;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public class Clouds extends BaseModel {


    @SerializedName("all")
    @Expose
    private Double all;

    public Double getAll() {
        return all;
    }

    public void setAll(Double all) {
        this.all = all;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.all);
    }

    public Clouds() {
    }

    protected Clouds(Parcel in) {
        this.all = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<Clouds> CREATOR = new Creator<Clouds>() {
        @Override
        public Clouds createFromParcel(Parcel source) {
            return new Clouds(source);
        }

        @Override
        public Clouds[] newArray(int size) {
            return new Clouds[size];
        }
    };
}
