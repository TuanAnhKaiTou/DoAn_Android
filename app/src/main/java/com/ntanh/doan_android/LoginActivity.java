package com.ntanh.doan_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private TextView mTenDangNhap, mCredit;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initi();
    }

    void Initi() {
        mTenDangNhap = findViewById(R.id.txt_tendangnhap_main);
        mCredit = findViewById(R.id.txt_credit_main);

        Intent intent = getIntent();
        try{
            jsonObject = new JSONObject(intent.getStringExtra("json"));
            mTenDangNhap.setText(jsonObject.getString("ten_dang_nhap"));
            mCredit.setText(jsonObject.getString("credit"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void manageAccountView(View view) {
        Intent intent = new Intent(this, ManageAccountActivity.class);
        intent.putExtra("json", jsonObject.toString());
        startActivity(intent);
    }

    public void buyCreditView(View view) {
        Intent intent = new Intent(this, BuyCreditActivity.class);
        startActivity(intent);
    }

    public void historyView(View view) {
    }

    public void newGameView(View view) {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

    public void highScoreView(View view) {
    }
}
