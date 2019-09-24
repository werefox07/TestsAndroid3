package com.geekbrains.lesson4.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpMainActivity extends AppCompatActivity {

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
        btnLoad.setOnClickListener((v) -> onClick());

        client = new OkHttpClient();
    }

    public void onClick() {
        urlBuilder = HttpUrl.parse("https://api.github.com/users").newBuilder();
        if (!editText.getText().toString().isEmpty()) {
            // Строку запроса, передаваемую в метод url, можно предварительно сконструировать
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
            new DownloadPageTask().execute(request); // Запускаем в новом потоке
        } else {
            Toast.makeText(this, "Подключите интернет", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadPageTask extends AsyncTask<Request, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mInfoTextView.setText("");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Request... requests) {
            try {
                return downloadOneUrl(requests[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mInfoTextView.setText(result);
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(result);
        }
    }

    private String downloadOneUrl(Request request) throws IOException {
        String data = "";
        try {
            // синхронный вызов
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            } else {
                // Вывести заголовки
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                // Тело запроса
                data = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


}
