package com.example.policeapp;

public class Model {
    private String imgUri;
    public Model()
    {}
    public Model(String imgUri){
        this.imgUri=imgUri;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
