package com.example.weathcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weathcare.common.NodeNames;
import com.example.weathcare.common.WeatherTip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextCity;
    TextView printTemperature;
    TextView printHumidity;
    TextView printWindSpeed;
    TextView printCountry;
    Button infoBtn;

    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.htweather);
        Init();
    }

    private void Init()
    {
        //EditText
        editTextCity =  findViewById(R.id.editTextCity);

        //Print
        printTemperature = findViewById(R.id.textViewTemp);
        printHumidity = findViewById(R.id.textViewHumidity);
        printWindSpeed = findViewById(R.id.textViewWindSpeed);
        printCountry = findViewById(R.id.textViewCountry);

        //Btn
        infoBtn = findViewById(R.id.submitBtn);

        //Listener
        infoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.submitBtn:
                ShowInformation();
                break;
        }
    }

    private void ShowInformation()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, UrlOFOpenWeatherMap(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            { Json(response); Toast.makeText(MainActivity.this, "Good City!", Toast.LENGTH_LONG).show();}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Incorrect City!", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }//End of ShowInformation

    private void Json(JSONObject response)
    {
        try {
            JSONObject object1 = response.getJSONObject("main");
            String temperature = object1.getString("temp");
            Double tempeConversion = Double.parseDouble(temperature)-273.15f;
            String humidity = object1.getString("humidity");

            JSONArray object2 = response.getJSONArray("weather");
            JSONObject obj = object2.getJSONObject(0);
            String weather = obj.getString("main");
            String weath_desc = obj.getString("description");

            JSONObject object3 = response.getJSONObject("sys");
            String country = object3.getString("country");


            //Gawa ako on click nilang lahat
            if(weather.equals("Rain"))
            {
                progressBar.setVisibility(View.VISIBLE);
                infoBtn.setVisibility(View.GONE);

                //Gone button dito pag nag back
            }else if(weather.equals("Thunderstorm"))
            {

                //Gone button dito pag nag back
            }else if(weather.equals("Drizzle"))
            {

                //Gone button dito pag nag back
            }else if(weather.equals("Snow"))
            {

                //Gone button dito pag nag back
            }else if(weather.equals("Clear"))
            {

                //Gone button dito pag nag back
            }else if(weather.equals("Clear") && tempeConversion >= 25.0)
            {

                //Gone button dito pag nag back
            }
            else if(weather.equals("Clouds"))
            {

                //Gone button dito pag nag back
            }

            printTemperature.setText(tempeConversion.toString().substring(0,5) + " °C");
//            printHumidity.setText("Humidity: "+ humidity);
            printHumidity.setText(WeatherTip.stayCool);
            printWindSpeed.setText(WeatherTip.stayHydrated);
            printCountry.setText(WeatherTip.stayInformed);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }//End of Json

    private String UrlOFOpenWeatherMap()
    {
        //Apikey nyo dapat ilagay dito pag nag error
        String apikey = "e1e4d822bb3f6e0db3e8bba1880c7e33";
        String city = editTextCity.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey+"\n";//asd
        return url;
    }

}


