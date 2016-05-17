package com.ykf.bishe.model.entity.element;

/**
 * Created by ykf on 16/5/12.
 */
public class Location {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    private String district;
    private String adcode;
}
