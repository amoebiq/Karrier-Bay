package com.ucarry.developer.android.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skadavath on 7/26/17.
 */

public class ContactUs {

    @SerializedName("contact_no")
    private String number;
    private String issue;
    private String name;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
