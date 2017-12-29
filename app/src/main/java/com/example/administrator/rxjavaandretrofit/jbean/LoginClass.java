package com.example.administrator.rxjavaandretrofit.jbean;

import java.io.Serializable;

/**
 * Created by gaoqiong on 2017/10/13.
 */

public class LoginClass implements Serializable{

    public LoginClass(String user_name, int user_mobile_type, String user_mobile_system, String user_mobile_token) {
        this.user_name = user_name;
        this.user_mobile_type = user_mobile_type;
        this.user_mobile_system = user_mobile_system;
        this.user_mobile_token = user_mobile_token;
    }

    /**
     * user_name : 18867151262
     * user_mobile_type : 0
     * user_mobile_system : 6.0
     * user_mobile_token : AlQMo42qOMbq8RMLOp2THwxFOy7f3zPJFnmEl96gR3Uf
     */



    private String user_name;
    private int user_mobile_type;
    private String user_mobile_system;
    private String user_mobile_token;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_mobile_type() {
        return user_mobile_type;
    }

    public void setUser_mobile_type(int user_mobile_type) {
        this.user_mobile_type = user_mobile_type;
    }

    public String getUser_mobile_system() {
        return user_mobile_system;
    }

    public void setUser_mobile_system(String user_mobile_system) {
        this.user_mobile_system = user_mobile_system;
    }

    public String getUser_mobile_token() {
        return user_mobile_token;
    }

    public void setUser_mobile_token(String user_mobile_token) {
        this.user_mobile_token = user_mobile_token;
    }
}
