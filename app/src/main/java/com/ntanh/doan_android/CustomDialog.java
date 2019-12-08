package com.ntanh.doan_android;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomDialog extends Dialog implements View.OnClickListener  {
    private EditText mMatKhau, mReMatKhau;
    private Button btnDoiMatKhau, btnHuy;
    private Context context;
    private String ten_dang_nhap;

    public CustomDialog(@NonNull Context context, String ten_dang_nhap) {
        super(context);
        this.context = context;
        this.ten_dang_nhap = ten_dang_nhap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.custom_forget_pass);
        this.setCanceledOnTouchOutside(false);
        this.setTitle("Lấy mật khẩu");


        mMatKhau = findViewById(R.id.edtMatKhauMoi_forget);
        mReMatKhau = findViewById(R.id.edtReMatKhauMoi_forget);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau_forget);
        btnHuy = findViewById(R.id.btnHuy_forget);

        btnDoiMatKhau.setOnClickListener(this);
        btnHuy.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDoiMatKhau_forget:
                final String mat_khau = mMatKhau.getText().toString();
                final String re_mat_khau = mReMatKhau.getText().toString();
                // ktra mat khau trung vs nhap lai mat khau ko trong
                if (!Ultilis.CheckForgotPass(mat_khau, re_mat_khau)) {
                    Toast.makeText(context, "Không để trông thông tin", Toast.LENGTH_SHORT).show();
                }
                // ktra mat khau trung vs nhap lai mat khau
                else if (!mat_khau.equals(re_mat_khau)) {
                    Toast.makeText(context, "Nhập lại mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                } else {
                    final Map<String, String> paramets = new HashMap<>();
                    paramets.put("mat_khau", mat_khau);
                    paramets.put("ten_dang_nhap", this.ten_dang_nhap);
                    new UserAsyncTask(context, NetworkUtils.POST, paramets, "Update password", "waiting for update pass..."){
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
                                dismiss();
                            } catch (JSONException e) {
                                Log.d("JSOn_EXception", "Failed ProgressJson");
                            }
                        }
                    }.execute("get-new-pass");
                }
                break;
            case R.id.btnHuy_forget: this.dismiss();
        }
    }
}
