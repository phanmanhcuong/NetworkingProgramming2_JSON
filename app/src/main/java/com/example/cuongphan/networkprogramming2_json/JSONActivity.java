package com.example.cuongphan.networkprogramming2_json;


import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONActivity extends Activity {

    String mTotalpayments;
    String mMonthlyPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        MonthlyPayments monthlyPayments = new MonthlyPayments();
        monthlyPayments.execute("http://apps.coreservlets.com/NetworkingSupport/loan-calculator");
    }

    private class MonthlyPayments extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String result = stringBuilder.toString();
                JSONObject jsonObject = new JSONObject(result);
                mTotalpayments = jsonObject.getString("formattedTotalPayments");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mTotalpayments;
        }

        @Override
        protected void onPostExecute(String totalpayments){
            TextView textView = (TextView)findViewById(R.id.tv);
            textView.setText(mTotalpayments);
        }
    }
}
