package com.geekbrains.lesson4.nativeclass;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NativeMainActivity extends AppCompatActivity {
    private TextView mInfoTextView;
    private ProgressBar progressBar;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        mInfoTextView = (TextView) findViewById(R.id.tvLoad);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener((v) -> onClick());
    }

    public void onClick() {
        String bestUrl = "https://api.github.com/users"; // mojombo
        if (!editText.getText().toString().isEmpty()) {
            bestUrl += "/" + editText.getText();
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            new DownloadPageTask().execute(bestUrl); // запускаем в новом потоке
        } else {
            Toast.makeText(this, "Подключите интернет", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mInfoTextView.setText("");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadOneUrl(urls[0]);
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

    private String downloadOneUrl(String address) throws IOException {
        InputStream inputStream = null;
        String data = "";
        try {
            URL url = new URL(address);
            // Сначала получаем объект HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // устанавливаем параметры сессии
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
            // connection.setRequestMethod("GET"); // используется по умолчанию. Чтобы отправить POST, используйте setRequestMethod.
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();
            // Проверяем код ответа
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK

                // Вспомогательные методы  класса
                System.out.println("Метод запроса: " + connection.getRequestMethod());
                // Вывести код ответа
                System.out.println("Ответное сообщение: " + connection.getResponseMessage());
                // Получить список полей и множество ключей из заголовка
                Map<String, List<String>> myMap = connection.getHeaderFields();
                Set<String> myField = myMap.keySet();
                System.out.println("\nДалее следует заголовок:");
                // Вывести все ключи и значения из заголовка
                for (String k : myField) {
                    System.out.println("Ключ: " + k + " Значение: "
                            + myMap.get(k));
                }

                // получаем данные, которые находятся по запрашиваемой ссылке
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read = 0;
                while ((read = inputStream.read()) != -1) {
                    bos.write(read);
                }
                byte[] result = bos.toByteArray();
                bos.close();
                data = new String(result);
            } else {
                data = connection.getResponseMessage() + " . Error Code : " + responseCode;
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return data;
    }
}

