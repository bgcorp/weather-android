package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnChooseСity;
    EditText etCity;
    TextView textCity;
    TextView textTemp;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textCity = findViewById(R.id.textCity);
        textTemp = findViewById(R.id.temp);

        etCity = findViewById(R.id.etCity);
        btnChooseСity = findViewById(R.id.btnChooseСity);
        btnChooseСity.setOnClickListener(this);
        mQueue = Volley.newRequestQueue(this);
        this.getWeatherDataByCityName("almaty");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChooseСity:
                String cityName = etCity.getText().toString();
                if (!TextUtils.isEmpty(cityName)) {
                    this.getWeatherDataByCityName(cityName);
                    etCity.setText("");
                }
                break;
            default:
                break;
        }
    }

    public void getWeatherDataByCityName(String cityName) {
        String token = "";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + token + "&units=Metric";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("api", "res: " + response);
                            JSONObject main = response.getJSONObject("main");
                            double temp = main.getDouble("temp");
                            String cityName = response.getString("name");

                            textCity.setText(cityName);
                            textTemp.setText(temp + " градусов по цельсию");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("api", "error: " + error);
                        error.printStackTrace();
                    }
                });
        mQueue.add(request);
    }
}
