package com.ntanh.doan_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

public class ForgotPassWordActivity extends AppCompatActivity {

    private EditText mTenDangNhap, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_word);
        Initi();
    }

    void Initi() {
        mTenDangNhap = findViewById(R.id.edt_tendangnhap_forgotpass);
        mEmail = findViewById(R.id.edt_email_forgotpass);
    }

    public void handleForgotPass(View view) {
        final String ten_dang_nhap = mTenDangNhap.getText().toString();
        String email = mEmail.getText().toString();
        // ktra ton tai ten dang nhap vs email
        if (!Ultilis.CheckForgotPass(ten_dang_nhap, email)) {
            Toast.makeText(this, "Nhập đầy đủ thông tin!!", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> paramets = new HashMap<>();
            paramets.put("ten_dang_nhap", ten_dang_nhap);
            paramets.put("email",email);
            new UserAsyncTask(this, NetworkUtils.POST, paramets, "Forgot Password", "Waiting for forgot..."){
                @Override
                public void ProgressJS(Context context, String json) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getBoolean("success") == true) {
                            // Show 1 dialog de thay doi mat khau
                            new CustomDialog(context, ten_dang_nhap).show();

                        } else {
                            Toast.makeText(context, jsonObject.getString("notifi"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.d("JSOn_EXception", "Failed ProgressJson");
                    }

                }
            }.execute("check-exist");
        }
    }
}
