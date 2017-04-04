package Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.EditText;

import com.google.gson.annotations.SerializedName;
import com.yourapp.developer.karrierbay.BR;


/**
 * Created by vel on 11/2/17.
 */

public class SenderOrder extends BaseObservable {
    private User user;
    private String toDate;
    private String fromDate;
    private String toTime;
    private String fromTime;
    private String senderItemInfo;
    private int spinWantToSendIdx = 1;
    private String total_amount;
    public boolean isSender;
    private String status;
    //For currier list
    private SenderOrderItemAttributes[] sender_order_item;
    private String id;
    private String order_id;
    private String sender_id;
    private String to_geo_long;
    private String updated_at;
    private String created_at;
    private String isInsured;
    private String coupon;

    @Bindable
    public String getToDate() {
        return toDate;
    }

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CarrierScheduleDetailAttributes getCarrier_schedule_detail() {
        return carrier_schedule_detail;
    }

    public void setCarrier_schedule_detail(CarrierScheduleDetailAttributes carrier_schedule_detail) {
        this.carrier_schedule_detail = carrier_schedule_detail;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
        notifyPropertyChanged(BR.toDate);
    }

    @Bindable
    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
        notifyPropertyChanged(BR.fromDate);
    }

    @Bindable
    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
        notifyPropertyChanged(BR.toTime);
    }

    @Bindable
    public String getFromTime() {
        return fromTime;
    }

    public String getSenderItemInfo() {
        if (isSender) {
            if (getSender_order_item_attributes()[0].getItem_type() != null && getSender_order_item_attributes()[0].getItem_type().equals(Constants.ARTICLE)) {
                senderItemInfo = getSender_order_item_attributes()[0].getItem_attributes().getWeight() + " Kg";
            } else {
                senderItemInfo = getSender_order_item_attributes()[0].getQuantity() + " Persons";

            }
        } else {
            if (getCarrierScheduleDetailAttributes().getMode() != null && getCarrierScheduleDetailAttributes().getMode().equals(Constants.ARTICLE)) {
                senderItemInfo = getCarrierScheduleDetailAttributes().getCapacity() + " kg";
            } else {
                senderItemInfo = getCarrierScheduleDetailAttributes().getPassengercount() + " Persons";
            }
        }

        return senderItemInfo;
    }

    public void setSenderItemInfo(String senderItemInfo) {
        this.senderItemInfo = senderItemInfo;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
        notifyPropertyChanged(BR.fromTime);

    }


    public CarrierScheduleDetailAttributes getCarrierScheduleDetailAttributes() {
        if (carrierScheduleDetailAttributes == null) {
            carrierScheduleDetailAttributes = new CarrierScheduleDetailAttributes();
        }
        return carrierScheduleDetailAttributes;
    }

    public void setCarrierScheduleDetailAttributes(CarrierScheduleDetailAttributes carrierScheduleDetailAttributes) {
        this.carrierScheduleDetailAttributes = carrierScheduleDetailAttributes;
    }

    @SerializedName("carrier_schedule_detail_attributes")
    private CarrierScheduleDetailAttributes carrierScheduleDetailAttributes;
    @SerializedName("carrier_schedule_detail")
    private CarrierScheduleDetailAttributes carrier_schedule_detail;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //fOr sender row
    public SenderOrderItemAttributes[] getSender_order_item() {
        if (sender_order_item_attributes == null) {

            sender_order_item = new SenderOrderItemAttributes[1];
            sender_order_item[0] = new SenderOrderItemAttributes();
        }
        return sender_order_item;
    }

    public void setSender_order_item(SenderOrderItemAttributes[] sender_order_item) {
        this.sender_order_item = sender_order_item;
    }


    public boolean isSender() {
        return isSender;
    }

    public void setSender(boolean sender) {
        isSender = sender;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getIsInsured() {
        return isInsured;
    }

    public void setIsInsured(String isInsured) {
        this.isInsured = isInsured;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }



    @Bindable
    public int getCarrierWanttosSendIdx() {
        return carrierWanttosSendIdx;
    }

    public void setCarrierWanttosSendIdx(int carrierWanttosSendIdx) {
        this.carrierWanttosSendIdx = carrierWanttosSendIdx;
        notifyPropertyChanged(BR.carrierWanttosSendIdx);
    }

    private String comments;
    private int carrierWanttosSendIdx;

    @Bindable
    public int getSpinWantToSendIdx() {
        return spinWantToSendIdx;
    }

    public void setSpinWantToSendIdx(int spinWantToSendIdx) {
        this.spinWantToSendIdx = spinWantToSendIdx;
        notifyPropertyChanged(BR.spinWantToSendIdx);
    }

    @SerializedName("pickup_order_mapping")
    private PickupOrderMapping pickupOrderMapping;

    private SenderOrderItemAttributes[] sender_order_item_attributes;
    @SerializedName("receiver_order_mapping")
    private ReceiverOrderMapping receiverOrderMapping;

    private String from_geo_lat;

    private String from_loc;

    private int from_loc_index;

    private String to_geo_lat;

    public String getTo_geo_long() {
        return to_geo_long;
    }

    public void setTo_geo_long(String to_geo_long) {
        this.to_geo_long = to_geo_long;
    }

    @Bindable
    public PickupOrderMapping getPickupOrderMapping() {
        if (pickupOrderMapping == null) {
            pickupOrderMapping = new PickupOrderMapping();
        }
        return pickupOrderMapping;
    }

    public void setPickupOrderMapping(PickupOrderMapping pickupOrderMapping) {
        this.pickupOrderMapping = pickupOrderMapping;


    }


    public SenderOrderItemAttributes[] getSender_order_item_attributes() {
        if (sender_order_item_attributes == null || sender_order_item_attributes.length == 0) {
            sender_order_item_attributes = new SenderOrderItemAttributes[1];
            sender_order_item_attributes[0] = new SenderOrderItemAttributes();
        }
        return sender_order_item_attributes;
    }

    public void setSender_order_item_attributes(SenderOrderItemAttributes[] sender_order_item_attributes) {
        this.sender_order_item_attributes = sender_order_item_attributes;
    }

    @Bindable
    public ReceiverOrderMapping getReceiverOrderMapping() {
        if (receiverOrderMapping == null) {
            receiverOrderMapping = new ReceiverOrderMapping();
        }

        return receiverOrderMapping;
    }

    @Bindable
    public void setReceiverOrderMapping(ReceiverOrderMapping receiverOrderMapping) {
        this.receiverOrderMapping = receiverOrderMapping;
    }


    public String getFrom_geo_lat() {
        return from_geo_lat;
    }

    public void setFrom_geo_lat(String from_geo_lat) {
        this.from_geo_lat = from_geo_lat;
    }

    @Bindable
    public String getFrom_loc() {
        return from_loc;
    }

    public void setFrom_loc(String from_loc) {
        this.from_loc = from_loc;
        notifyPropertyChanged(BR.from_loc);
    }

    public int getFrom_loc_index() {
        return from_loc_index;
    }

    public void setFrom_loc_index(int from_loc_index) {
        this.from_loc_index = from_loc_index;
    }

    public String getTo_geo_lat() {
        return to_geo_lat;
    }

    public void setTo_geo_lat(String to_geo_lat) {
        this.to_geo_lat = to_geo_lat;
    }


    private String to_loc;


    private String from_geo_long;

    @Bindable
    public String getTo_loc() {
        return to_loc;
    }


    public void setTo_loc(String to_loc) {
        this.to_loc = to_loc;
        notifyPropertyChanged(BR.to_loc);
    }

    //
    @Bindable
    public String getFrom_geo_long() {
        return from_geo_long;
    }

    public void setFrom_geo_long(String from_geo_long) {
        this.from_geo_long = from_geo_long;
    }


    public void onFocusChangeValidation(View v, boolean hasFocus) {
        if (!hasFocus) {
            EditText et = ((EditText) v);
            if (et.getText().toString().equals("")) {
                et.setError("Please fill it");
            } else {
                et.setError(null);
            }
        }
    }
}