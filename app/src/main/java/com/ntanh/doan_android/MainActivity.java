package com.ntanh.doan_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText mTenDangNhap, mMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Anh xa view
        InitiView();
    }

    public void InitiView() {
        mTenDangNhap = findViewById(R.id.edt_tendangnhap_forgotpass);
        mMatKhau = findViewById(R.id.edt_matkhau_register);
    }
    public void loginView(View view) {
        String ten_dang_nhap = mTenDangNhap.getText().toString();
        String mat_khau = mMatKhau.getText().toString();

        Map<String,String> paramets = new HashMap<>();
        paramets.put("ten_dang_nhap", ten_dang_nhap);
        paramets.put("mat_khau", mat_khau);

        if (!Ultilis.CheckForgotPass(ten_dang_nhap, mat_khau)) {
            Toast.makeText(this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            new UserAsyncTask(this, NetworkUtils.POST, paramets, "Login", "Waiting for login...") {
                @Override
                public void ProgressJS(Context context, String json) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getBoolean("success") == true) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("json", jsonObject.getJSONObject("data").toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, jsonObject.getString("notifi"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        Log.d("JSOn_EXception", "Failed ProgressJson");
                    }

                }
            }.execute("check-login");
        }
    }

    public void forgotPassView(View view) {
        Intent intent = new Intent(this, ForgotPassWordActivity.class);
        startActivity(intent);
    }

    public void registerView(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
