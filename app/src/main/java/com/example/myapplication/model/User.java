package com.example.myapplication.model;

/**
 * Created by JokerFish on 2017/7/18.
 */

public class User {
    private String mobile;
    private String password;
    private String captcha;

    public User(String mobile, String password) {
        this.mobile = mobile;
        this.captcha = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
