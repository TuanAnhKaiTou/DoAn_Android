package com.ntanh.doan_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText mTenDangNhap, mMatKhau, mEmail, mReMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Initi();
    }

    void Initi() {
        mTenDangNhap = findViewById(R.id.edt_tendangnhap_forgotpass);
        mEmail = findViewById(R.id.edt_email_forgotpass);
        mMatKhau = findViewById(R.id.edt_matkhau_register);
        mReMatKhau = findViewById(R.id.edt_pre_password_register);

    }

    public void handleRegister(View view) {
        String ten_dang_nhap =  mTenDangNhap.getText().toString();
        String email =  mEmail.getText().toString();
        String mat_khau =  mMatKhau.getText().toString();
        String re_mat_khau =  mReMatKhau.getText().toString();
        // ktra nếu các trương
        if (!Ultilis.CheckRegister(ten_dang_nhap, mat_khau,email,re_mat_khau)) {
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else if (!re_mat_khau.equals(mat_khau)) {
            Toast.makeText(this, "Nhập lại mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> paramets = new HashMap<>();
            paramets.put("ten_dang_nhap", ten_dang_nhap);
            paramets.put("email",email);
            paramets.put("mat_khau", mat_khau);
            new UserAsyncTask(this, NetworkUtils.POST, paramets, "Register"){
                @Override
                public void ProgressJS(Context context, ProgressDialog progressDialog, String json) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getBoolean("success") == true) {
                            Toast.makeText(context, jsonObject.getString("notifi"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, jsonObject.getString("notifi"), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        Log.d("JSOn_EXception", "Failed ProgressJson");
                    }

                }
            }.execute("check-register");
        }
    }
}
