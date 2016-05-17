package com.ykf.bishe.model.entity.element;

/**
 * Created by ykf on 16/5/12.
 */
public class Address {
    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String city;
}
