package com.example.policeapp;

public class HelpOtherHelperClass {
    String age,name,crmtype,address,gender;

    public HelpOtherHelperClass(){}

    public HelpOtherHelperClass(String age, String name, String crmtype, String address, String gender) {
        this.age = age;
        this.name = name;
        this.crmtype = crmtype;
        this.address = address;
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrmtype() {
        return crmtype;
    }

    public void setCrmtype(String crmtype) {
        this.crmtype = crmtype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
