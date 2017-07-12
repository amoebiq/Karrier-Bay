package com.ucarry.developer.android.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skadavath on 5/10/17.
 */

public class UserUpdateRequest {

    private String address;
    private String image;
    private String aadhar_link;
    private String phone;
    private String email;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SerializedName("bank_detail")
    private BankDetail bankDetail;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserUpdateRequest(String address , String image, String aadhar_link , BankDetail bankDetail) {

        this.address = address;
        this.image = image;
        this.aadhar_link = aadhar_link;
        this.bankDetail = bankDetail;

    }

    public UserUpdateRequest() {


    }

    public BankDetail getBankDetail() {
        return bankDetail;
    }

    public void setBankDetail(BankDetail bankDetail) {
        this.bankDetail = bankDetail;
    }

    public String getAadhar_link() {
        return aadhar_link;
    }

    public void setAadhar_link(String aadhar_link) {
        this.aadhar_link = aadhar_link;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
