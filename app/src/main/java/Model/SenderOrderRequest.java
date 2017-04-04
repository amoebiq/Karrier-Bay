package Model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;
import com.yourapp.developer.karrierbay.BR;

public class SenderOrderRequest extends BaseObservable {

    @SerializedName("sender_order")
    public SenderOrder SenderOrder;

    @SerializedName("carrier_schedule")
    public SenderOrder carrierOrder;

    public Model.SenderOrder getCarrierOrder() {
        return carrierOrder;
    }

    public void setCarrierOrder(Model.SenderOrder carrierOrder) {
        this.carrierOrder = carrierOrder;
    }




    public SenderOrder getSenderOrder() {
        if (SenderOrder == null) {
            SenderOrder = new SenderOrder();
        }
        return SenderOrder;
    }

    public void setSenderOrder(SenderOrder SenderOrder) {
        this.SenderOrder = SenderOrder;
        notifyPropertyChanged(BR.sender);
    }

}