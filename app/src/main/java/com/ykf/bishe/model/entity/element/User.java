package com.ykf.bishe.model.entity.element;

import cn.bmob.v3.BmobObject;

/**
 * Created by ykf on 16/5/16.
 */
public class User extends BmobObject{
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;
    private String password;
}
