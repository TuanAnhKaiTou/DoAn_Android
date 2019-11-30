package com.ntanh.doan_android;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class UserAsyncTask extends AsyncTask<String, Void, String> implements XuLyJS {
    ProgressDialog progressDialog;
    private Context context;
    private String METHOD;
    private Map<String, String> paramets;
    private String titleProgressDialog;


    public UserAsyncTask(Context context, String METHOD, Map<String,String> paramets, String titleProgressDialog) {
        this.titleProgressDialog = titleProgressDialog;
        this.context = context;
        this.METHOD = METHOD;
        this.paramets = paramets;

    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(this.titleProgressDialog);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        // Kết nối
        return NetworkUtils.getJSONData(strings[0], this.METHOD, this.paramets);
    }

    @Override
    protected void onPostExecute(String s) {
        ProgressJS(context,this.progressDialog, s);

    }

    @Override
    public void ProgressJS( Context context,ProgressDialog progressDialog, String json) {
        progressDialog.dismiss();
    }
}
