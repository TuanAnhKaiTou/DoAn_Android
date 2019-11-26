package com.ntanh.doan_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void manageAccountView(View view) {
        Intent intent = new Intent(this, ManageAccountActivity.class);
        startActivity(intent);
    }

    public void buyCreditView(View view) {
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
