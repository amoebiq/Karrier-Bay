package Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import Utilities.Utility;

/**
 * Created by vel on 16/2/17.
 */

public class CarrierScheduleDetailAttributes extends BaseObservable {
    private String id;

    private String schedule_id;

    private String updated_at;

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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getReady_to_carry() {
        return ready_to_carry;
    }

    public void setReady_to_carry(String ready_to_carry) {
        this.ready_to_carry = ready_to_carry;
    }

    private String created_at;


    private String ready_to_carry;



    private String end_time;

    private String capacity;

    private String start_time;

    private String mode;

    public String getPassengercount() {

        return passengercount;
    }

    public void setPassengercount(String passengercount) {
        this.passengercount = passengercount;
    }

    private String passengercount;

    public String getEnd_time ()
    {
        return end_time;
    }

    public void setEnd_time (String end_time)
    {
        this.end_time = end_time;
    }

    public String getCapacity ()
    {

        return capacity;
    }

    public void setCapacity (String capacity)
    {
        this.capacity = capacity;
    }

    public String getStart_time ()
    {
        return start_time;
    }

    private String displayStartTime;

    public String getDisplayStartTime() {
        return  Utility.displayDateTime(start_time);
    }

    public void setDisplayStartTime(String displayStartTime) {
        this.displayStartTime = displayStartTime;
    }

    public String getDisplayEndTime() {
        return Utility.displayDateTime(end_time);
    }

    public void setDisplayEndTime(String displayEndTime) {
        this.displayEndTime = displayEndTime;
    }

    private String displayEndTime;

    public void setStart_time (String start_time)
    {
        this.start_time = start_time;
    }

    public String getMode ()
    {

        return mode;
    }

    public void setMode (String mode)
    {
        this.mode = mode;
    }

}
