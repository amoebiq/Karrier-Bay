package com.ucarry.developer.android.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by skadavath on 7/11/17.
 */

public class UpdateOrderRequest implements Serializable{

    @SerializedName("order_id")
    private String orderId;
    private String status;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
