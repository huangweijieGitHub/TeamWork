package com.example.teamwork.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by 伟捷。 on 2016/5/23.
 */
public class MyUser extends BmobUser {
    /***
     * 用户类
     */
    private  String username;
    private  String password;
    private  String mobilePhoneNumber;
    private  String email;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    @Override
    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
}
