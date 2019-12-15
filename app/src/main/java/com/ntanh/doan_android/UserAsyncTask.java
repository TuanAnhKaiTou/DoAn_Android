package com.ntanh.doan_android;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Map;

public class UserAsyncTask extends AsyncTask<String, Void, String> implements XuLyJS {
    ProgressDialog progressDialog;
    private Context context;
    private String METHOD;
    private Map<String, String> paramets;
    private String titleProgressDialog;
    private String messageProgressDialog;
    private boolean flagProgressDialog;


    public UserAsyncTask(Context context, String METHOD, Map<String,String> paramets, String titleProgressDialog, String messageProgressDialog) {
        this.titleProgressDialog = titleProgressDialog;
        this.messageProgressDialog = messageProgressDialog;
        this.context = context;
        this.METHOD = METHOD;
        this.paramets = paramets;
        this.flagProgressDialog = true;

    }

    public UserAsyncTask(Context context, String METHOD,  Map<String,String> paramets) {
        this.context = context;
        this.METHOD = METHOD;
        this.paramets = paramets;
        this.flagProgressDialog = false;
    }

    @Override
    protected void onPreExecute() {
        if (this.flagProgressDialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(this.titleProgressDialog);
            progressDialog.setMessage(this.messageProgressDialog);
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        // Kết nối
        return NetworkUtils.getJSONData(strings[0], this.METHOD, this.paramets);
    }

    @Override
    protected void onPostExecute(String s) {
        ProgressJS(context, s);
        if (this.flagProgressDialog) {
            this.progressDialog.dismiss();
        }

    }

    @Override
    public void ProgressJS( Context context,String json) {

    }

}
