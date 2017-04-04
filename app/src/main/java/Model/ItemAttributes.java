package Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.yourapp.developer.karrierbay.BR;

/**
 * Created by vel on 11/2/17.
 */


public class ItemAttributes extends BaseObservable {

    private int breadthIndex;
    private String breadth;
    private String total_distance;
    private String service_charge_percent;
    private String service_charge;
    private String insurance_charge;
    private String per_km_charge;
    private String total_weight_charge;
    private String height;
    private String risk_charge;
    private String total_charge;
    private String total_distance_charge;
    private String insurance_percent;
    private String volumetricfullDetails;
    private String item_value;
    private String length;
    @SerializedName("item_weight")
    private String weight;
    private  String grand_total;


    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }



    public String getVolumetricfullDetails() {
        volumetricfullDetails = length + "  " + breadth + "  " + height + "\n";
        return volumetricfullDetails;
    }

    public void setVolumetricfullDetails(String volumetricfullDetails) {
        this.volumetricfullDetails = volumetricfullDetails;
    }




    @Bindable
    public String getBreadth() {
        return breadth;
    }

    public void setBreadth(String breadth) {
        this.breadth = breadth;
        notifyPropertyChanged(BR.breadth);
    }

    @Bindable
    public String getHeight() {
        return height;
    }

    public String getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(String total_distance) {
        this.total_distance = total_distance;
    }

    public String getService_charge_percent() {
        return service_charge_percent;
    }

    public void setService_charge_percent(String service_charge_percent) {
        this.service_charge_percent = service_charge_percent;
    }

    public String getService_charge() {
        return service_charge;
    }

    public void setService_charge(String service_charge) {
        this.service_charge = service_charge;
    }

    public String getInsurance_charge() {
        return insurance_charge;
    }

    public void setInsurance_charge(String insurance_charge) {
        this.insurance_charge = insurance_charge;
    }

    public String getPer_km_charge() {
        return per_km_charge;
    }

    public void setPer_km_charge(String per_km_charge) {
        this.per_km_charge = per_km_charge;
    }

    public String getTotal_weight_charge() {
        return total_weight_charge;
    }

    public void setTotal_weight_charge(String total_weight_charge) {
        this.total_weight_charge = total_weight_charge;
    }

    public String getRisk_charge() {
        return risk_charge;
    }

    public void setRisk_charge(String risk_charge) {
        this.risk_charge = risk_charge;
    }

    @Bindable
    public String getTotal_charge() {
        return total_charge;
    }

    public void setTotal_charge(String total_charge) {
        this.total_charge = total_charge;
        notifyPropertyChanged(BR.total_charge);
    }

    @Bindable
    public String getTotal_distance_charge() {
        return total_distance_charge;
    }

    public void setTotal_distance_charge(String total_distance_charge) {
        this.total_distance_charge = total_distance_charge;
        notifyPropertyChanged(BR.total_distance_charge);
    }

    public String getInsurance_percent() {
        return insurance_percent;
    }

    public void setInsurance_percent(String insurance_percent) {
        this.insurance_percent = insurance_percent;
    }

    public void setHeight(String height) {

        this.height = height;
        notifyPropertyChanged(BR.height);
    }

    public String getItem_value() {
        return item_value;
    }

    public void setItem_value(String item_value) {
        this.item_value = item_value;
    }

    @Bindable
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
        notifyPropertyChanged(BR.length);
    }

    @Bindable
    public String getWeight() {
        if (weight == null) {
            weight = "";
        }
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
        notifyPropertyChanged(BR.weight);
    }


}

