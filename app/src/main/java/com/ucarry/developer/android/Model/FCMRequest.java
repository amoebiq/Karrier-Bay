package com.ucarry.developer.android.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skadavath on 6/5/17.
 */

public class FCMRequest {

    @SerializedName("reg_id")
    private String regId;

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
