package com.ntanh.doan_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class NewGameActivity extends AppCompatActivity implements RingProgressBar.OnProgressListener, View.OnClickListener {

    private RingProgressBar mRingProgressBar;
    private int progress = 1, mMaxProgress;
    private RingProgressbarAsyntask mThreadCountdown;
    private TextView txt_timer, mTextQuestion;
    private TextView mTextContent_A, mTextContent_B, mTextContent_C, mTextContent_D;
    private LinearLayout mBtn_A, mBtn_B, mBtn_C, mBtn_D;
    private String mDapAn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        //Initi();
        InitiQuestion();
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

    @SuppressLint("StaticFieldLeak")
    public void InitiQuestion() {
        this.mBtn_A = findViewById(R.id.btn_A);
        this.mBtn_B = findViewById(R.id.btn_B);
        this.mBtn_C = findViewById(R.id.btn_C);
        this.mBtn_D = findViewById(R.id.btn_D);

        this.mTextQuestion = findViewById(R.id.text_question);
        this.mTextContent_A = findViewById(R.id.txt_content_A);
        this.mTextContent_B = findViewById(R.id.txt_content_B);
        this.mTextContent_C = findViewById(R.id.txt_content_C);
        this.mTextContent_D = findViewById(R.id.txt_content_D);
        //

        this.mBtn_A.setOnClickListener(this);
        this.mBtn_B.setOnClickListener(this);
        this.mBtn_C.setOnClickListener(this);
        this.mBtn_D.setOnClickListener(this);

        new UserAsyncTask(this, NetworkUtils.GET, null,"", "") {

            @Override
            public void ProgressJS(Context context, String json) {
                //Toast.makeText(context, json + "", Toast.LENGTH_SHORT).show();
                //Log.d("JSON-test", json);
                try {

                    JSONObject jsonObject = new JSONObject(json);

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    mTextQuestion.setText(jsonArray.getJSONObject(0).getString("noi_dung"));
                    mTextContent_A.setText(jsonArray.getJSONObject(0).getString("phuong_an_a"));
                    mTextContent_B.setText(jsonArray.getJSONObject(0).getString("phuong_an_b"));
                    mTextContent_C.setText(jsonArray.getJSONObject(0).getString("phuong_an_c"));
                    mTextContent_D.setText(jsonArray.getJSONObject(0).getString("phuong_an_d"));
                    mDapAn = jsonArray.getJSONObject(0).getString("dap_an");
                } catch (JSONException e) {
                    Log.d("JSOn_EXception", "Failed ProgressJson");
                }
            }
        }.execute("cau-hoi");
    }

    @Override
    public void progressToComplete() {
        Toast.makeText(this, "Hoàn tất", Toast.LENGTH_SHORT).show();
    }

    public void XuLyDapAn(String chooseDapAn, LinearLayout btn) {
        if (chooseDapAn.equals(mDapAn)) {
            XuLyDung(btn);
        }
        else {
            XuLySai(btn);
        }
    }

    public void XuLyDung(LinearLayout btn) {
        try {
            Thread.sleep(1000);
            btn.setBackgroundResource(R.drawable.custom_answer_true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void XuLySai(LinearLayout btn) {
        try {
            Thread.sleep(1000);
            btn.setBackgroundResource(R.drawable.custom_answer_fail);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_A:
                XuLyDapAn("A", mBtn_A);
                break;
            case R.id.btn_B:
                XuLyDapAn("B", mBtn_B);
                break;
            case R.id.btn_C:
                XuLyDapAn("C", mBtn_C);
                break;
            case R.id.btn_D:
                XuLyDapAn("D", mBtn_D);
                break;
            default: {
                break;
            }
        }

    }
}
