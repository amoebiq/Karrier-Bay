package com.ucarry.developer.android.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skadavath on 7/25/17.
 */

public class OrderCompletion {

    @SerializedName("order_id")
    private String orderId;
    private String pin;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
