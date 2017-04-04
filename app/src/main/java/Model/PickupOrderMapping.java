package Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


/**
 * Created by vel on 11/2/17.
 */
import com.yourapp.developer.karrierbay.BR;
public class PickupOrderMapping extends BaseObservable
{
    private String landmark;


    private String phone_2;


    public String getFullAdress() {
        fullAdress=name +"\n"+landmark+"\n"+address_line_1+"\n"+address_line_2+"\n"+phone_1;
        return fullAdress;
    }

    public void setFullAdress(String fullAdress) {
        this.fullAdress = fullAdress;
    }

    private String address_line_1;
private String fullAdress;
    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPhone_2() {
        return phone_2;
    }

    public void setPhone_2(String phone_2) {
        this.phone_2 = phone_2;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhone_1() {
        return phone_1;
    }

    public void setPhone_1(String phone_1) {
        this.phone_1 = phone_1;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getAuto_save() {
        return auto_save;
    }

    public void setAuto_save(String auto_save) {
        this.auto_save = auto_save;
    }

    private String pin;

    private String phone_1;

    private String address_line_2;

    private String name;

    private String auto_save;

//    @Bindable public String getLandmark ()
//    {
//        return landmark;
//    }
//
//    public void setLandmark (String landmark)
//    {
//        this.landmark = landmark;
//        notifyPropertyChanged(BR.landmark);
//    }
//
//    @Bindable
//    public String getPhone_2 ()
//    {
//        return phone_2;
//    }
//
//    public void setPhone_2 (String phone_2)
//    {
//        this.phone_2 = phone_2;
//        notifyPropertyChanged(BR.phone_2);
//    }
//
//    @Bindable public String getAddress_line_1 ()
//    {
//        return address_line_1;
//    }
//
//    public void setAddress_line_1 (String address_line_1)
//    {
//        this.address_line_1 = address_line_1;
//        notifyPropertyChanged(BR.address_line_1);
//    }
//
//    @Bindable public String getPin ()
//    {
//        return pin;
//    }
//
//    public void setPin (String pin)
//    {
//        this.pin = pin;
//        notifyPropertyChanged(BR.pin);
//    }
//
//    @Bindable public String getPhone_1 ()
//    {
//        return phone_1;
//    }
//
//    public void setPhone_1 (String phone_1)
//    {
//        this.phone_1 = phone_1;
//        notifyPropertyChanged(BR.phone_1);
//    }
//
//    @Bindable public String getAddress_line_2 ()
//    {
//        return address_line_2;
//    }
//
//    public void setAddress_line_2 (String address_line_2)
//    {
//        this.address_line_2 = address_line_2;
//        notifyPropertyChanged(BR.address_line_2);
//    }
//
//    @Bindable public String getName ()
//    {
//        return name;
//    }
//
//    public void setName (String name)
//    {
//        this.name = name;
//        notifyPropertyChanged(BR.name);
//    }
//
//    @Bindable public String getAuto_save ()
//    {
//        return auto_save;
//    }
//
//    public void setAuto_save (String auto_save)
//    {
//        this.auto_save = auto_save;
//        notifyPropertyChanged(BR.auto_save);
//    }


}

