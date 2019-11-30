package com.ntanh.doan_android;

import android.widget.Toast;

public class Ultilis {
    public static boolean CheckRegister(String ten_dang_nhap, String mat_khau, String email, String re_mat_khau) {
        if (re_mat_khau.equals("") || mat_khau.equals("") || email.equals("") || ten_dang_nhap.equals(""))
           return false;
        return true;

    }

    public static boolean CheckForgotPass(String ten_dang_nhap, String email) {
        if (ten_dang_nhap.equals("") || email.equals(""))
            return false;
        return true;
    }
}
