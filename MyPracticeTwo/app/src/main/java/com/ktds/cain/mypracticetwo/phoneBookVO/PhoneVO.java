package com.ktds.cain.mypracticetwo.phoneBookVO;

/**
 * Created by 206-013 on 2016-06-08.
 */
public class PhoneVO {
    private int pageNo;
    private String name;
    private String location;
    private String phoneNumber;

    public PhoneVO(int pageNo, String name, String location, String phoneNumber) {
        this.pageNo = pageNo;
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
