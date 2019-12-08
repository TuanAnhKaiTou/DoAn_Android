package com.ntanh.doan_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class NewGameActivity extends AppCompatActivity implements RingProgressBar.OnProgressListener{

    private RingProgressBar mRingProgressBar;
    private int progress = 1, mMaxProgress;
    private RingProgressbarAsyntask mThreadCountdown;
    private TextView txt_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        Initi();
    }

    public void Initi() {
        this.txt_timer = findViewById(R.id.text_timer);
        this.mRingProgressBar = findViewById(R.id.ringProgress);
        this.mRingProgressBar.setOnProgressListener(this);
        this.mRingProgressBar.setMax(30); // Tính bằng second
        mMaxProgress = mRingProgressBar.getMax();
        // Xử lý đếm ngược bất đồng bộ
        mThreadCountdown = new RingProgressbarAsyntask(this, txt_timer, mRingProgressBar, progress, mMaxProgress);

        mThreadCountdown.execute();
    }

    @Override
    public void progressToComplete() {
        Toast.makeText(this, "Hoàn tất", Toast.LENGTH_SHORT).show();
    }
}
