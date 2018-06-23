package com.madfooat.task.modelLayer.models;

import android.os.Parcelable;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public abstract class BaseModel implements Parcelable {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
