package com.example.policeapp;

public class regHelperClass {
    String email,aadhar,pass,phoneNo;

    public regHelperClass(){}

    /*public regHelperClass(String email, String aadhar, String pass, String phoneNo) {
        this.aadhar = aadhar;
        this.email = email;
        this.phoneNo = phoneNo;
        this.pass = pass;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }*/


    public regHelperClass(String email, String aadhar, String pass, String phoneNo) {
        this.email = email;
        this.aadhar = aadhar;
        this.pass = pass;
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
