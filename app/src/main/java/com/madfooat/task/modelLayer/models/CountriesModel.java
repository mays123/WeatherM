package com.madfooat.task.modelLayer.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MaysAtari on 6/21/2018.
 */

public class CountriesModel extends BaseModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.code);
    }

    public CountriesModel() {
    }

    protected CountriesModel(Parcel in) {
        this.name = in.readString();
        this.code = in.readString();
    }

    public static final Creator<CountriesModel> CREATOR = new Creator<CountriesModel>() {
        @Override
        public CountriesModel createFromParcel(Parcel source) {
            return new CountriesModel(source);
        }

        @Override
        public CountriesModel[] newArray(int size) {
            return new CountriesModel[size];
        }
    };

    @Override
    public String toString() {
        return getName();
    }
}
