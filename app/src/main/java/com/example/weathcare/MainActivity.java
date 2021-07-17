package com.example.weathcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weathcare.common.WeatherTip;
import com.example.weathcare.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextCity;
    //Clouds
    TextView printTemperatureCloud;
    TextView printDescriptionCloud;
    TextView printTip1Cloud;
    TextView printTip2Cloud;
    TextView printTip3Cloud;

    //Rain
    TextView printTemperatureRain;
    TextView printDescriptionRain;
    TextView printTip1Rain;
    TextView printTip2Rain;
    TextView printTip3Rain;

    //Snow
    TextView printTemperatureSnow;
    TextView printDescriptionSnow;
    TextView printTip1Snow;
    TextView printTip2Snow;
    TextView printTip3Snow;

    //Hot
    TextView printTemperatureHot;
    TextView printDescriptionHot;
    TextView printTip1Hot;
    TextView printTip2Hot;
    TextView printTip3Hot;


    Button infoBtn;

    private View viewHot;
    private View viewRain;
    private View viewSnow;
    private View viewClouds;

    private LinearLayout llmainMenu;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
    }

    private void Init()
    {
        viewHot = findViewById(R.id.htweather);
        viewRain = findViewById(R.id.rnweather);
        viewSnow = findViewById(R.id.coldweather);
        viewClouds = findViewById(R.id.cloudseather);

        //EditText
        editTextCity =  findViewById(R.id.editTextCity);

        //Clouds
        printTemperatureCloud = findViewById(R.id.textViewTempCl);
        printDescriptionCloud = findViewById(R.id.textViewDescriptionCl);
        printTip1Cloud = findViewById(R.id.textViewTip1Cl);
        printTip2Cloud = findViewById(R.id.textViewTip2Cl);
        printTip3Cloud = findViewById(R.id.textViewTip3Cl);

        //Rain
        printTemperatureRain = findViewById(R.id.textViewTempRain);
        printDescriptionRain = findViewById(R.id.textViewDescriptionRain);
        printTip1Rain = findViewById(R.id.textViewTip1Rn);
        printTip2Rain = findViewById(R.id.textViewTip2Rn);
        printTip3Rain = findViewById(R.id.textViewTip3Rn);

        //Hot
        printTemperatureHot = findViewById(R.id.textViewTempHot);
        printDescriptionHot = findViewById(R.id.textViewDescriptionHot);
        printTip1Hot = findViewById(R.id.textViewTip1Ht);
        printTip2Hot = findViewById(R.id.textViewTip2Ht);
        printTip3Hot = findViewById(R.id.textViewTip3Ht);

        //Snow
        printTemperatureSnow = findViewById(R.id.textViewTempSnow);
        printDescriptionSnow = findViewById(R.id.textViewDescriptionSnow);
        printTip1Snow = findViewById(R.id.textViewTip1Sn);
        printTip2Snow = findViewById(R.id.textViewTip2Sn);
        printTip3Snow = findViewById(R.id.textViewTip3Sn);

        llmainMenu = findViewById(R.id.main_menu);
        
        //Btn
        infoBtn = findViewById(R.id.submitBtn);

        //FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //Listener
        infoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
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
            if(weather.equals("Rain") || weather.equals("Thunderstorm"))
            {
                viewRain.setVisibility(View.VISIBLE);
                infoBtn.setVisibility(View.GONE);

                Log.v("HAPPY", "Rain");

                printTemperatureRain.setText(tempeConversion.toString().substring(0,5) + " °C");
                printDescriptionRain.setText(weath_desc);
                printTip1Rain.setText(WeatherTip.warmAndDry);
                printTip2Rain.setText(WeatherTip.chargePhone);
                printTip3Rain.setText(WeatherTip.tuneToNews);

            }else if(weather.equals("Drizzle"))
            {
                Log.v("HAPPY", "Drizle");

            }else if(weather.equals("Snow"))
            {
                viewSnow.setVisibility(View.VISIBLE);
                infoBtn.setVisibility(View.GONE);

                Log.v("HAPPY", "Snow");

                printTemperatureSnow.setText(tempeConversion.toString().substring(0,5) + " °C");
                printDescriptionSnow.setText(weath_desc);
                printTip1Snow.setText(WeatherTip.dressWarmly);
                printTip2Snow.setText(WeatherTip.listenWeatherForecast);
                printTip3Snow.setText(WeatherTip.seekShelter);


            }else if(weather.equals("Clear"))
            {
                viewHot.setVisibility(View.VISIBLE);
                infoBtn.setVisibility(View.GONE);

                printTemperatureHot.setText(tempeConversion.toString().substring(0,5) + " °C");
                printDescriptionHot.setText(weath_desc);
                printTip1Hot.setText(WeatherTip.stayHydrated);
                printTip2Hot.setText(WeatherTip.stayCool);
                printTip3Hot.setText(WeatherTip.stayInformed);


                Log.v("HAPPY", "Clear");
            }else if(weather.equals("Clear") && tempeConversion >= 38.0)
            {
                viewHot.setVisibility(View.VISIBLE);
                infoBtn.setVisibility(View.GONE);

                printTemperatureHot.setText(tempeConversion.toString().substring(0,5) + " °C");
                printDescriptionHot.setText(weath_desc);
                printTip1Hot.setText(WeatherTip.stayHydrated);
                printTip2Hot.setText(WeatherTip.stayCool);
                printTip3Hot.setText(WeatherTip.stayInformed);

                //Gone button dito pag nag back
            }
            else if(weather.equals("Clouds"))
            {
                viewClouds.setVisibility(View.VISIBLE);
                infoBtn.setVisibility(View.GONE);

                Log.v("HAPPY", "Clouds");
                //Gone button dito pag nag back

                printTemperatureCloud.setText(tempeConversion.toString().substring(0,5) + " °C");
                printDescriptionCloud.setText(weath_desc);
                printTip1Cloud.setText(WeatherTip.spendTime);
                printTip2Cloud.setText(WeatherTip.startFitness);
                printTip3Cloud.setText(WeatherTip.spendOutdoors);
            }


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
    
    public void BtnBackBtn(View view)
    {
        Toast.makeText(this, "asdasadadasa", Toast.LENGTH_SHORT).show();
        viewRain.setVisibility(View.GONE);
        viewClouds.setVisibility(View.GONE);
        viewSnow.setVisibility(View.GONE);
        viewHot.setVisibility(View.GONE);

        llmainMenu.setVisibility(View.VISIBLE);
        infoBtn.setVisibility(View.VISIBLE);
    }


    public void BtnLogoutHomeClick(View v)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

}


