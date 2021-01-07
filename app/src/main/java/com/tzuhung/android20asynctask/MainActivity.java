package com.tzuhung.android20asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    ImageView IV_show;
    Button btn_download;
    ProgressBar  PB_schedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IV_show = findViewById(R.id.IV_show);
        btn_download = findViewById(R.id.btn_download);
        btn_download.setOnClickListener(this);
        PB_schedule = findViewById(R.id.PB_schedule);
    }


    @Override
    public void onClick(View v) {
        new downloadimg().execute("https://www.mirrormedia.com.tw/assets/images/20200317123257-a74b2da99113fe595d4140915663258d-mobile.jpg");

    }
    private  class  downloadimg extends AsyncTask<String, Integer, Bitmap> {
        HttpURLConnection httpURLConnection;


        @Override
        protected Bitmap doInBackground(String... strings) {
            //從Url載入圖片
            try {
                URL url = new URL(strings[0]);
                PB_schedule.incrementProgressBy(50);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                Bitmap temp = BitmapFactory.decodeStream(inputStream);
                PB_schedule.incrementProgressBy(100);
                return temp;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null){
                IV_show.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Download Successful",
                        Toast.LENGTH_SHORT).show();//下載完成彈出下載成功

            }
            else {
                Toast.makeText(getApplicationContext(), "Download fail",
                        Toast.LENGTH_SHORT).show();//下載失敗彈出下載失敗
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values != null){
                PB_schedule.setProgress(values[0]);

            }
        }
    }
}