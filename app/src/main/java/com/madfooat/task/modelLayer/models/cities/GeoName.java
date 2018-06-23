package com.madfooat.task.modelLayer.models.cities;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.madfooat.task.modelLayer.models.BaseModel;

/**
 * Created by MaysAtari on 6/21/2018.
 */

public class GeoName extends BaseModel implements Comparable<GeoName>{

    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("geonameId")
    @Expose
    private Integer geonameId;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("toponymName")
    @Expose
    private String toponymName;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("fcl")
    @Expose
    private String fcl;
    @SerializedName("fcode")
    @Expose
    private String fcode;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Integer geonameId) {
        this.geonameId = geonameId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToponymName() {
        return toponymName;
    }

    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getFcl() {
        return fcl;
    }

    public void setFcl(String fcl) {
        this.fcl = fcl;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lng);
        dest.writeValue(this.geonameId);
        dest.writeString(this.countryCode);
        dest.writeString(this.name);
        dest.writeString(this.toponymName);
        dest.writeString(this.lat);
        dest.writeString(this.fcl);
        dest.writeString(this.fcode);
    }

    public GeoName() {
    }

    protected GeoName(Parcel in) {
        this.lng = in.readString();
        this.geonameId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.countryCode = in.readString();
        this.name = in.readString();
        this.toponymName = in.readString();
        this.lat = in.readString();
        this.fcl = in.readString();
        this.fcode = in.readString();
    }

    public static final Creator<GeoName> CREATOR = new Creator<GeoName>() {
        @Override
        public GeoName createFromParcel(Parcel source) {
            return new GeoName(source);
        }

        @Override
        public GeoName[] newArray(int size) {
            return new GeoName[size];
        }
    };

    @Override
    public String toString() {
        return getName();
    }


    @Override
    public int compareTo(@NonNull GeoName o) {
        return  ((GeoName)o).getName().compareToIgnoreCase(getName())*-1;
    }
}
