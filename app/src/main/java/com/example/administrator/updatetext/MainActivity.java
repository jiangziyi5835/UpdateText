package com.example.administrator.updatetext;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView tvClickUpdata;
    private ProgressBar pb_updating;
        private String url = "http://oss.crdai.com/apk/app_jycrd_4_1_5.apk";
//    private String url = "https://sh-download.weiyun.com/ftn_handler/96ea7b8f433fc7aa7219d99c845ed94301ae7f65efe8fbae3df61fc69419a4f75ae5c35a40f853f0c64b716c6935bdd468a8f27da41f8a9842e0e50893c890c6/taifeng1.1.apk?fname=taifeng1.1.apk&from=30113&version=3.3.3.3&uin=10000";


    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }


        tvClickUpdata = this.findViewById(R.id.tv_clickupdate);
        pb_updating = this.findViewById(R.id.pb_updating);
        tvClickUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                if (isWifi(MainActivity.this)) {
                    dialog.setTitle("title标题");
                } else {
                    dialog.setTitle("当前未处于wifi环境");
                }

                dialog.setCancelable(false);
                dialog.setPositiveButton("确定下载更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update2();
//                        update();
//                        dialog.cancel();
                    }
                });
                dialog.setNegativeButton("不下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                dialog.show();

            }
        });
    }


    /**
     * 检查wifi
     *
     * @param mContext
     * @return
     */
    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null
                && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }


    /**
     * 下载2
     */
    private void update2() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("下载中");
        request.setTitle("我的下载");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

        }
        request.allowScanningByMediaScanner();//设置可以被扫描到
        request.setVisibleInDownloadsUi(true);// 设置下载可见
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//下载完成后通知栏任然可见
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "my.apk");

        DownloadManager manager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        // manager.enqueue(request);
        long Id = manager.enqueue(request);
        //listener(Id);
        SharedPreferences sPreferences = this.getSharedPreferences(
                "downloadapk", 0);
        sPreferences.edit().putLong("apk", Id).commit();//保存此次下载ID
        Log.d("shengji", "开始下载任务:" + Id + " ...");
    }


}