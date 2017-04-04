package Model;

/**
 * Created by vel on 11/2/17.
 */

public final class SenderOrderResponse {
    public final long id;
    public final String order_id;
    public final String sender_id;
    public final String from_loc;
    public final String to_loc;
    public final String total_amount;
    public final String from_geo_lat;
    public final String from_geo_long;
    public final String to_geo_long;
    public final String to_geo_lat;
    public final String status;
    public final String comments;
    public final String created_at;
    public final String updated_at;
    public final Coupon coupon;
    public final boolean isInsured;
    public final Receiverordermapping receiver_order_mapping;
    public final SenderOrderItem sender_order_item[];
    public final Pickupordermapping pickup_order_mapping;

    public SenderOrderResponse(long id, String order_id, String sender_id, String from_loc, String to_loc, String total_amount, String from_geo_lat, String from_geo_long, String to_geo_long, String to_geo_lat, String status, String comments, String created_at, String updated_at, Coupon coupon, boolean isInsured, Receiverordermapping receiver_order_mapping, SenderOrderItem[] sender_order_item, Pickupordermapping pickup_order_mapping){
        this.id = id;
        this.order_id = order_id;
        this.sender_id = sender_id;
        this.from_loc = from_loc;
        this.to_loc = to_loc;
        this.total_amount = total_amount;
        this.from_geo_lat = from_geo_lat;
        this.from_geo_long = from_geo_long;
        this.to_geo_long = to_geo_long;
        this.to_geo_lat = to_geo_lat;
        this.status = status;
        this.comments = comments;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.coupon = coupon;
        this.isInsured = isInsured;
        this.receiver_order_mapping = receiver_order_mapping;
        this.sender_order_item = sender_order_item;
        this.pickup_order_mapping = pickup_order_mapping;
    }

    public static final class Coupon {

        public Coupon(){
        }
    }

    public static final class Receiverordermapping {
        public final long id;
        public final String reciever_id;
        public final String order_id;
        public final String sender_id;
        public final String name;
        public final String phone_1;
        public final String phone_2;
        public final String address_line_1;
        public final String address_line_2;
        public final State state;
        public final String landmark;
        public final String pin;
        public final String status;
        public final boolean auto_save;
        public final String created_at;
        public final String updated_at;

        public Receiverordermapping(long id, String reciever_id, String order_id, String sender_id, String name, String phone_1, String phone_2, String address_line_1, String address_line_2, State state, String landmark, String pin, String status, boolean auto_save, String created_at, String updated_at){
            this.id = id;
            this.reciever_id = reciever_id;
            this.order_id = order_id;
            this.sender_id = sender_id;
            this.name = name;
            this.phone_1 = phone_1;
            this.phone_2 = phone_2;
            this.address_line_1 = address_line_1;
            this.address_line_2 = address_line_2;
            this.state = state;
            this.landmark = landmark;
            this.pin = pin;
            this.status = status;
            this.auto_save = auto_save;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public static final class State {

            public State(){
            }
        }
    }

    public static final class SenderOrderItem {
        public final long id;
        public final String order_id;
        public final Item_attributes item_attributes;
        public final String unit_price;
        public final long quantity;
        public final String total_amount;
        public final String item_type;
        public final String item_subtype;
        public final String created_at;
        public final String updated_at;

        public SenderOrderItem(long id, String order_id, Item_attributes item_attributes, String unit_price, long quantity, String total_amount, String item_type, String item_subtype, String created_at, String updated_at){
            this.id = id;
            this.order_id = order_id;
            this.item_attributes = item_attributes;
            this.unit_price = unit_price;
            this.quantity = quantity;
            this.total_amount = total_amount;
            this.item_type = item_type;
            this.item_subtype = item_subtype;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public static final class Item_attributes {
            public final long length;
            public final long breadth;
            public final long height;
            public final double per_km_charge;
            public final long total_distance;
            public final double total_distance_charge;
            public final long total_weight_charge;
            public final long insurance_percent;
            public final double insurance_charge;
            public final long risk_charge;
            public final long service_charge_percent;
            public final double service_charge;
            public final double total_charge;

            public Item_attributes(long length, long breadth, long height, long per_km_charge, long total_distance, double total_distance_charge, long total_weight_charge, long insurance_percent, double insurance_charge, long risk_charge, long service_charge_percent, double service_charge, double total_charge){
                this.length = length;
                this.breadth = breadth;
                this.height = height;
                this.per_km_charge = per_km_charge;
                this.total_distance = total_distance;
                this.total_distance_charge = total_distance_charge;
                this.total_weight_charge = total_weight_charge;
                this.insurance_percent = insurance_percent;
                this.insurance_charge = insurance_charge;
                this.risk_charge = risk_charge;
                this.service_charge_percent = service_charge_percent;
                this.service_charge = service_charge;
                this.total_charge = total_charge;
            }
        }
    }

    public static final class Pickupordermapping {
        public final long id;
        public final String name;
        public final String order_id;
        public final String sender_id;
        public final String phone;
        public final String address_line_1;
        public final String address_line_2;
        public final State state;
        public final String landmark;
        public final String pin;
        public final String status;
        public final boolean auto_save;
        public final String created_at;
        public final String updated_at;

        public Pickupordermapping(long id, String name, String order_id, String sender_id, String phone, String address_line_1, String address_line_2, State state, String landmark, String pin, String status, boolean auto_save, String created_at, String updated_at){
            this.id = id;
            this.name = name;
            this.order_id = order_id;
            this.sender_id = sender_id;
            this.phone = phone;
            this.address_line_1 = address_line_1;
            this.address_line_2 = address_line_2;
            this.state = state;
            this.landmark = landmark;
            this.pin = pin;
            this.status = status;
            this.auto_save = auto_save;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public static final class State {

            public State(){
            }
        }
    }
}