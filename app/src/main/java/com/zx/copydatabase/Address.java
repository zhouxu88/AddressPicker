package com.zx.copydatabase;

/**
 * Created by 周旭 on 2017/1/21.
 * "省市区"模型类
 */

public class Address {

    private String province; //省
    private String city; //市
    private String area; //区

    public Address(String province, String city, String area) {
        this.province = province;
        this.city = city;
        this.area = area;
    }

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
