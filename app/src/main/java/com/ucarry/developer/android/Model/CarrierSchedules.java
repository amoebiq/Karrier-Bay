package com.ucarry.developer.android.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Observable;

/**
 * Created by skadavath on 5/16/17.
 */

public class CarrierSchedules extends Observable {

    private String id;
    private String schedule_id;
    private String from_loc;
    private String to_loc;
    private String from_geo_lat;
    private String to_geo_lat;
    private String status;
    private String comments;
    private String created_at;
    private String updated_at;
    private String stop_overs;
    private String from_geo_long;
    private String to_geo_long;
    private User user;
    @SerializedName("carrier_schedule_detail")
    private CarrierScheduleDetail carrierScheduleDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getFrom_loc() {
        return from_loc;
    }

    public void setFrom_loc(String from_loc) {
        this.from_loc = from_loc;
    }

    public String getTo_loc() {
        return to_loc;
    }

    public void setTo_loc(String to_loc) {
        this.to_loc = to_loc;
    }

    public String getFrom_geo_lat() {
        return from_geo_lat;
    }

    public void setFrom_geo_lat(String from_geo_lat) {
        this.from_geo_lat = from_geo_lat;
    }

    public String getTo_geo_lat() {
        return to_geo_lat;
    }

    public void setTo_geo_lat(String to_geo_lat) {
        this.to_geo_lat = to_geo_lat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStop_overs() {
        return stop_overs;
    }

    public void setStop_overs(String stop_overs) {
        this.stop_overs = stop_overs;
    }

    public String getFrom_geo_long() {
        return from_geo_long;
    }

    public void setFrom_geo_long(String from_geo_long) {
        this.from_geo_long = from_geo_long;
    }

    public String getTo_geo_long() {
        return to_geo_long;
    }

    public void setTo_geo_long(String to_geo_long) {
        this.to_geo_long = to_geo_long;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CarrierScheduleDetail getCarrierScheduleDetail() {
        return carrierScheduleDetail;
    }

    public void setCarrierScheduleDetail(CarrierScheduleDetail carrierScheduleDetail) {
        this.carrierScheduleDetail = carrierScheduleDetail;
    }
}
