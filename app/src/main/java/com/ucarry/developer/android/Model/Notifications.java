package com.ucarry.developer.android.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skadavath on 7/24/17.
 */

public class Notifications {

    private String id;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("order_schedule_id")
    private String orderScheduleId;
    @SerializedName("notif_type")
    private String notifType;
    private String message;
    @SerializedName("ref_4")
    private String ref4;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("ref_1")
    private String ref_1;

    public String getRef_1() {
        return ref_1;
    }

    public void setRef_1(String ref_1) {
        this.ref_1 = ref_1;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderScheduleId() {
        return orderScheduleId;
    }

    public void setOrderScheduleId(String orderScheduleId) {
        this.orderScheduleId = orderScheduleId;
    }

    public String getNotifType() {
        return notifType;
    }

    public void setNotifType(String notifType) {
        this.notifType = notifType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRef4() {
        return ref4;
    }

    public void setRef4(String ref4) {
        this.ref4 = ref4;
    }
}
