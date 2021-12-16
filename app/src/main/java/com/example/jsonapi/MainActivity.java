package com.example.jsonapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsonapi.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<String> userList;
    ArrayAdapter<String> listAdapter;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    Button buttonSend, buttonGet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeUserList();

        /*binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new sendData().start();
            }
        });  */
        binding.buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new fetchData().start();

            }
        });

    }

    private void initializeUserList() {

        userList = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, userList);
        binding.userList.setAdapter(listAdapter);
    }


    /* class sendData extends Thread {

        String data = " ";

        @Override
        public void run() {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Sending Data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });
           try {
                URL url = new URL("https://mocki.io/v1/e5f25acc-2bc2-46f4-8529-6a865089cfa5");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                String line;

                while ((line = bufferedWriter.writeLine()) != null) {

                    data = data + line;
                }

                if (!data.isEmpty()) {

                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray users = jsonObject.getJSONArray("data");
                    userList.clear();
                    for (int i = 0; i < users.length(); i++) {


                        JSONObject names = users.getJSONObject(i);
                        String name = names.getString("cabin_crew_name");
                        userList.add(name);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        */


        class fetchData extends Thread {

            String data = "";

            @Override
            public void run() {

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Fetching Data");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                    }
                });
                try {
                    URL url = new URL("https://mocki.io/v1/e5f25acc-2bc2-46f4-8529-6a865089cfa5");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {

                        data = data + line;
                    }

                    if (!data.isEmpty()) {

                        JSONObject jsonObject = new JSONObject(data);
                        JSONArray users = jsonObject.getJSONArray("data");
                        userList.clear();
                        for (int i = 0; i < users.length(); i++) {


                            JSONObject names = users.getJSONObject(i);
                            String name = names.getString("cabin_crew_name");
                            userList.add(name);
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        listAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
     }