package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private AdapterMask pAdapter;
    private List<Mask> maskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView ivProducts = findViewById(R.id.auto_list);
        pAdapter = new AdapterMask(MainActivity.this,maskList);
        ivProducts.setAdapter(pAdapter);

        configurationNextButton();
            new GetAuto().execute();
    }

    private void configurationNextButton() {
        Button addData = (Button) findViewById(R.id.button_add);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Add.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private class GetAuto extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids)
        {
            try
            {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/ВласоваАС/api/Autoes");
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();

                BufferedReader reader =new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine())!=null)
                {
                    result.append(line);
                }
                return  result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempar = new JSONArray(s);
                for (int i=0; i<tempar.length(); i++)
                {
                    JSONObject autojson = tempar.getJSONObject(i);
                    Mask tempaut = new Mask(
                            autojson.getInt("ID"),
                            autojson.getString("Name"),
                            autojson.getString("Power"),
                            autojson.getString("Image")
                    );
                    maskList.add(tempaut);
                    pAdapter.notifyDataSetInvalidated();
                }
            }
            catch (Exception ignored)
            {

            }
        }

    }
}