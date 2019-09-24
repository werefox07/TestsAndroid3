package com.geekbrains.lesson4.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.geekbrains.lesson4.R;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpMainActivityAsync extends AppCompatActivity {
    private TextView mInfoTextView;
    private ProgressBar progressBar;
    private EditText editText;
    private OkHttpClient client;
    private HttpUrl.Builder urlBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        mInfoTextView = (TextView) findViewById(R.id.tvLoad);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button btnLoad = (Button) findViewById(R.id.btnLoad);
        client = new OkHttpClient();
        btnLoad.setOnClickListener((v) -> onClick());
    }

    public void onClick() {
        urlBuilder = HttpUrl.parse("https://api.github.com/users").newBuilder();
        if (!editText.getText().toString().isEmpty()) {
            urlBuilder.addQueryParameter("login", editText.getText().toString()); //"mojombo");
        }
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            try {
                downloadOneUrl(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Подключите интернет", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadOneUrl(Request request) throws IOException {
        progressBar.setVisibility(View.VISIBLE);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                final String responseData = response.body().string();
                OkHttpMainActivityAsync.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mInfoTextView.setText(responseData);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}


