package com.ucarry.developer.android.Model;

/**
 * Created by skadavath on 5/10/17.
 */

public class UserUpdateRequest {

    private String address;
    private String image;
    private String aadhar_link;

    public UserUpdateRequest(String address , String image,String aadhar_link) {

        this.address = address;
        this.image = image;
        this.aadhar_link = aadhar_link;

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
