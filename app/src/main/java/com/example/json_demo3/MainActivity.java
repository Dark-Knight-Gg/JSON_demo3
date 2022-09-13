package com.example.json_demo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageButton m1_imgbtnVN,m1_imgbtnEN;
    TextView m1_txt1;
    String content="";
    private void AnhXa(){
        m1_imgbtnEN=(ImageButton) findViewById(R.id.m1_imgbtnEN);
        m1_imgbtnVN=(ImageButton) findViewById(R.id.m1_imgbtnVN);
        m1_txt1=(TextView) findViewById(R.id.m1_txt1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        new JSON_OBJECT().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo3.json");
        m1_imgbtnVN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                READ_JSON("vn");
            }
        });
        m1_imgbtnEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                READ_JSON("en");
            }
        });
    }
    private class JSON_OBJECT extends AsyncTask<String, Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            content = s;
            READ_JSON("vn");
        }
    }
    private void READ_JSON(String language) {
        try {
            JSONObject object = new JSONObject(content);
            JSONObject objectlang = object.getJSONObject("language");
            JSONObject object1 = objectlang.getJSONObject(language);
            String name = object1.getString("name");
            String address = object1.getString("address");
            String course1 = object1.getString("course1");
            String course2 = object1.getString("course2");
            String course3 = object1.getString("course3");
            m1_txt1.setText(name+"\n"+address+"\n"+course1+"\n"+course2+"\n"+course3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}