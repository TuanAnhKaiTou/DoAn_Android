package com.ntanh.doan_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManageAccountActivity extends AppCompatActivity {

    private EditText mTenDangNhap, mEmail, mMatKhau, mReMatKhau;
    private ImageView mHinhDaiDien;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        Initi();
    }

    void Initi() {
        this.mTenDangNhap = findViewById(R.id.edt_tenDangNhap_manager);
        this.mEmail = findViewById(R.id.edt_email_manager);
        this.mMatKhau = findViewById(R.id.edt_password_manager);
        this.mReMatKhau = findViewById(R.id.edt_repassword_manager);
        this.mHinhDaiDien = findViewById(R.id.img_hinhDaiDien);

        Intent intent = getIntent();
        try {
            jsonObject = new JSONObject(intent.getStringExtra("json"));
            mTenDangNhap.setText(jsonObject.getString("ten_dang_nhap"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void handleManager(View view) {
        String ten_dang_nhap = mTenDangNhap.getText().toString();
        String email = mEmail.getText().toString();
        String mat_khau = mMatKhau.getText().toString();
        String re_mat_khau = mReMatKhau.getText().toString();
        if (!Ultilis.CheckRegister(ten_dang_nhap, mat_khau, email, re_mat_khau)) {
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!re_mat_khau.equals(mat_khau)) {
            Toast.makeText(this, "Nhập lại mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> paramets = new HashMap<>();
            paramets.put("ten_dang_nhap", ten_dang_nhap);
            paramets.put("email", email);
            paramets.put("mat_khau", mat_khau);

            new UserAsyncTask(this, NetworkUtils.POST, paramets, "Manager", "Waiting for manager..."){
                @Override
                public void ProgressJS(Context context, String json) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getBoolean("success") == true) {
                            Toast.makeText(context, jsonObject.getString("notifi"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, jsonObject.getString("notifi"), Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        Log.d("JSOn_EXception", "Failed ProgressJson");
                    }

                }
            }.execute("manager");
            finish();
        }
    }

    public void handleChangeAvatar(View view) {
    }
}
