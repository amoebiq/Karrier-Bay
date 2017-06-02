package com.ucarry.developer.android.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skadavath on 6/2/17.
 */

public class CarrierScheduleRequest {

    @SerializedName("carrier_schedule")
    private CarrierSchedules carrierSchedules;

    public CarrierSchedules getCarrierSchedules() {
        return carrierSchedules;
    }

    public void setCarrierSchedules(CarrierSchedules carrierSchedules) {
        this.carrierSchedules = carrierSchedules;
    }
}
